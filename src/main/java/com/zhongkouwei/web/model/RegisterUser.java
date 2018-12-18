package com.zhongkouwei.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUser {

    private String username;

    private String email;

    private String phone;

    private String password;

}
