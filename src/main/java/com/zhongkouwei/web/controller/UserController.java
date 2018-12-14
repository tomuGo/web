package com.zhongkouwei.web.controller;

import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.model.PasswordModel;
import com.zhongkouwei.web.prc.UserRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRpc userPrc;

    @RequestMapping(value = "users/{id}",method = RequestMethod.GET)
    public ResultSub<UserInfo> getUserInfoByUserId(@PathVariable("id")Integer userId){
        UserInfo userInfo=userPrc.getUserInfoByUserId(userId);
        return new ResultSub<>(userInfo);
    }

    @RequestMapping(value = "users/{id}",method = RequestMethod.POST)
    public ResultSub<Integer> addUser(@RequestBody UserInfo userInfo){
        Integer userId=userPrc.addUser(userInfo);
        return new ResultSub<>(userId);
    }

    @RequestMapping(value = "users/{id}",method = RequestMethod.PATCH)
    public ResultSub<Boolean> updateUser(@PathVariable("id")Integer userId,@RequestBody UserInfo userInfo){
        userPrc.updateUser(userInfo,userId);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "users/{id}",method = RequestMethod.PUT)
    public ResultSub<Boolean> updatePassword(@RequestBody PasswordModel passwordModel){
        Assert.notNull(passwordModel.getUserId(),"用户不能为空");
        Assert.notNull(passwordModel.getNewPassword(),"新密码不能为空");
        Assert.notNull(passwordModel.getOldPassword(),"旧密码不能为空");
        userPrc.updatePassword(passwordModel.getUserId(),passwordModel.getOldPassword(),passwordModel.getNewPassword());
        return new ResultSub<>(Boolean.TRUE);
    }




}
