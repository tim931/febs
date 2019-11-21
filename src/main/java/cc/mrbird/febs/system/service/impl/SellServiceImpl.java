package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.entity.Sell;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.mapper.CommodityMapper;
import cc.mrbird.febs.system.mapper.SellMapper;
import cc.mrbird.febs.system.service.ISellService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static cc.mrbird.febs.common.utils.FebsUtil.getCurrentUser;

/**
 * @author: zt
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SellServiceImpl implements ISellService {

    @Autowired
    private SellMapper sellMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 查询所有的销售记录
     */
    @Override
    public IPage<Sell> findSellDetail(Sell sell, QueryRequest request) {
        Page<Sell> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "sellId", FebsConstant.ORDER_ASC, false);
        return sellMapper.findSellDetailPage(page, sell);
    }

    /**
     * 增加一条销售记录
     */
    @Override
    public void addSell(Sell sell) {
        try{
            //页面传递String类型的时间值转换为Date格式
            String sj=sell.getSellData();
            //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            //使用SimpleDateFormat的parse()方法生成Date
            Date date = sf.parse(sj);
            //把date赋值给实体类
            sell.setSellTime(date);
            //查询商品信息
            Commodity commodity = commodityMapper.selectById(sell.getCommodityId());
            //查询商品数量
            Integer  cCount = commodity.getCommodityQuantity();
            //商品数量减去销售出去的商品数量
            cCount-=sell.getSellCount();
            //把新的商品数量重新赋值给实体类
            commodity.setCommodityQuantity(cCount);
            //设置修改时间
            commodity.setModifyTime(new Date());
            //修改商品表
            commodityMapper.updateById(commodity);
            //获取用户id
            User currentUser = getCurrentUser();
            sell.setCreateUserId(currentUser.getUserId());
            //新增销售记录、创建时间
            sell.setCreateTime(new Date());
            sellMapper.addSell(sell);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
      * 删除销售记录
     * */
    @Override
    public void deleteSell(String[] sellId) {
        try{
            for(String seId : sellId ){
                //循环遍历出销售记录Id，根据id查找出销售记录
                Sell sell= sellMapper.selectById(seId);
                //查出销售的商品数量
                Integer sCount=sell.getSellCount();
                //根据销售商品Id查出商品信息
                Commodity commodity=commodityMapper.selectById(sell.getCommodityId());
                //根据商品对象查出商品数量
                Integer commodityQuantity=commodity.getCommodityQuantity();
                //原有的商品数量减去销售出去的商品数量
                commodityQuantity+=sCount;
                //把新的商品数量赋值给商品实体类
                commodity.setCommodityQuantity(commodityQuantity);
                //设置修改时间
                commodity.setModifyTime(new Date());
                //修改商品表信息
                commodityMapper.updateById(commodity);
            }
            List<String> list = Arrays.asList(sellId);
            sellMapper.deleteSell(list);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /** 修改销售记录*/
    @Override
    public void updateSell(Sell sell) {
        // 获取商品对象
        Commodity commodity=commodityMapper.selectById(sell.getCommodityId());
        // 获取商品数量
        Integer commodityQuantity=commodity.getCommodityQuantity();
        //获取修改的商品数量
        Integer sCount = sell.getSellCount();
        //获取修改之前的原商品数量
        Sell sl= sellMapper.selectSellById(sell.getSellId());
          Integer yCount=sl.getSellCount();
          if(sCount>0){
              commodityQuantity+=(yCount-sCount);
             //把新的商品数量重新赋值给实体类
             commodity.setCommodityQuantity(commodityQuantity);
             //设置修改时间
              commodity.setModifyTime(new Date());
              //修改商品表
              commodityMapper.updateById(commodity);
              //获取用户id
              User currentUser = getCurrentUser();
              sell.setModifyUserId(currentUser.getUserId());
              //设置销售记录修改时间
              sell.setModifyTime(new Date());
              //修改后的采购记录
              sell.setSellTime(new Date());
              sellMapper.updateSell(sell);
          }else{
              throw new FebsException("商品数量不能小于等于0");
          }






    }


}
