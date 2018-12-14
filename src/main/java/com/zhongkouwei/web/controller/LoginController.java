package com.zhongkouwei.web.controller;

import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.RoleInfo;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.app.AppConstants;
import com.zhongkouwei.web.component.RedisComponent;
import com.zhongkouwei.web.prc.RoleRpc;
import com.zhongkouwei.web.prc.UserRpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    UserRpc userPrc;
    @Autowired
    RedisComponent redisComponent;
    @Autowired
    RoleRpc roleRpc;

    @RequestMapping(value = "login",method = RequestMethod.GET)
    public ResultSub<UserInfo> login(HttpServletResponse response, @RequestParam("account") String account, @RequestParam("password") String password){
        UserInfo userInfo=userPrc.login(account,password);
        String token=login(userInfo);
        response.addHeader(AppConstants.HEADER_TOKEN,token);
        return new ResultSub<>(userInfo);
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public ResultSub<Boolean> logout(HttpServletRequest request){
        String token=request.getHeader(AppConstants.HEADER_TOKEN);
        List<String>sessionKeys=new ArrayList<>();
        sessionKeys.add(AppConstants.HEADER_TOKEN+token);
        sessionKeys.add(AppConstants.SESSION_USER+token);
        sessionKeys.add(AppConstants.SESSION_ROLE+token);
        redisComponent.del(sessionKeys);
        return new ResultSub<>(Boolean.TRUE);
    }

    @RequestMapping(value = "userInfo",method = RequestMethod.GET)
    public ResultSub<Map> getUserInfoByToken(HttpServletRequest request){
        String token=request.getHeader(AppConstants.HEADER_TOKEN);
        //todo
        return null;
    }

    private String login(UserInfo userInfo){
        String token=UUID.randomUUID().toString();
        redisComponent.set(AppConstants.HEADER_TOKEN+token,"");
        redisComponent.set(AppConstants.SESSION_USER+token,userInfo);
        if(AppConstants.IS_MANAGE.equals(userInfo.getIsManage())){
            List<RoleInfo>roleInfos=roleRpc.getRoleInfoByUserId(userInfo.getUserId());
            redisComponent.set(AppConstants.SESSION_ROLE+token,roleInfos);
        }
        return token;
    }



}
