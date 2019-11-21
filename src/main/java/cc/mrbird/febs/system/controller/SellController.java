package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.*;
import cc.mrbird.febs.system.service.IClientService;
import cc.mrbird.febs.system.service.IProcurementService;
import cc.mrbird.febs.system.service.ISellService;
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
 * @author: zt
 */
@Slf4j
@Validated
@RestController
@RequestMapping("sell")
public class SellController extends BaseController {

    @Autowired
    private ISellService iSellService;
    @Autowired
    private IProcurementService iProcurementService;
    @Autowired
    private IClientService iClientService;

    /**
     * 查询所有的销售记录  分页
     */
    @GetMapping("list")
    @RequiresPermissions("sell:view")
    public FebsResponse selectAllSell(Sell sell, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(iSellService.findSellDetail(sell, request));
        return new FebsResponse().success().data(dataTable);
    }

    /**
     * 查找所有的商品
     */
    @GetMapping("select/list")
    @ControllerEndpoint(exceptionMessage = "获取商品名")
    public List<Commodity> commodityList() {
        List<Commodity> list = iProcurementService.CommodityName();
        return list;
    }

    /**
     * 查找所有的客户
     */
    @GetMapping("select/listClient")
    @ControllerEndpoint(exceptionMessage = "获取客户姓名")
    public List<Client> clientList() {
        List<Client> listClient = iClientService.selectClientList();
        return listClient;
    }

    /**
     * 新增销售记录
     */
    @PostMapping("add")
    @RequiresPermissions("sell:add")
    @ControllerEndpoint(operation = "新增销售记录", exceptionMessage = "新增销售记录失败！")
    public FebsResponse addSell(@Valid Sell sell) {
        iSellService.addSell(sell);
        return new FebsResponse().success();
    }

    /**
     * 删除销售记录
     */
    @GetMapping("delete/{sellIds}")
    @RequiresPermissions("sell:delete")
    @ControllerEndpoint(operation = "删除销售记录", exceptionMessage = "删除销售记录失败！")
    public FebsResponse deleteSell(@NotBlank(message = "{required}") @PathVariable String sellIds) {
        String[] ids = sellIds.split(StringPool.COMMA);
        iSellService.deleteSell(ids);
        return new FebsResponse().success();
    }

    /**
     * 修改销售记录
     **/
    @PostMapping("update")
    @RequiresPermissions("sell:update")
    @ControllerEndpoint(operation = "修改销售记录", exceptionMessage = "修改销售记录失败！")
    public FebsResponse updateProcurement(@Valid Sell sell) {
        /*用对象点出ID 判断ID是否为空*/
        if (sell.getSellId() == null)
            throw new FebsException("销售ID为空");
        /*如果ID不为空 业务层调修改商品信息的方法*/
        iSellService.updateSell(sell);
        return new FebsResponse().success();
    }

    /*导出excel表*/
    @GetMapping("excel")
    @RequiresPermissions("sell:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Sell sell, HttpServletResponse response) {
        List<Sell> sells = this.iSellService.findSellDetail(sell, queryRequest).getRecords();
        ExcelKit.$Export(Sell.class, response).downXlsx(sells, false);
    }

}
