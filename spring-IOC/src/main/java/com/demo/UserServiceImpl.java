package com.demo;

// 真实对象，完成增删改查操作的人
public class UserServiceImpl implements UserService {
    public void add() {
        System.out.println("添加一条信息");
    }

    public void delete() {
        System.out.println("删除一条信息");
    }

    public void update() {
        System.out.println("修改一条信息");
    }

    public void query() {
        System.out.println("查询一条信息");
    }
}
