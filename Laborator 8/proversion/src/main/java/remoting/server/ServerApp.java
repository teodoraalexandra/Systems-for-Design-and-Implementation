package remoting.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("ro.ubb.remoting.server.config");

        // StudentRepo studentRepo = context.getBean(StudentRepo.class);
        // ProblemRepo problemRepo = context.getBean(ProblemRepo.class);
        // AssignRepo assignRepo = context.getBean(AssignRepo.class);
        // GradingRepo gradingRepo = context.getBean(GradingRepo.class);
    }
}