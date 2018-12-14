package com.zhongkouwei.web.model;

import lombok.Data;

@Data
public class PasswordModel {

    private Integer userId;

    private String oldPassword;

    private String newPassword;
}
