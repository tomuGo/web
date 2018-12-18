package com.zhongkouwei.web.controller;

import com.zhongkouwei.user.common.model.PasswordModel;
import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.aop.TokenHandler;
import com.zhongkouwei.web.model.RegisterUser;
import com.zhongkouwei.web.prc.UserRpc;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRpc userPrc;

    @TokenHandler
    @RequestMapping(value = "users/{id}",method = RequestMethod.GET)
    public ResultSub<UserInfo> getUserInfoByUserId(@PathVariable("id")Integer userId){
        UserInfo userInfo=userPrc.getUserInfoByUserId(userId);
        return new ResultSub<>(userInfo);
    }

    @RequestMapping(value = "register",method = RequestMethod.POST)
    public ResultSub<Integer> addUser(@RequestBody RegisterUser registerUser){
        Boolean boo=StringUtils.isEmpty(registerUser.getEmail()) && StringUtils.isEmpty(registerUser.getPhone())
                && StringUtils.isEmpty(registerUser.getUsername());
        Assert.isTrue(!boo,"用户名为空");
        Assert.isTrue(!StringUtils.isEmpty(registerUser.getPassword()),"密码为空");
        UserInfo userInfo=new UserInfo();
        BeanUtils.copyProperties(registerUser,userInfo);
        userInfo.setIsManage((byte)0);
        Integer userId=userPrc.addUser(userInfo);
        return new ResultSub<>(userId);
    }

    @TokenHandler
    @RequestMapping(value = "users/{id}",method = RequestMethod.PUT)
    public ResultSub<Boolean> updateUser(@PathVariable("id")Integer userId,@RequestBody UserInfo userInfo){
        userPrc.updateUser(userInfo,userId);

        return new ResultSub<>(Boolean.TRUE);
    }

    @TokenHandler
    @RequestMapping(value = "users/updatePassword",method = RequestMethod.PUT)
    public ResultSub<Boolean> updatePassword(@RequestBody PasswordModel passwordModel){
        Assert.notNull(passwordModel.getUserId(),"用户不能为空");
        Assert.notNull(passwordModel.getNewPassword(),"新密码不能为空");
        Assert.notNull(passwordModel.getOldPassword(),"旧密码不能为空");
        userPrc.updatePassword(passwordModel);
        return new ResultSub<>(Boolean.TRUE);
    }




}
