package com.zhongkouwei.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients(value = "com.zhongkouwei")
@EnableAutoConfiguration
public class Web {
    public static void main(String[] args) {
        SpringApplication.run(Web.class, args);
    }
}
