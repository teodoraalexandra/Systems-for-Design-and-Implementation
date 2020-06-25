import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.ClientRepo;
import repository.MovieRepo;
import repository.RentalRepo;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("Server started");
        try {
            LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("config");

        ClientRepo studentRepo = context.getBean(ClientRepo.class);
        MovieRepo movieRepo = context.getBean(MovieRepo.class);
        RentalRepo rentalRepo = context.getBean(RentalRepo.class);
    }
}