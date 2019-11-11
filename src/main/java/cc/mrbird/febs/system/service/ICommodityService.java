package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Commodity;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */
public interface ICommodityService extends IService<Commodity> {

    /**
     * 通过商品id查找商品信息
     *
     * @param commodityId 商品id
     * @return 商品
     */
    Commodity findByIds(Integer commodityId);



    /**
     * 通过商品名称名查找商品信息信息
     *
     * @param commodity_name 商品名称
     * @return 客户信息
     */
    Commodity findCommodityName(String commodity_name);


    /**
     * 查找商品详细信息
     *
     * @param page 分页对象
     * @param commodity 商品对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Commodity> findCommodityDetailPage( @Param("commodity") Commodity commodity,QueryRequest request);


    /**
     * 新增商品
     *
     * @param Commodity commodity
     */
    void createCommodity(Commodity commodity);

    /**
     * 删除商品
     *
     * @param commodityId 用户 id数组
     */
    void deleteCommoditys(String[] commodityId);

    /**
     * 修改商品信息
     *
     * @param Commodity commodity
     */
    void updateCommodity(Commodity commodity);


}
