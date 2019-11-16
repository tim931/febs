package cc.mrbird.febs.system.service;


import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.entity.Procurement;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */


public interface IProcurementService extends IService<Procurement> {

    /*查询所有的采购记录名称*/
    List<Commodity> CommodityName();

 /*根据采购商品Id查找商品信息*/
  Procurement queryProcurementById(Integer procurementId);

    /**
     * 通过采购商品名称名查找采购商品信息
     * @param procurementName 采购商品名称
     * @return 采购记录信息
     */
    Procurement findByName(String procurementName);

    /**
     * 查找采购商品查找所有商品分页信息
     * @param request request
     * @param procurement 采购商品对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Procurement> findProcurementDetailPage(Procurement procurement,QueryRequest request);

    /**
     * 查找采购商品信息
     *
     * @param procurement 采购商品对象，用于传递查询条件
     * @return List<User>
     */
    List<Procurement> findProcurementDetail( Procurement procurement);


    /**
     * 新增采购记录
     *
     * @param  procurement
     */
    void createProcurement(Procurement procurement);

     /**
     * 删除采购记录
     *
     * @param procurementId 采购记录id数组
     */
    void deleteProcurement(String[] procurementId);

    /**
     * 修改采购记录
     *
     * @param  procurement
     */
    void updateProcurements(Procurement procurement);





}
