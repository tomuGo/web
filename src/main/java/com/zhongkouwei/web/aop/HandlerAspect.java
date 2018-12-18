package com.zhongkouwei.web.aop;

import com.zhongkouwei.user.common.model.ResultSub;
import com.zhongkouwei.user.common.model.UserInfo;
import com.zhongkouwei.web.app.AppConstants;
import com.zhongkouwei.web.component.CurrentUserComponent;
import com.zhongkouwei.web.component.RedisComponent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangyiming1031@aliyun.com
 * @Description
 * @Date 2018/11/19 10:19
 **/
@Aspect
@Slf4j
@Component
public class HandlerAspect {

    @Pointcut("@annotation(com.zhongkouwei.web.aop.RpcHandler)")
    public void RpcHandler() {
    }

    @Pointcut("execution(public * com.zhongkouwei.web.controller.*.*(..))&&@annotation(com.zhongkouwei.web.aop.TokenHandler)")
    public void TokenHandler() {
    }

    @Autowired
    RedisComponent redisComponent;

    @Around("TokenHandler()")
    public Object tokenArround(ProceedingJoinPoint pjp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader(AppConstants.HEADER_TOKEN);
        System.out.println("token:" + token);
        if (StringUtils.isEmpty(token) || !redisComponent.exits(AppConstants.SESSION_TOKEN + token)) {
            return errorHandler(401, "登陆失效");
        }
        UserInfo userInfo = redisComponent.getUserInfoFromRedis(AppConstants.SESSION_USER + token);
        if (userInfo == null) {
            return errorHandler(401, "登陆失效");
        }
        CurrentUserComponent.setCurrentUser(userInfo);
        try {
            Object obj = pjp.proceed();
            return obj;
        } catch (Throwable e) {
            log.error("异常: {}", e);
            return errorHandler(500, e.getMessage());
        } finally {
            CurrentUserComponent.delCurrentUser();
        }
    }

    @Around("RpcHandler()")
    public Object rpcArround(ProceedingJoinPoint pjp) {

        try {
            Object obj = pjp.proceed();
            return obj;
        } catch (Throwable e) {
            log.error("PRC异常: {}", e);
            return errorHandler(500, "prc调用异常");
        }
    }

    private ResultSub errorHandler(Integer status, String message) {
        ResultSub resultSub = new ResultSub();
        resultSub.setMessage(message);
        resultSub.setStatus(status);
        return resultSub;
    }

}
