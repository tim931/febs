package cc.mrbird.febs.system.controller;

import cc.mrbird.febs.common.authentication.ShiroHelper;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.entity.*;
import cc.mrbird.febs.system.mapper.SellMapper;
import cc.mrbird.febs.system.service.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

/**
 * @author MrBird
 */
@Controller("systemView")
public class ViewController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ShiroHelper shiroHelper;
    @Autowired
    private IClientService iClientService;
    @Autowired
    private ICommodityService iCommodityService;
    @Autowired
    private IProcurementService iProcurementService;
    @Autowired
    private SellMapper sellMapper ;


    @GetMapping("login")
    @ResponseBody
    public Object login(HttpServletRequest request) {
        if (FebsUtil.isAjaxRequest(request)) {
            throw new ExpiredSessionException();
        } else {
            ModelAndView mav = new ModelAndView();
            mav.setViewName(FebsUtil.view("login"));
            return mav;
        }
    }

    @GetMapping("unauthorized")
    public String unauthorized(){
        return FebsUtil.view("error/403");
    }


    @GetMapping("/")
    public String redirectIndex() {
        return "redirect:/index";
    }

    @GetMapping("index")
    public String index(Model model) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentuserAuthorizationInfo();
        User user = super.getCurrentUser();
        User currentUserDetail = userService.findByName(user.getUsername());
        currentUserDetail.setPassword("It's a secret");
        model.addAttribute("user", currentUserDetail);
        model.addAttribute("permissions", authorizationInfo.getStringPermissions());
        model.addAttribute("roles", authorizationInfo.getRoles());
        return "index";
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "layout")
    public String layout() {
        return FebsUtil.view("layout");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "password/update")
    public String passwordUpdate() {
        return FebsUtil.view("system/user/passwordUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile")
    public String userProfile() {
        return FebsUtil.view("system/user/userProfile");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/avatar")
    public String userAvatar() {
        return FebsUtil.view("system/user/avatar");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "user/profile/update")
    public String profileUpdate() {
        return FebsUtil.view("system/user/profileUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user")
    @RequiresPermissions("user:view")
    public String systemUser() {
        return FebsUtil.view("system/user/user");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/sell")
    @RequiresPermissions("sell:view")
    public String systemSell() {
        return FebsUtil.view("system/sell/sell");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/client")
    @RequiresPermissions("client:view")
    public String systemClient() {
        return FebsUtil.view("system/client/client");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/commodity")
    @RequiresPermissions("commodity:view")
    public String systemCommodity() {
        return FebsUtil.view("system/commodity/commodity");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/car")
    @RequiresPermissions("car:view")
    public String systemCar() {
        return FebsUtil.view("system/car/car");
    }

    /*新增用户*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/add")
    /*要求subject中必须同时含有user:add的权限才能执行方法systemUserAdd()
            。否则抛出异常AuthorizationException。*/
    @RequiresPermissions("user:add")
    public String systemUserAdd() {
        /*调用工具类拿到视图前缀加上视图名跳转到添加页面*/
        return FebsUtil.view("system/user/userAdd");
    }

    /*新增商品*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/commodity/add")
    /*要求subject中必须同时含有user:add的权限才能执行方法systemUserAdd()
            。否则抛出异常AuthorizationException。*/
    @RequiresPermissions("commodity:add")
    public String systemCommodityAdd() {
        /*调用工具类拿到视图前缀加上视图名跳转到添加页面*/
        return FebsUtil.view("system/commodity/commodityAdd");
    }

    /*修改商品*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/commodity/update/{commodityId}")
    @RequiresPermissions("commodity:update")
    public String systemCommodityUpdate(@PathVariable Integer commodityId, Model model){
        resolveCommodityIdModel(commodityId,model);
        return FebsUtil.view("system/commodity/commodityUpdate");
    }

    /*新增销售记录*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/sell/add")
    /*要求subject中必须同时含有user:add的权限才能执行方法systemUserAdd()
            。否则抛出异常AuthorizationException。*/
    @RequiresPermissions("sell:add")
    public String systemSellAdd() {
        /*调用工具类拿到视图前缀加上视图名跳转到添加页面*/
        return FebsUtil.view("system/sell/sellAdd");
    }

    /*修改修改记录*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/sell/update/{sellId}")
    @RequiresPermissions("sell:update")
    public String systemSellUpdate(@PathVariable Integer sellId, Model model){
        resolveSellIModel(sellId,model);
        return FebsUtil.view("system/sell/sellUpdate");
    }

    /*新增采购商品*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/procurement/add")
    /*要求subject中必须同时含有client:add的权限才能执行方法systemClientAdd()
            。否则抛出异常AuthorizationException。*/
    @RequiresPermissions("procurement:add")
    public String systemProcurementAdd() {
        /*调用工具类拿到视图前缀加上视图名跳转到添加页面*/
        return FebsUtil.view("system/procurement/procurementAdd");
    }

    /*修改采购商品信息*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/procurement/update/{procurementId}")
    @RequiresPermissions("procurement:update")
    //@PathVariable可以用来映射URL中的占位符到目标方法的参数中
    public String systemProcurementUpdate(@PathVariable Integer procurementId, Model model) {
        resolveProcurementIdModel(procurementId,model);
        return FebsUtil.view("system/procurement/procurementUpdate");
    }


    /*新增客戶*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/client/add")
    /*要求subject中必须同时含有client:add的权限才能执行方法systemClientAdd()
            。否则抛出异常AuthorizationException。*/
    @RequiresPermissions("client:add")
    public String systemClientAdd() {
        /*调用工具类拿到视图前缀加上视图名跳转到添加页面*/
        return FebsUtil.view("system/client/clientAdd");
    }

    /*修改客戶*/
    @GetMapping(FebsConstant.VIEW_PREFIX + "system/client/update/{clientId}")
    @RequiresPermissions("client:update")
    public String systemClientUpdate(@PathVariable Integer clientId, Model model) {
        resolveClientModel(clientId,model);
        return FebsUtil.view("system/client/clientUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/update/{username}")
    @RequiresPermissions("user:update")
    public String systemUserUpdate(@PathVariable String username, Model model) {
        resolveUserModel(username, model, false);
        return FebsUtil.view("system/user/userUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/user/detail/{username}")
    @RequiresPermissions("user:view")
    public String systemUserDetail(@PathVariable String username, Model model) {
        resolveUserModel(username, model, true);
        return FebsUtil.view("system/user/userDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/role")
    @RequiresPermissions("role:view")
    public String systemRole() {
        return FebsUtil.view("system/role/role");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/procurement")
    @RequiresPermissions("procurement:view")
    public String systemProcurement() {
        return FebsUtil.view("system/procurement/procurement");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/menu")
    @RequiresPermissions("menu:view")
    public String systemMenu() {
        return FebsUtil.view("system/menu/menu");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "system/dept")
    @RequiresPermissions("dept:view")
    public String systemDept() {
        return FebsUtil.view("system/dept/dept");
    }

    @RequestMapping(FebsConstant.VIEW_PREFIX + "index")
    public String pageIndex() {
        return FebsUtil.view("index");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "404")
    public String error404() {
        return FebsUtil.view("error/404");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "403")
    public String error403() {
        return FebsUtil.view("error/403");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "500")
    public String error500() {
        return FebsUtil.view("error/500");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "car/carEximportResult")
    public String carEximportResult() {
        return FebsUtil.view("system/car/carEximportResult");
    }

    private void resolveUserModel(String username, Model model, Boolean transform) {
        User user = userService.findByName(username);
        model.addAttribute("user", user);
        if (transform) {
            String ssex = user.getSex();
            if (User.SEX_MALE.equals(ssex)) user.setSex("男1");
            else if (User.SEX_FEMALE.equals(ssex)) user.setSex("女1");
            else user.setSex("保密1");
        }
        if (user.getLastLoginTime() != null)
            model.addAttribute("lastLoginTime", DateUtil.getDateFormat(user.getLastLoginTime(), DateUtil.FULL_TIME_SPLIT_PATTERN));
    }


    private void resolveClientModel(Integer clientId , Model model) {
        Client client = iClientService.findById(clientId);
        model.addAttribute("client", client);
    }

    private void resolveCommodityIdModel(Integer commodityId , Model model) {
        //根据id查询出商品信息
        Commodity commodity = iCommodityService.findByIds(commodityId);
        model.addAttribute("commodity", commodity);
    }

    /*根据采购商品ID查找商品信息*/
    private void resolveProcurementIdModel(Integer procurementId , Model model) {
        Procurement procurement = iProcurementService.queryProcurementById(procurementId);
        model.addAttribute("procurement", procurement);
    }

    /*根据销售记录ID查找销售记录信息*/
    private void resolveSellIModel(Integer sellId , Model model) {
        Sell sell = sellMapper.selectSellById(sellId);
        SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd ");
        String date=dateformat.format(sell.getSellTime());
        sell.setSellData(date);
        model.addAttribute("sell", sell);
    }


}
