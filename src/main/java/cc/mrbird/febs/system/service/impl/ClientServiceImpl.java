package cc.mrbird.febs.system.service.impl;


import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.system.entity.Client;
import cc.mrbird.febs.system.mapper.ClientMapper;
import cc.mrbird.febs.system.service.IClientService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zt
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements IClientService {


    /*通过客户名称查找客户信息*/
    @Override
    public Client findByName(String clientName) {
        return baseMapper.findByName(clientName);
    }

    @Override
    public List<Client> selectClientList() {
        return baseMapper.selectClientList();
    }


    /*通过Id称查找客户信息*/
    @Override
    public Client findById(Integer clientId) {
        return baseMapper.selectById(clientId);
    }

    /*查找客户详细信息*/
    @Override
    public IPage<Client> findClientDetail(Client client, QueryRequest request) {
        Page<Client> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "clientId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findClientDetailPage(page,client);
    }

     /** 通过客户名称查找客户详细信息*/
    @Override
    public Client findClientDetail(String clientName) {
        Client param = new Client();
        param.setClientName(clientName);
        List<Client> clients = baseMapper.findClientDetail(param);
        return CollectionUtils.isNotEmpty(clients) ? clients.get(0) : null;
    }

    /*新增客户*/
    @Override
    public void createClient(Client client) {
        client.setCreateTime(new Date());
        save(client);
    }

    /** 删除客户*/
    @Override
    public void deleteClientIds(String[] clientIds) {
        List<String> list = Arrays.asList(clientIds);
        // 删除用户
        try
        {
            baseMapper.deleteListClient(list);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

     /* * 修改客户信息*/
    @Override
    @Transactional
    public void updateClient(Client client) {
        client.setModifyTime(new Date());
        baseMapper.updateClient(client);
    }
}
