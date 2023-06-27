import com.Student;
import com.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyStudent {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("StudentBean.xml");

        Student student = (Student) context.getBean("student1");
        student.show();
    }
}
