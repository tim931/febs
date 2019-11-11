package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Commodity;
import cc.mrbird.febs.system.mapper.CommodityMapper;
import cc.mrbird.febs.system.service.ICommodityService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author zt
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CommodityServiceImpl extends ServiceImpl<CommodityMapper, Commodity> implements ICommodityService {

    /*通过商品id查找商品信息*/
    @Override
    public Commodity findByIds(Integer commodityId) {
        return baseMapper.selectById(commodityId);
    }

    /*通过商品名称名查找商品信息信息*/
    @Override
    public Commodity findCommodityName(String commodity_name) {
        return baseMapper.findCommodityName(commodity_name);
    }

    /*查找商品详细信息  分页*/
    @Override
    public IPage<Commodity> findCommodityDetailPage( Commodity commodity,QueryRequest request) {
        Page<Commodity> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "commodityId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findCommodityDetailPage(page,commodity);
    }


    /*新增商品*/
    @Override
    public void createCommodity(Commodity commodity) {
        save(commodity);
    }

    /*删除商品*/
    @Override
    public void deleteCommoditys(String[] commodityId) {
        List<String> list = Arrays.asList(commodityId);
        // 删除用户
        this.removeByIds(list);
    }

    /*修改商品*/
    @Override
    @Transactional
    public void updateCommodity(Commodity commodity) {
        baseMapper.updateById(commodity);
    }
}
