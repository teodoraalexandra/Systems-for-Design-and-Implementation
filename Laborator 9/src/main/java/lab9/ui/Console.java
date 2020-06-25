package lab9.ui;

import lab9.model.Client;
import lab9.model.Movie;
import lab9.model.Rental;
import lab9.service.ClientService;
import lab9.service.MovieService;
import lab9.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Component
public class Console {
    @Autowired
    private ClientService clientService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private RentalService rentalService;

    private void printMenu() {
        System.out.println("\n\n\t1. Client operations.");
        System.out.println("\t2. Movie operations.");
        System.out.println("\t3. Rental operations.");
        System.out.println("\t0. Exit.\n");
    }

    private void printClientMenu() {
        System.out.println("\n\n\t\t\t1. Add a Client.");
        System.out.println("\t\t\t2. Delete a Client.");
        System.out.println("\t\t\t3. Update a Client.");
        System.out.println("\t\t\t4. Print all Clients.");
        System.out.println("\t\t\t5. Filter clients by Name.");
        System.out.println("\t\t\t6. Clients with longest Name.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private void printMovieMenu() {
        System.out.println("\n\n\t\t\t1. Add a Movie.");
        System.out.println("\t\t\t2. Delete a Movie.");
        System.out.println("\t\t\t3. Update a Movie.");
        System.out.println("\t\t\t4. Print all Movies.");
        System.out.println("\t\t\t5. Filter Movies by Title.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private void printRentalMenu() {
        System.out.println("\n\n\t\t\t1. Add an Rental.");
        System.out.println("\t\t\t2. Delete an Rental.");
        System.out.println("\t\t\t3. Update an Rental.");
        System.out.println("\t\t\t4. Print all Rentals.");
        System.out.println("\t\t\t5. Filter Rental by Client ID.");
        System.out.println("\t\t\t6. The Most Rented Movie.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private Client readClient() throws IOException {
        System.out.println("Read client {id, serialNumber, name}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();

            Client client = new Client(serialNumber, name);
            client.setId(id);

            return client;

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private Movie readMovie() throws IOException {
        System.out.println("Read movie {id, serialNumber, title, director, duration}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            String serialNumber = bufferRead.readLine();
            String title = bufferRead.readLine();
            String director = bufferRead.readLine();
            int duration = Integer.parseInt(bufferRead.readLine());

            Movie movie = new Movie(serialNumber, title, director, duration);
            movie.setId(id);

            return movie;

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private Rental readRental() throws IOException {
        System.out.println("Read rental {id, clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            int clientId = Integer.parseInt(bufferRead.readLine());
            int movieId = Integer.parseInt(bufferRead.readLine());

            Rental rental = new Rental(clientId, movieId);
            rental.setId(id);

            return rental;

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private String readId() {
        System.out.println("Enter the id: ");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            Long id = Long.valueOf(bufferRead.readLine());
            return String.valueOf(id);

        } catch (IOException | RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return String.valueOf(-1);
    }

    private String readFilter() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String s = bufferRead.readLine();
            return s;

        } catch (IOException | RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return String.valueOf(-1);
    }

    public void runConsole() {
        try {
            while (true) {
                printMenu();
                int command1, command2;
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                command1 = Integer.parseInt(bufferRead.readLine());
                if (command1 == 1) {
                    while (true) {
                        printClientMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            Client client = readClient();
                            clientService.saveClient(client);
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            clientService.deleteClientById(id);
                        } else if (command2 == 3) {
                            Client client = readClient();
                            clientService.updateClient(client);
                        } else if (command2 == 4) {
                            printAllClients();
                        } else if (command2 == 5) { //filter clients by name
                            System.out.print("Name for filter: ");
                            String name = readFilter();
                            System.out.println("You've entered: " + name);
                            List<Client> filterClients = clientService.getAllClients();
                            filterClients.stream()
                                    .filter(client -> client.getName().equals(name))
                                    .forEach(client -> System.out.println(client.toString()));
                        } else if (command2 == 6) { //client with the longest name
                            List<Client> clients = clientService.getAllClients();
                            Optional<String> longestName = clients.stream()
                                    .map(Client::getName)
                                    .reduce((Client1, Client2) -> Client1.length() >= Client2.length() ? Client1 : Client2);
                            System.out.println(longestName.toString());
                        } else if (command2 == 0) break;
                    }
                } else if (command1 == 2) {
                    while (true) {
                        printMovieMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            Movie movie = readMovie();
                            movieService.saveMovie(movie);
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            movieService.deleteMovieById(id);
                        } else if (command2 == 3) {
                            Movie movie = readMovie();
                            movieService.updateMovie(movie);
                        } else if (command2 == 4) {
                            printAllMovies();
                        }
                        else if (command2 == 5) { // filter movie by title
                            System.out.print("Title for filter: ");
                            String title = readFilter();
                            System.out.println("You've entered: " + title);
                            List<Movie> filterMovies = movieService.getAllMovies();
                            filterMovies.stream()
                                    .filter(movie -> movie.getTitle().equals(title))
                                    .forEach(movie -> System.out.println(movie.toString()));
                        }
                        else if (command2 == 0) break;
                    }
                } else if (command1 == 3) {
                    while (true) {
                        printRentalMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            Rental rental = readRental();
                            rentalService.saveRental(rental);
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            rentalService.deleteRentalById(id);
                        } else if (command2 == 3) {
                            Rental rental = readRental();
                            rentalService.updateRental(rental);
                        } else if (command2 == 4) {
                            printAllRentals();
                        }
                        else if (command2 == 5) {
                            System.out.print("ClientId for filter: ");
                            String id = readFilter();
                            int intId = Integer.parseInt(id);
                            System.out.println("You've entered: " + id);
                            List<Rental> filterRentals = rentalService.getAllRentals();
                            filterRentals.stream()
                                    .filter(rental -> rental.getCid() == intId)
                                    .forEach(rental -> System.out.println(rental.toString()));
                        } else if (command2 == 6){
                            Iterable<Rental> rentals = rentalService.getAllRentals();
                            List<Integer> problems;
                            problems = StreamSupport.stream(rentals.spliterator(), false)
                                    .map(Rental::getMid).collect(Collectors.toList());
                            Map<Object, Long> counts = problems.stream()
                                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                            System.out.println(counts.entrySet()
                                    .stream()
                                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                                    .get()
                                    .getKey());
                        } else if (command2 == 0) break;
                    }
                } else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printAllClients() {
        List<Client> clients = clientService.getAllClients();
        clients.forEach(System.out::println);
    }

    private void printAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        movies.forEach(System.out::println);
    }

    private void printAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        rentals.forEach(System.out::println);
    }
}
