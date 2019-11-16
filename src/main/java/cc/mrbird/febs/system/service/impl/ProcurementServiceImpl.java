package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.entity.Procurement;
import cc.mrbird.febs.system.mapper.CommodityMapper;
import cc.mrbird.febs.system.mapper.ProcurementMapper;
import cc.mrbird.febs.system.service.IProcurementService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ProcurementServiceImpl extends ServiceImpl<ProcurementMapper, Procurement> implements IProcurementService {

   @Autowired
   private ProcurementMapper procurementMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public List<Commodity> CommodityName() {
        return procurementMapper.queryCommodityName();
    }

    @Override
    /*根据ID查找商品采购信息*/
    public Procurement queryProcurementById(Integer procurementId) {
        return procurementMapper.findProcurementById(procurementId);
    }

    @Override
    public Procurement findByName(String procurementName) {
        return null;
    }

    /*查找采购商品查找所有商品分页信息*/
    @Override
    public IPage<Procurement> findProcurementDetailPage(Procurement procurement, QueryRequest request) {
        Page<Procurement> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "procurementId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findProcurementDetailPage(page,procurement);
    }

    /*查找采购记录信息*/
    @Override
    public List<Procurement> findProcurementDetail(Procurement procurement) {
        return baseMapper.findProcurementDetail(procurement);
    }

   /* 新增采购记录*/
    @Override
    public void createProcurement(Procurement procurement) {
        //查询商品信息
        Commodity commodity = commodityMapper.selectById(procurement.getCommodityId());
        //获取商品数量
        Integer commodityQuantity = commodity.getCommodityQuantity();
        if(procurement.getProcurementCount()>0){
            //拿到商品原来数量加上采购数量
            commodityQuantity += procurement.getProcurementCount();
            //把新的商品数量赋值给商品实体类
            commodity.setCommodityQuantity(commodityQuantity);
            //设置修改时间
            commodity.setModifyTime(new Date());
            //修改商品表
            commodityMapper.updateById(commodity);
            //新增采购记录
            procurement.setProcurementTime(new Date());
            save(procurement);
        }else {
            throw new FebsException("商品数量不能小于等于0");
        }

    }


    /*修改采购记录*/
    @Override
    public void updateProcurements(Procurement procurement){
        //获取文本框商品数量
        Commodity commodity = commodityMapper.findCommodityName(procurement.getCommodityName());
        //获取商品数量
        Integer   commodityQuantity = commodity.getCommodityQuantity();
        //获取修改之前的原商品采购数量
        Procurement pt = procurementMapper.findProcurementById(procurement.getProcurementId());
        Integer  pp=pt.getProcurementCount();
        Integer pc=procurement.getProcurementCount();
          if(pc>0) {
              //拿到原来的商品数量加上(之前的采购数量减去修改的采购数量)
              commodityQuantity += (pc-pp);
              //把新的商品数量赋值给商品实体类
              commodity.setCommodityQuantity(commodityQuantity);
              //设置修改时间
              commodity.setModifyTime(new Date());
              //修改商品表
              commodityMapper.updateById(commodity);
              //修改后的采购记录
              procurement.setProcurementTime(new Date());
              procurementMapper.UpdateProcurement(procurement);

          }else {
              throw new FebsException("商品数量不能小于等于0");
          }
    }


   //*删除采购记录
   @Override
    public void deleteProcurement(String[] procurementId){
       for(String proId:procurementId){
            //循环遍历出采购商品Id，根据id查找出采购记录
             Procurement procurement=baseMapper.selectById(proId);
            //根据采购Id查找采购数量
            Integer procurementCount=procurement.getProcurementCount();
             //根据采购记录商品Id查询出商品信息
             Commodity commodity=commodityMapper.selectById(procurement.getCommodityId());
             //根据商品信息查找商品数量
            Integer commodityQuantity = commodity.getCommodityQuantity();
            //商品数量减出删除的采购记录数量时
            commodityQuantity-=procurementCount;
            //把新的商品数量赋值给商品实体类
            commodity.setCommodityQuantity(commodityQuantity);
            //设置修改时间
            commodity.setModifyTime(new Date());
            //修改商品表信息
            commodityMapper.updateById(commodity);
        }
        List<String> list = Arrays.asList(procurementId);
        baseMapper.deleteBatchIds(list);
    }




}
