package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.entity.Procurement;
import cc.mrbird.febs.system.service.IProcurementService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
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
@RequestMapping("procurement")
public class ProcurementController extends BaseController {

    @Autowired
    private IProcurementService iProcurementService;

    /*查找所有的商品 分页*/
    @GetMapping("list")
    @RequiresPermissions("procurement:view")
    public FebsResponse procurementList(Procurement procurement, QueryRequest request){
        Map<String, Object> dataTable = getDataTable(this.iProcurementService.findProcurementDetailPage(procurement,request));
        return new FebsResponse().success().data(dataTable);
    }

    /* 新增一条客户信息*/
    @PostMapping
    @RequiresPermissions("procurement:add")
    @ControllerEndpoint(operation = "新增采购记录", exceptionMessage = "新增采购记录")
    public FebsResponse addCommodity(@Valid Procurement procurement) {
        this.iProcurementService.createProcurement(procurement);
        return new FebsResponse().success();
    }

    /*查找所有的商品 分页*/
    @GetMapping("select/list")
    @ControllerEndpoint(exceptionMessage = "获取采购记录")
    public List<Commodity> clientList1() {
        List<Commodity> list =  iProcurementService.CommodityName();
        return list;
    }

    /*修改采购商品信息*/
    @PostMapping("update")
    @RequiresPermissions("procurement:update")
    @ControllerEndpoint(operation = "修改采购记录", exceptionMessage = "修改采购记录失败")
       public FebsResponse updateProcurement(@Valid Procurement procurement){
            /*用对象点出ID 判断ID是否为空*/
        if (procurement.getProcurementId() == null)
            throw new FebsException("采购记录ID为空");
           /*如果ID不为空 业务层调修改商品信息的方法*/
        this.iProcurementService.updateProcurements(procurement);
        return new FebsResponse().success();
    }

    /*删除一个客户信息*/
    @GetMapping("delete/{procurementIds}")
    @RequiresPermissions("procurement:delete")
    @ControllerEndpoint(operation = "删除采购记录信息", exceptionMessage = "删除采购记录信息失败")
    public FebsResponse deleteProcurement(@NotBlank(message = "{required}") @PathVariable String procurementIds) {
        String[] ids = procurementIds.split(StringPool.COMMA);
        this.iProcurementService.deleteProcurement(ids);
        return new FebsResponse().success();
    }

    /*导出excel表*/
    @GetMapping("excel")
    @RequiresPermissions("procurement:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Procurement procurement, HttpServletResponse response) {
        List<Procurement> procurements = this.iProcurementService.findProcurementDetailPage(procurement, queryRequest).getRecords();
        ExcelKit.$Export(Procurement.class, response).downXlsx(procurements, false);
    }
}
