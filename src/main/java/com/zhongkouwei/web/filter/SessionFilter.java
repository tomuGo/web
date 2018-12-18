package com.zhongkouwei.web.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "sessionFilter")
public class SessionFilter implements Filter {

    private static List<String> swaggerUrls = Arrays.asList("^/swagger.*", "^/webjars.*", "/v2/api-docs*");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }
}
