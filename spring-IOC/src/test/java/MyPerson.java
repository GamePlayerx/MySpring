
import com.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyPerson {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("person.xml");
        Person person = (Person) context.getBean("person8");
        person.show();
    }
}
