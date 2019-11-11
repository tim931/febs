package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Client;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zt
 */

public interface ClientMapper extends BaseMapper<Client> {

    /**
     * 通过客户名称名查找客户信息
     * @param clientName 客户名称
     * @return 客户信息
     */
    Client findByName(String clientName);

    /**
     * 查找客户详细信息
     *
     * @param page 分页对象
     * @param client 客户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Client> findClientDetailPage(Page page, @Param("client") Client client);

    /**
     * 查找用户详细信息
     *
     * @param client 客户对象，用于传递查询条件
     * @return List<User>
     */
    List<Client> findClientDetail(@Param("client") Client client);


}
