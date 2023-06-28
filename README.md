# MySpring
# spring
1.Spring是一个开源的免费的框架（容器）！
2.Spring是一个轻量级的，非入侵式的框架！
3.控制反转（IOC），面向切面编程（AOP）!
4.支持事务的处理，对框架整合的支持！

总结一句话：Spring就是一个轻量级的控制反转（IOC）和面向切面编程（AOP）的框架！

# IOC理论
1.UserDao接口
```java
public interface UserDao {
    void getUser();
}
```
2.UserDaoImpl实现类
```java
public class UserDaoImpl implements UserDao {
    @Override
    public void getUser() {
        System.out.println("默认获取用户的数据！");
    }
}
```
3.UserService接口
```java
public interface UserService {
    void getUser();
}
```
4.UserServiceImpl实现类
```java
public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserDaoImpl();
    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```
5.MyTest
```java
public class MyTest {
    public static void main(String[] args) {
        // 用户实现调用的是业务层，dao层它们不需要接触
        UserServiceImpl userService = new UserServiceImpl();
        userService.getUser();
    }
}
```
这是一个简单的业务的过程，但是现在要添加一个新的UserMysqlImpl实现类
```java
public class UserMysqlImpl implements UserDao{
    @Override
    public void getUser() {
        System.out.println("mysql数据获取！");
    }
}
```
要想要实现，UserServiceImpl实现类也需要修改
```java
public class UserServiceImpl implements UserService{
    private UserDao userDao = new UserMysqlImpl();
    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

在我们之前的业务中，用户的需求可能会影响我们原来的代码，我们需要根据用户的需求去修改源代码！
如果程序代码量十分大，修改一次的成本代价十分昂贵

我们使用一个Set接口实现,已经发生了革命性的变化！
```java
public class UserServiceImpl implements UserService{
    private UserDao userDao;

    // 利用set进行动态实现值的注入
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```
MyTest类
```java
public class MyTest {
    public static void main(String[] args) {
        // 用户实现调用的是业务层，dao层它们不需要接触
        UserServiceImpl userService = new UserServiceImpl();
        userService.setUserDao(new UserMysqlImpl());
        userService.getUser();
    }
}
```

之前，程序是主动创建对象！控制权在程序猿手上！
使用了set注入后，程序不在具有主动性，而是变成了被动的接受对象！
主动权再用户手上。

这种思想，从本质上解决了问题，我们程序猿不用再去管理对象的创建了!
系统的耦合性大大降低，可以更加关注再业务的实现上。这是IOC的原型！

## IOC本质
控制反转IOC（Inversion of Control），是一种设计思想，DI（依赖注入）是实现IOC的一种方法，
也有人认为DI只是IOC的另一种说法。没有IOC的程序，我们使用面向对象编程，对象的创建与对象间
的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，
个人任务所谓控制反转就是：获得依赖对象的方式反转了。

采用XML方式配置Bean的时候，Bean的定义信息和实现分离的，而采用注解的方式可以把两者合为一体，
Bean的定义信息直接以注解的形式定义在实现类中，从而达到了零配置的目的。

控制反转是一种通过描述（XML或注解）并通过第三方生产或获取特定对象的方式。在Spring中实现
控制反转的是IOC容器，其实现方式是依赖注入（Dependency Injection,DI）

## HelloSpring

做个简单的例子体验一下

创建实体类Hello
```java
public class Hello {
    private String name;

    public String getNaem() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

这里用的是XML文件方式
```java
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">

<!--    bean就是Java对象，由Spring创建和管理

        类型  变量名  =  new  类型()
        Hello hello = new Hello();

        id  =  变量
        class  =  new的对象
        property  相当于给对象中的属性设置一个值！
-->
    <bean id="hello" class="com.Hello">
        <property name="name" value="Spring"/>
    </bean>

</beans>
```

在创建一个测试类
```java
public class MyTest {

    public static void main(String[] args) {
        // 获取Spring的上下文对象！
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        // 我们的对象现在都正在Soring中的管理了，我们要使用，直接去里面取出来就可以！
        Hello hello = (Hello) context.getBean("hello");
        System.out.println(hello.toString());
    }
}
```

测试一下，最后的输出结果
```java
Hello{name='Spring'}
```

这个过程中Hello是Spring创建的，Hello对象的属性是Spring容器设置的

这个例子的过程就是控制反转：

控制：谁来控制对象的创建，创痛应用程序的对象是由程序本身控制创建的，使用Spring后，
对象是由Spring来创建的

反转：程序本身不创建对象，而变成被动的接收对象

依赖注入：就是利用set方法进行注入的。
```java
 public void setName(String name) {
        this.name = name;
 }
```

IOC是一种编程思想，由主动的编程编程被懂的接收

在MyTest测试类中
```java
ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
```
浅浅看一下ClassPathXmlApplicationContext的底层代码的：

```mermaid
graph BT
ClassPathXmlApplicationContext -->  AbstractXmlApplicationContext

AbstractXmlApplicationContext -->AbstractRefreshableConfigApplicationContext

AbstractRefreshableConfigApplicationContext --> AbstractRefreshableApplicationContext

AbstractRefreshableApplicationContext --> AbstractApplicationContext

AbstractApplicationContext --> ConfigurableApplicationContext

ConfigurableApplicationContext --> ApplicationContext

```
现在，我们彻底不用再程序中改动了，要实现不同的操作，只需要在xml配置文件中进行修改，
所谓IOC,一句话搞定：对象由Spring来创建，管理，装配！

# IOC创建对象的方式
> 无参构造方法来创建

User实体类
```java
public class User {
    private String name;

    public User() {
        System.out.println("user无参构造方法！");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("name=="+name);
    }
}
```

UserBeanXML文件
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">

    <bean id="user" class="com.User">
        <property name="name" value="Java"/>
    </bean>

</beans>
```

测试类MyDemo
```java
public class MyDemo {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("UserBean.xml");
        User user = (User) context.getBean("user");
        user.show();
    }
}
```

结果可以发现，在调用show方法之前，User之前已经通过无参构造初始化了！
```java
user无参构造方法！
name==Java
```

> 有参构造方法创建

实体类Student
```java
public class Student {
    public String name;

    public Student(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show() {
        System.out.println("name===="+name);
    }
}
```

xml的三种写法
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">

<!--    第一种根据index参数下标设置-->
    <bean id="student1" class="com.Student">
        <constructor-arg index="0" value="Java1"/>
    </bean>

<!--    第二种根据参数名字设置-->
    <bean id="student2" class="com.Student">
<!--        name指参数名-->
        <constructor-arg name="name" value="Java2"/>
    </bean>

<!--    第三种根据参数类型设置-->
    <bean id="student3" class="com.Student">
        <constructor-arg type="java.lang.String" value="Java3"/>
    </bean>

</beans>
```

测试MyStudent类
```java
public class MyStudent {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("StudentBean.xml");

        Student student = (Student) context.getBean("student1");
        student.show();
    }
}
```

总结：在配置文件加载的时候。其中管理的对象都已经初始化了！

# Spring配置

> 别名

alias设置别名，为bean设置别名，可以设置多个别名
```xml
<!--    设置别名：在获取bean的时候可以使用别名获取-->
    <alias name="student1" alias="studentdemo"/>
```

> Bean的配置

```xml
<!--    bean接收Java对象，由于Spring创建和管理-->
<!--
        id 是bean的识别符，要唯一，如果没有配置id，name接收默认标识符
        如果配置id，又配置了name，那么name是别名
        name可以设置多个别名，可以用逗号，分号，空格隔开
        如果不配置id和name，可以根据applicationContext.getBean(.class)获取对象；

        class是bean的全限定名=包名+类名
-->
    <bean id="user" name="user2,user3,user4" class="com.xcc.demo.User">
        <property name="name" value="Java"/>
    </bean>
```

> import
> 这import，一般用于团队开发使用，他可以将多个配置文件，导入合并为一个

applicationContext.xml
```xml
<imoirt resource="student.xml"/>
<imoirt resource="UserBean.xml"/>
<imoirt resource="Beans.xml"/>
```
使用的时候，直接使用总的配置就可以了。

# DI依赖注入

### 构造器注入
之前的IOC写过有参无参

### set注入
依赖注入：set注入

依赖：bean对象的创建依赖于容器

注入：bean对象种的所有属性，由容器来注入

```xml
 <bean id="school" class="com.School">
        <property name="schoolName" value="家里蹲大学"/>
    </bean>

    <bean id="person" class="com.Person">
        <!--    常量注入-->
        <property name="name" value="小明"/>
        <!--    Bean注入-->
        <property name="school" ref="school"/>
        <!--    数组注入-->
        <property name="books">
            <array>
                <value>西游记百会文</value>
                <value>悟空传</value>
                <value>西游记后传</value>
                <value>西游记</value>
            </array>
        </property>
        <!--    List注入-->
        <property name="hobbys">
            <list>
                <value>看电影</value>
                <value>玩游戏</value>
                <value>写代码</value>
            </list>
        </property>
        <!--    Map注入-->
        <property name="card">
            <map>
                <entry key="aaa" value="111"/>
                <entry key="bbb" value="222"/>
                <entry key="ccc" value="333"/>
            </map>
        </property>
        <!--    set注入-->
        <property name="games">
            <set>
                <value>艾尔登法环</value>
                <value>最终幻想16</value>
                <value>原神</value>
                <value>LOL</value>
            </set>
        </property>
        <!--    NULL注入-->
        <property name="wife">
            <null/>
        </property>
        <!--    Properties注入-->
        <property name="info">
            <props>
                <prop key="学号">2023</prop>
                <prop key="性别">男</prop>
                <prop key="姓名">小明</prop>
            </props>
        </property>
    </bean>
```
### 其他注入
> p命名和c命名注入

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:p="http://www.springframework.org/schema/p"
       xmlns:c="http://www.springframework.org/schema/c"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">
<!--    导入约束：xmlns:p="http://www.springframework.org/schema/p"-->
<!--    p命名空间。属性依然要设置set方法-->
    <bean id="demo" class="com.UserDemo" p:name="小白" p:age="26"/>

<!--    导入约束： xmlns:c="http://www.springframework.org/schema/c"-->
    <bean id="demo1" class="com.UserDemo" c:name="小黑" c:age="18"/>
</beans>
```
不过需要注意用c命名空间的时候，对象需要有参构造函数，p命名空间，需要无参构造函数

# bean作用域

|   Scope    | Description |
|-------|-------------|
|   singleton    |      (Default) Scopes a single bean definition to a single object instance for each Spring IoC container.       |
|   prototype    |    Scopes a single bean definition to any number of object instances.         |
|   request    |      Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring ApplicationContext.       |
|   session    |      Scopes a single bean definition to the lifecycle of an HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext.       |
|   application    |    Scopes a single bean definition to the lifecycle of a ServletContext. Only valid in the context of a web-aware Spring ApplicationContext.         |
|   websocket    |    Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext.         |


### 1.单例模式（Spring默认机制）
只会创建一次
```xml
<bean id="demo1" class="com.UserDemo" c:name="小黑" c:age="18" scope="singleton"/>
```

### 2.原型模式
每次容器中get的时候，都会产生一个新对象！
```xml
<bean id="demo1" class="com.UserDemo" c:name="小黑" c:age="18" scope="prototype"/>
```

### 3.其余的request,session,application,websocket这些只能在web开发中使用到。

# bean的自动装配

自动装配是Spring满足bean依赖一种方式！  
Spring会在上下文中自动寻找，并自动给bean装配属性！

> 在Spring中有三种装配方式   
> 1.在xml中显示的配置   
> 2.在Java中显示配置   
> 3.隐式的自动装配bean

> byName 按名称自动装配
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">

    <bean id="dog" class="com.dog"/>
    <bean id="cat" class="com.cat"/>

    <bean id="people" class="com.People" autowire="byName">
        <property name="name" value="小黑"/>
    </bean>

</beans>
```
> byName：将查找其类中所有的set方法名，例如setCat，获得将set去掉并且字母小写的字符串，即cat
> 去Spring容器中寻找是否有此字符串名称id的对象
> 如果有，就取出注入；如果没有，就会报空指针异常。

> byType 按类型自动装配
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd">

    <bean id="dog" class="com.dog"/>
    <bean id="cat" class="com.cat"/>

    <bean id="people" class="com.People" autowire="byType">
        <property name="name" value="小黑"/>
    </bean>

</beans>
```
> byTpe: 会自动在容器上下文中查找，和自己对象属性类型相同的bean！
> 使用的前提是必须保证：同一类型的对象，在Spring容器中唯一。如果不唯一，会报不唯一的异常。

# 使用注解实现自动装配

1.导入约束 context约束

2.配置注解的支持：context:annotation-config/
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/context/spring-context.xsd">

<!--    -->
    <context:annotation-config/>
</beans>
```
@Autowired

直接在属性上使用即可！也可以在set方法上使用   
使用Autowired我们可以不用编写Set方法了，前提是你这自动装配的属性在IOC（Spring）容器中存在，
且符合名字byName

> @Autowired(required = false) 说明：false，对象可以为null；true，对象必须存对象，不能为null.

```java
// 如果允许对象为null，设置required = false，默认为true
@Autowired(required = false)
private Cat cat;
```

@Qualifier
> @Autowired 是根据类型自动装配的，加上@Qualifier则可以根据byName的方式自动装配
> @Qualifier不能单独使用
```xml
<bean id="dog1" class="com.dog"/>
<bean id="cat1" class="com.Cat"/>
```

```java
@Autowired
@Qualifier(value = "cat1")
private Cat cat;
@Autowired
@Qualifier(value = "dog1")
private Dog dog;
```

@Resource
> @Resource如有指定的name属性，先按该属性进行byName方式查找装配；
> 其次再进行默认的byName方式进行装配；
> 如果以上都不成功，则按byType的方式自动装配。
> 都不成功，则报异常
> 方式一
```xml
<bean id="dog" class="com.dog"/>
<bean id="cat2" class="com.Cat"/>
```
```java
// 如果允许对象为null，设置required = false，默认为true
@Resource(name = "cat2")
private Cat cat;
@Resource
private Dog dog;
```
方式二
```xml
<bean id="dog" class="com.dog"/>
<bean id="cat" class="com.Cat"/>
```
```java
@Resource
private Cat cat;
@Resource
private Dog dog;
```
### 小结
> @Autowired和@Resource异同：   
> 1、@Autowired与@Resource都可以用来装配bean。都可以写再字段上，或写再set方法上     
> 2、@Autowired默认按类型装配（属于spring规范），默认情况下必须要求依赖对象必须存在，如果
> 要允许null值，可以设置它的required属性为false，如：@Autowired(required=false),
> 如果我们想使用名称装配可以结合@Qualifiter注解进行使用   
> 3、@Resource(属于J2EE复返)，默认按照名称进行装配，名称可以通过name属性进行指定。
> 如果没有指定name属性，当注解写在字段上时，默认取字段名进行按照名称查找，如果注解写在set方法上
> 默认取属性名进行装配。当找不到名称匹配的bean时才按照类型进行装配。
> 但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。
> 它们的作用都是用注解方式注解对象，但执行顺序不同。@Autowired先byType，@Resource先byName

# 使用注解开发

要使用注解开发，必须要保证aop包导入，使用注解需要导入context约束，添加注解的支持！
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springFramework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/context/spring-context.xsd">

<!--    指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="com"/>
    <context:annotation-config/>
</beans>
```
1、bean

2、 属性如何注入
```java
import org.springframework.beans.factory.annotation.Value;
// 这里这个注解的意思，就是名声这个类被Spring接管了，注册到容器中
// 等价与<bean id="user" class="com.User"/>
// @Component 组件
@Component
public class User {
    // 相当于<property name="name" value="SpringFramework"/>
    @Value("SpringFramework")
    public String name;

//    @Value("SpringFramework")
    public void setName() {
        this.name = name;
    }
}
```
3、衍生的注解    
@Component有几个衍生注解，我们在web开发中，会按照mvc三层架构分层！    
dao 【@Repository】     
service 【@Service】    
controller  【@Controller】    
这四个注解功能都是一样的，都是代表将某个注册到Spring中，装配Bean    
4、自动装配置

```java
/**
 *  @Autowired: 自动装配通过类型。名字
 *  如果Autowired不能唯一自动装配上属性，则需要通过@Qualifier(value = "xxx")
 * @Nullable 字段标记了这注解，说明这个字段可以为null；
 * @Resource： 自动装配通过名字，类型。
 */
```
5、作用域
> @Scope    
> singleton: 默认的，Spring会采用单例模式创建这个对象。关闭工厂，所有的对象都会销毁。    
> prototype：多例模式。关闭工厂，所有的对象不会销毁。内部的垃圾回收机制会回收

```java
// 单例模式
@Component
@Scope("singleton")
public class User {
    // 相当于<property name="name" value="SpringFramework"/>
    @Value("SpringFramework")
    public String name;

    public void setName() {
        this.name = name;
    }
}
```
```java
// 多例模式
@Component
@Scope("prototype")
public class User {
    // 相当于<property name="name" value="SpringFramework"/>
    @Value("SpringFramework")
    public String name;

    public void setName() {
        this.name = name;
    }
}
```
6、小结   
XML与注解：   
XML可以适用任何场景，结构清晰，维护方便     
注解不是自己提供的类使用不了，维护复杂，开发简单

XML与注解整合开发：       
XML管理Bean       
注解完成属性注入            
我们在使用的过程中，只需要注意一个问题：必须让注解生效，就需要开启注解的支持
```xml
<!--    指定要扫描的包，这个包下的注解就会生效-->
    <context:component-scan base-package="com"/>
    <context:annotation-config/>
```

# 使用Java的方式配置Spring
使用JavaConfig来装配

```java
// 这个也会被Spring托管，注册到容器中，因为它本来就是一个@Component
// @Configuration代表这是一个配置类，就和我们之前看的beans.xml
// @ComponentScan("xxxx") 意思是扫描下面路径的包
@Configuration
@ComponentScan("com")
public class AppConfig {
    // 注册一个bean，就相当于我们之前写的一个bean标签
    // 这个方法的名字，就相当于bean标签中的id属性
    // 这个方法的返回值，就相当于bean标签中的class属性
    @Bean
    public MyService myService() {
        // 就是返回哟啊注入到bean的对象！
        return new MyServiceImpl();
    }
}
```
等于
```xml
<beans>
    <bean id="myService" class="com.service.MyServiceImpl"/>
</beans>
```
Import

```java
@Configuration
@Import(AppConfig.class)
public class MyConfig {
    @Bean
    public Dog dog() {
        return new Dog();
    }
}
```
# 代理模式
SpringAOP的底层就是代理模式     
代理模式：       
1、静态代理        
2、动态代理
![img.png](img.png)

# 静态代理模式

### 角色分析：
* 抽象角色：一般使用接口或者抽象类来实现
* 真实角色：被代理的角色
* 代理角色：代理真实角色；代理真实角色后，一般会做一些附属的操作。
* 客户：使用代理角色来进行一些操作。

### 代码步骤：
* 1.接口
```java
// 抽象角色：租房
public interface Rent {
    public void rent();
}
```
* 2.真实角色
```java
// 真实角色：房东，房东要出租房子
public class Host implements Rent{
    public void rent() {
        System.out.println("房屋出租");
    }
}
```
* 3.代理角色
```java
// 代理角色：中介
public class Proxy implements Rent{
    private Host host;
    public Proxy() {}
    public Proxy(Host host) {
        this.host = host;
    }
    // 租房
    public void rent() {
        seeHouse();
        host.rent();
        fare();
    }
    // 看房
    public void seeHouse() {
        System.out.println("待房客看房");
    }
    // 收中介费
    public void fare() {
        System.out.println("收中介费");
    }
}
```
* 4.客户端访问代理角色
```java
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
```

### 代理模式的好处
* 可以使真实角色的操作更加纯粹！不用去关注一些公共的业务
* 公共也就交给代理角色！实现了业务的分工！
* 公共业务发生扩展的时候，方便集中管理

### 缺点：
* 一个真实角色就会产生一个代理角色；代码量会翻倍-开发效率降低

# 动态代理模式










