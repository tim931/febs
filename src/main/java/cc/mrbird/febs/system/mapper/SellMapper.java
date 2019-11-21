package cc.mrbird.febs.system.mapper;


import cc.mrbird.febs.system.entity.Sell;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */
public interface SellMapper extends BaseMapper<Sell> {

    /**
     * 查找销售记录信息
     *
     * @param sellId  传递条件
     * @return sell
     */
     Sell selectSellById(Integer sellId);

    /**
     * 查找销售记录分页信息
     *
     * @param page        分页对象
     * @param sell 销售记录对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Sell> findSellDetailPage(Page page, @Param("sell") Sell sell);

    /**
     * 修改销售记录
     * @param sell 销售记录对象，用于传递条件
     * @return
     */
    void updateSell(Sell sell);

    /**
     * 删除销售记录
     * @param list 销售记录id，用于传递条件
     * @return
     */
    void deleteSell(List<String> list);

    /**
     * 增加销售记录
     * @param sell 销售记录对象，用于传递条件
     * @return
     */
    void addSell(Sell sell);

}
