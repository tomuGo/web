package com.zhongkouwei.web.prc;

import com.zhongkouwei.user.client.UserClient;
import com.zhongkouwei.user.common.model.PasswordModel;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRpc {

    @Autowired
    UserClient userClient;

    public UserInfo login(String account,String password){
        ResultSub<UserInfo>loginUser=userClient.login(account,password);
        RpcUtil.resultHandler(loginUser);
        return loginUser.getData();
    }

    public UserInfo getUserInfoByUserId(Integer userId){
        ResultSub<UserInfo>userInfoResultSub=userClient.getUser(userId);
        RpcUtil.resultHandler(userInfoResultSub);
        return userInfoResultSub.getData();
    }

    public Integer addUser(UserInfo userInfo){
        ResultSub<Integer>userid=userClient.addUser(userInfo);
        RpcUtil.resultHandler(userid);
        return userid.getData();
    }

    public void updateUser(UserInfo userInfo,Integer userId){
        ResultSub resultSub=userClient.updateUser(userInfo,userId);
        RpcUtil.resultHandler(resultSub);
    }

    public void updatePassword(PasswordModel passwordModel){
        ResultSub resultSub=userClient.updatePassword(passwordModel);
        RpcUtil.resultHandler(resultSub);
    }



}
