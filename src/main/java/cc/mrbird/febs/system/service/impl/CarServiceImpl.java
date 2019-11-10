package cc.mrbird.febs.system.service.impl;

import cc.mrbird.febs.common.authentication.ShiroRealm;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Role;
import cc.mrbird.febs.system.entity.RoleMenu;
import cc.mrbird.febs.system.mapper.CarMapper;
import cc.mrbird.febs.system.mapper.RoleMapper;
import cc.mrbird.febs.system.service.ICarService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * @author zt
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements ICarService {

     @Autowired
     private ICarService iCarService;

    @Override
    /*通过车牌号查找车辆信息*/
    public List<Car> findCar(String licensePlate) {
        List<Car> list=iCarService.findCar(licensePlate);
        return list;
    }

    @Override
    @Transactional
    /*增加车辆信息*/
    public void addCar(Car car) {
        car.setCreateTime(new Date());
        this.baseMapper.insert(car);
    }

    @Override
    @Transactional
    /*根据id删除车辆信息*/
    public void delCar(String carIds) {
        List<String> list = Arrays.asList(carIds.split(StringPool.COMMA));
        baseMapper.delete(new QueryWrapper<Car>().lambda().in(Car::getCarId, list));

    }

    @Override
    /*修改车辆信息*/
    public void updateCar(Car car) {
        car.setModifyTime(new Date());
        baseMapper.updateById(car);
    }

    @Override
    /*查找所有车辆信息  分页*/
    public IPage<Car> findCarPage(Car car, QueryRequest request) {
        Page<Car> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", FebsConstant.ORDER_DESC, false);
        return baseMapper.findCarPage(page, car);
    }



}
