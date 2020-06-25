
import domain.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ClientServiceClient;
import service.MovieServiceClient;
import service.RentalServiceClient;


public class ClientApp {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("config");


        ClientService clientService = context.getBean(ClientServiceClient.class);
        MovieService movieService = context.getBean(MovieServiceClient.class);
        RentalService rentalService = context.getBean(RentalServiceClient.class);

        Console console = new Console(clientService, movieService, rentalService);
        try {
            console.runConsole();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}