package com.zhongkouwei.web.component;

import com.zhongkouwei.user.common.model.UserInfo;

public class CurrentUserComponent {

    private static ThreadLocal<UserInfo>currentUser=new ThreadLocal<>();

    public static UserInfo getCurrentUser(){
        return currentUser.get();
    }

    public static void setCurrentUser(UserInfo user){
        currentUser.set(user);
    }

    public static void delCurrentUser(){
        currentUser.remove();
    }
}
