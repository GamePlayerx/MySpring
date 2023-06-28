package com.demo;

public class test {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
//        userService.add();
        UserServiceProxy proxy = new UserServiceProxy();
        proxy.setUserService(userService);
        proxy.add();
    }
}
