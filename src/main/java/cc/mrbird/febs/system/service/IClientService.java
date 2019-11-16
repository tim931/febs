package cc.mrbird.febs.system.service;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.Client;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zt
 */
public interface IClientService extends IService<Client> {

    /**
     * 通过客户名称查找客户信息
     *
     * @param clientName 客户名称
     * @return 客户
     */
    Client findByName(String clientName);

    /**
     * 通过客户id查找客户信息
     *
     * @param clientId 客户id
     * @return 客户
     */
    Client findById(Integer clientId);


    /**
     * 查找客户详细信息
     *
     * @param request request
     * @param client   客户对象，用于传递查询条件
     * @return IPage
     */
    IPage<Client> findClientDetail(Client client, QueryRequest request);

    /**
     * 通过客户名称查找客户详细信息
     *
     * @param clientName 客户名称
     * @return 客户信息
     */
    Client findClientDetail(String clientName);

    /**
     * 新增客户
     *
     * @param  client
     */
    void createClient(Client client);

    /**
     * 删除客户
     *
     * @param clientIds 用户 id数组
     */
    void deleteClientIds(String[] clientIds);

    /**
     * 修改客户信息
     *
     * @param  client
     */
    void updateClient(Client client);

}
