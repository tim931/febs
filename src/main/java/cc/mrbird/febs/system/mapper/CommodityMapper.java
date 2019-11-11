package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Commodity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommodityMapper extends BaseMapper<Commodity> {

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
    IPage<Commodity> findCommodityDetailPage(Page page, @Param("commodity") Commodity commodity);


    /**
     * 查找商品详细信息
     *
     * @param commodity 商品对象，用于传递查询条件
     * @return List<Commodity>
     */
    List<Commodity> findCommodityDetail(@Param("commodity") Commodity commodity);

}
