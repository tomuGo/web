package com.zhongkouwei.web.config;

import com.mongodb.MongoClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

/**
* @Author wangyiming1031@aliyun.com 
* @Description 
* @Date 2018/11/8 11:46
**/
@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb")
@Getter
@Setter
public class MongodbConfig {

    String host;

    int port;

    String database;

    @Bean
    public MongoClient getMongoClient() {
        return new MongoClient(host, port);
    }

    @Bean
    public MongoDbFactory getSimpleMongoDbFactory(){
        SimpleMongoDbFactory simpleMongoDbFactory=new SimpleMongoDbFactory(getMongoClient(),database);
        return simpleMongoDbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(getSimpleMongoDbFactory());
    }

}
