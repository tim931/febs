package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.DeptTree;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.MD5Util;
import cc.mrbird.febs.system.entity.Client;
import cc.mrbird.febs.system.entity.Dept;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IClientService;
import cc.mrbird.febs.system.service.IUserService;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @GetMapping("list")
    @RequiresPermissions("client:view")
    public FebsResponse clientList(Client client, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.iClientService.findClientDetail(client,request));
        return new FebsResponse().success().data(dataTable);
    }

    @PostMapping
    @RequiresPermissions("client:add")
    @ControllerEndpoint(operation = "新增客户", exceptionMessage = "新增客户失败")
    public FebsResponse addClient(@Valid Client client) {
        this.iClientService.createClient(client);
        return new FebsResponse().success();
    }





}
