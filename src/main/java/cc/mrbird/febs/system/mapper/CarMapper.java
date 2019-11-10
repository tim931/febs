package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Car;
import cc.mrbird.febs.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */

public interface CarMapper extends BaseMapper<Car> {

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
      void delCar(Long id);

    /**
     * 查找角色详情
     *
     * @param page 分页
     * @param car 车辆
     * @return IPage<car>
     */
    IPage<Car> findCarPage(Page page, @Param("car") Car car);

}



