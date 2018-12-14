package com.zhongkouwei.web.aop;

import com.zhongkouwei.user.common.model.ResultSub;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author wangyiming1031@aliyun.com
 * @Description
 * @Date 2018/11/19 10:19
 **/
@Aspect
@Slf4j
public class RpcAspect {

    @Pointcut("@annotation(com.zhongkouwei.web.aop.RpcHandler)")
    public void RpcHandler() {
    }

    @Around("RpcHandler()")
    public Object arround(ProceedingJoinPoint pjp) {

        try {
            Object obj = pjp.proceed();
            /*if (obj instanceof ResultSub) {
                ResultSub result = (ResultSub) obj;
                if (result.getStatus() != 200) {
                    return errorHandler(result.getMessage());
                }
                return obj;
            } else {
                return errorHandler("服务返回格式异常");
            }*/
            return obj;
        } catch (Throwable e) {
            log.error("PRC异常: {}", e);
            return errorHandler("prc调用异常");
        }
    }

    private ResultSub errorHandler(String message) {
        ResultSub resultSub = new ResultSub();
        resultSub.setMessage(message);
        resultSub.setStatus(500);
        return resultSub;
    }

}
