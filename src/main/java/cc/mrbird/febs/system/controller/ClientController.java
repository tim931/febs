package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.system.entity.Client;
import cc.mrbird.febs.system.service.IClientService;
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
@RequestMapping("client")
public class ClientController extends BaseController {

    @Autowired
    private IClientService iClientService;

    /*查找所有的用户 分页*/
    @GetMapping("list")
    @RequiresPermissions("client:view")
    public FebsResponse clientList(Client client, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.iClientService.findClientDetail(client, request));
        return new FebsResponse().success().data(dataTable);
    }

    /* 新增一条客户信息*/
    @PostMapping
    @RequiresPermissions("client:add")
    @ControllerEndpoint(operation = "新增客户", exceptionMessage = "新增客户失败")
    public FebsResponse addClient(@Valid Client client) {
        this.iClientService.createClient(client);
        return new FebsResponse().success();
    }


    /*删除一个客户信息*/
    @GetMapping("delete/{clientIds}")
    @RequiresPermissions("client:delete")
    @ControllerEndpoint(operation = "删除客户信息", exceptionMessage = "删除客户信息失败")
    public FebsResponse deleteClients(@NotBlank(message = "{required}") @PathVariable String clientIds) {
        String[] ids = clientIds.split(StringPool.COMMA);
        this.iClientService.deleteClientIds(ids);
        return new FebsResponse().success();
    }

    @PostMapping("update")
    @RequiresPermissions("client:update")
    @ControllerEndpoint(operation = "修改客户信息", exceptionMessage = "修改客户失败")
    public FebsResponse updateClient(@Valid Client client) {
        if (client.getClientId() == null)
            throw new FebsException("客户ID为空");
        this.iClientService.updateClient(client);
        return new FebsResponse().success();
    }

    /*导出excel表*/
    @GetMapping("excel")
    @RequiresPermissions("client:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Client client, HttpServletResponse response) {
        List<Client> clients = this.iClientService.findClientDetail(client, queryRequest).getRecords();
        ExcelKit.$Export(Client.class, response).downXlsx(clients, false);
    }

}
