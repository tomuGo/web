package com.zhongkouwei.web.prc;

import com.zhongkouwei.user.client.UserClient;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.aop.RpcHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRpc {

    @Autowired
    UserClient userClient;

    @RpcHandler
    public UserInfo login(String account,String password){
        ResultSub<UserInfo>loginUser=userClient.login(account,password);
        RpcUtil.resultHandler(loginUser);
        return loginUser.getData();
    }

    @RpcHandler
    public UserInfo getUserInfoByUserId(Integer userId){
        ResultSub<UserInfo>userInfoResultSub=userClient.getUser(userId);
        return userInfoResultSub.getData();
    }

    @RpcHandler
    public Integer addUser(UserInfo userInfo){
        ResultSub<Integer>userid=userClient.addUser(userInfo);
        return userid.getData();
    }

    @RpcHandler
    public void updateUser(UserInfo userInfo,Integer userId){
        userClient.updateUser(userInfo,userId);
    }

    @RpcHandler
    public void updatePassword(Integer userId,String oldPassword,String newPassword){
        userClient.updatePassword(userId,oldPassword,newPassword);
    }



}
