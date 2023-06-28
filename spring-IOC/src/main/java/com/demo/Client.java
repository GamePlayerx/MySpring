package com.demo;

// 客户类，一般客户都会找代理！
public class Client {
    public static void main(String[] args) {
        // 房东要租房
        Host host = new Host();
        // 中介帮助房东
        Proxy proxy = new Proxy(host);
        // 你去找中介
        proxy.rent();
    }
}
