package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.service.ICommodityService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;

/**
 * @author zt
 */
@Slf4j
@Validated
@RestController
@RequestMapping("commodity")
public class CommodityController extends BaseController {

    @Autowired
    private ICommodityService iCommodityService;

    @GetMapping("check/{commodityName}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String commodityName, String commodityId) {
        return this.iCommodityService.findCommodityName(commodityName) == null || StringUtils.isNotBlank(commodityId);
    }

    /*查找所有的用户 分页*/
    @GetMapping("list")
    @RequiresPermissions("commodity:view")
    public FebsResponse commodityList(Commodity commodity, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.iCommodityService.findCommodityDetailPage(commodity,request));
        return new FebsResponse().success().data(dataTable);
    }

 //* 新增一条客户信息*//*
    @PostMapping
    @RequiresPermissions("commodity:add")
    @ControllerEndpoint(operation = "新增商品", exceptionMessage = "新增商品失败")
    public FebsResponse addCommodity(@Valid Commodity commodity) {
        this.iCommodityService.createCommodity(commodity);
        return new FebsResponse().success();
    }


    /*删除一个客户信息*/
     @GetMapping("delete/{commodityIds}")
     @RequiresPermissions("commodity:delete")
     @ControllerEndpoint(operation = "删除商品信息", exceptionMessage = "删除商品信息失败")
     public FebsResponse deleteCommodity(@NotBlank(message = "{required}") @PathVariable String commodityIds) {
         String[] ids = commodityIds.split(StringPool.COMMA);
         this.iCommodityService.deleteCommoditys(ids);
         return new FebsResponse().success();
     }

    @PostMapping("update")
    @RequiresPermissions("commodity:update")
    @ControllerEndpoint(operation = "修改商品信息", exceptionMessage = "修改商品失败")
    public FebsResponse updateCommodity(@Valid Commodity commodity) {
        if (commodity.getCommodityId() == null)
            throw new FebsException("商品ID为空");
        this.iCommodityService.updateCommodity(commodity);
        return new FebsResponse().success();
    }

   /*导出excel表*/
    @GetMapping("excel")
    @RequiresPermissions("commodity:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Commodity commodity, HttpServletResponse response) {
        List<Commodity> commoditys = this.iCommodityService.findCommodityDetailPage(commodity, queryRequest).getRecords();
        ExcelKit.$Export(Commodity.class, response).downXlsx(commoditys, false);
    }

}
