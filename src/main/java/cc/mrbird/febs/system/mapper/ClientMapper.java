package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.system.entity.Client;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author zt
 */

public interface ClientMapper extends BaseMapper<Client> {

    /**
     * 通过客户名称名查找客户信息
     *
     * @param clientName 客户名称
     * @return 客户信息
     */
    Client findByName(String clientName);

    /**
     * 查找客户详细信息
     *
     * @param page   分页对象
     * @param client 客户对象，用于传递查询条件
     * @return Ipage
     */
    IPage<Client> findClientDetailPage(Page page, @Param("client") Client client);

    /**
     * 查找客户详细信息
     *
     * @param client 客户对象，用于传递查询条件
     * @return List<Client>
     */
    List<Client> findClientDetail(@Param("client") Client client);

    /**
     * 批量删除客户信息
     *
     * @param idList 客户ID集合，用于传递删除条件
     */
     boolean deleteListClient(@Param("idLis")List idList);


    /**
     *  修改客户信息
     *
     * @param client 客户对象，用于传递删除条件
     */
    boolean updateClient(Client client);

}
