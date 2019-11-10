package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.service.ICarService;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("car")
public class CarController extends BaseController {

     @Autowired
    private ICarService iCarService;

    /*查询车辆信息*/
    @GetMapping("list")
    @RequiresPermissions("car:view")/*要求subject中必须含有role:view的权限才能执行方法roleList()否则抛出异常AuthorizationException*/
    public FebsResponse carList(Car car, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.iCarService.findCarPage(car,request));
        return new FebsResponse().success().data(dataTable);
    }

    /*新增车辆*/
    @PostMapping
    @RequiresPermissions("car:add")
    @ControllerEndpoint(operation = "新增车辆", exceptionMessage = "新增车辆失败")
    public FebsResponse addCar(@Valid Car car) {
        this.iCarService.addCar(car);
        return new FebsResponse().success();
    }

    /*删除车辆*/
    @GetMapping("delete/{carIds}")
    @RequiresPermissions("car:delete")
    @ControllerEndpoint(operation = "删除角色", exceptionMessage = "删除角色失败")
    public FebsResponse deleteCar(@NotBlank(message = "{required}") @PathVariable String carIds) {
        this.iCarService.delCar(carIds);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("car:update")
    @ControllerEndpoint(operation = "修改角色", exceptionMessage = "修改角色失败")
    public FebsResponse updateCar(Car car) {
        this.iCarService.updateCar(car);
        return new FebsResponse().success();
    }

    @GetMapping("excel")
    @RequiresPermissions("car:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败!")
    public void export(QueryRequest queryRequest, Car car, HttpServletResponse response) throws FebsException {
        List<Car> cars = this.iCarService.findCarPage(car, queryRequest).getRecords();
        ExcelKit.$Export(Car.class, response).downXlsx(cars, false);
    }

    /**
     * 生成 Excel导入模板
     */
    @GetMapping("template")
    @RequiresPermissions("car:template")
    @ControllerEndpoint(exceptionMessage = "生成导出模板Excel失败!")
    public void generateImportTemplate(QueryRequest queryRequest,HttpServletResponse response) {
        List<Car> cars = new ArrayList<>();
        // 构建模板
        ExcelKit.$Export(Car.class, response).downXlsx(cars, true);
    }

    /**
     * 导入Excel数据，并批量插入 T_EXIMPORT表
     */
    @PostMapping("carImport")
    @RequiresPermissions("car:carImport")
    @ControllerEndpoint(exceptionMessage = "导入Excel数据失败")
    public FebsResponse importExcels(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new FebsException("导入数据为空");
        }
        String filename = file.getOriginalFilename();
        if (!StringUtils.endsWith(filename, ".xlsx")) {
            throw new FebsException("只支持.xlsx类型文件导入");
        }
        // 开始导入操作
        Stopwatch stopwatch = Stopwatch.createStarted();
        final List<Car> data = Lists.newArrayList();
        final List<Map<String, Object>> error = Lists.newArrayList();
        ExcelKit.$Import(Car.class).readXlsx(file.getInputStream(), new ExcelReadHandler<Car>() {
            @Override
            public void onSuccess(int sheet, int row, Car car) {
                // 数据校验成功时，加入集合
                car.setCreateTime(new Date());
                data.add(car);
            }

            @Override
            public void onError(int sheet, int row, List<ExcelErrorField> errorFields) {
                // 数据校验失败时，记录到 error集合
                error.add(ImmutableMap.of("row", row, "errorFields", errorFields));
            }
        });
        if (CollectionUtils.isNotEmpty(data)) {
            // 将合法的记录批量入库
            this.iCarService.batchInsert(data);
        }
        ImmutableMap<String, Object> result = ImmutableMap.of(
                "time", stopwatch.stop().toString(),
                "data", data,
                "error", error
        );
        return new FebsResponse().success().data(result);
    }

}
