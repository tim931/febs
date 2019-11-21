package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Sell;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author MrBird
 */

public interface ISellService {

    /**
     * 查找销售详细信息
     *
     * @param request
     * @param sell   销售对象，用于传递查询条件
     * @return IPage
     */
    IPage<Sell> findSellDetail(Sell sell, QueryRequest request);

    /**
     * 增加销售记录
     * @param sell 销售记录对象，用于传递条件
     * @return
     */
    void addSell(Sell sell);

    /**
     * 删除销售记录
     *
     * @param sellId 采购记录id数组
     */
    void deleteSell(String[] sellId);

    /**
     * 修改销售记录
     */
     void updateSell(Sell sell);

}
