package com.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyInvocationHandler implements InvocationHandler {
    // 被代理的接口
    private Object traget;

    public void setTraget(Object traget) {
        this.traget = traget;
    }

    // 生成代理类
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                traget.getClass().getInterfaces(),this);
    }

    /**
     * 处理代理实例，并返回结果
     * proxy： 代理类
     * method：代理类的调用处理程序的方法对象
     * @param proxy the proxy instance that the method was invoked on
     *
     * @param method the {@code Method} instance corresponding to
     * the interface method invoked on the proxy instance.  The declaring
     * class of the {@code Method} object will be the interface that
     * the method was declared in, which may be a superinterface of the
     * proxy interface that the proxy class inherits the method through.
     *
     * @param args an array of objects containing the values of the
     * arguments passed in the method invocation on the proxy instance,
     * or {@code null} if interface method takes no arguments.
     * Arguments of primitive types are wrapped in instances of the
     * appropriate primitive wrapper class, such as
     * {@code java.lang.Integer} or {@code java.lang.Boolean}.
     *
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log(method.getName());
        // 动态代理的本质，就是使用反射机制实现！
        Object result = method.invoke(traget, args);
        return result;
    }

    public void Log(String msg){
        System.out.println("调用了"+msg+"的方法！");
    }

}
