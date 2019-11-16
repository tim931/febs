package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.entity.Procurement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */
public interface ProcurementMapper extends BaseMapper<Procurement> {

    /*  /**
     * 通过采购商品名称名查找采购商品信息
     * @param procurementName 采购商品名称
     * @return 采购商品信息
     *//*
    Procurement findByName(String commodityName);
*/

    /*查询所有的商品名称*/
    List<Commodity> queryCommodityName();

    /*根据ID查找所有商品信息*/
    Procurement findProcurementById(Integer procurementId);

   /**
     * 修改采购商品信息
     */
    void UpdateProcurement(Procurement procurement);

    /**
     * 查找采购商品分页信息
     *
     * @param page        分页对象
     * @param procurement 采购商品对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Procurement> findProcurementDetailPage(Page page, @Param("procurement") Procurement procurement);

    /**
     * 查找采购商品信息
     *
     * @param procurement 采购商品对象，用于传递查询条件
     * @return List<User>
     */
    List<Procurement> findProcurementDetail(@Param("procurement") Procurement procurement);

}
