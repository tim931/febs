package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Role;
import cc.mrbird.febs.system.service.ICarService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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

}
