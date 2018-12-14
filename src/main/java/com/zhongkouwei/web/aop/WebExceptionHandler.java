package com.zhongkouwei.web.aop;

import com.zhongkouwei.user.common.model.ResultSub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResultSub<String> IllegalArgumentException(IllegalArgumentException e) {
        ResultSub<String>resultSub=new ResultSub<>();
        resultSub.setMessage(e.getMessage());
        resultSub.setStatus(500);
        return resultSub;
    }

}
