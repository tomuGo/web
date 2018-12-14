package com.zhongkouwei.web.prc;

import com.zhongkouwei.user.client.RoleClient;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.RoleInfo;
import com.zhongkouwei.web.aop.RpcHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRpc {

    @Autowired
    RoleClient roleClient;

    @RpcHandler
    public List<RoleInfo> getRoleInfoByUserId(Integer userId){
        ResultSub<List<RoleInfo>>resultSub=roleClient.getRoleInfosByUserId(userId);
        return resultSub.getData();
    }

}
