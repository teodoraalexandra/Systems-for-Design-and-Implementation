package lab9;

import lab9.ui.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab9"
                );

        context.getBean(Console.class).runConsole();

        System.out.println("Bye!");
    }
}
