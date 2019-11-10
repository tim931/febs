package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */
public interface ICarService extends IService<Car>{

    /**
     * 通过车牌号查找车辆信息
     * */
    List<Car> findCar(String licensePlate);

    /**
     * 增加车辆信息
     * */
    void addCar(Car car);

    /**
     * 根据id删除车辆信息
     * */
    void delCar(String carIds);

    /**
     * 修改车辆
     *
     * @param car 待修改的车辆
     */
    void updateCar(Car car);

    /**
     * 查找车辆信息
     ** @param car   汽车对象（用于传递查询条件）
     * @param request request
     * @return IPage
     */
    IPage<Car> findCarPage( Car car ,QueryRequest request);

}
