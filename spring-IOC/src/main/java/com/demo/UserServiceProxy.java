package com.demo;

// 代理角色，在这里添加日志的实现
public class UserServiceProxy implements UserService{
    private UserServiceImpl userService;

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }
    public void add() {
        log("add");
        System.out.println("添加一条信息");
    }

    public void delete() {
        log("delete");
        System.out.println("删除一条信息");
    }

    public void update() {
        log("update");
        System.out.println("修改一条信息");
    }

    public void query() {
        log("query");
        System.out.println("查询一条信息");
    }

    public void log(String msg) {
        System.out.println("执行了"+msg+"方法");
    }
}
