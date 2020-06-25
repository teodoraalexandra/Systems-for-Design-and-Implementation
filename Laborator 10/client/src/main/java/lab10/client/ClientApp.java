package lab10.client;

import lab10.core.model.Rental;
import lab10.web.dto.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class ClientApp {
    public static final String CLIENTS_URL = "http://localhost:8080/api/clients";
    public static final String MOVIES_URL = "http://localhost:8080/api/movies";
    public static final String RENTALS_URL = "http://localhost:8080/api/rentals";

    private static void printMenu() {
        System.out.println("\n\n\t1. Client operations.");
        System.out.println("\t2. Movie operations.");
        System.out.println("\t3. Rental operations.");
        System.out.println("\t0. Exit.\n");
    }

    private static void printClientMenu() {
        System.out.println("\n\n\t\t\t1. Add a Client.");
        System.out.println("\t\t\t2. Delete a Client.");
        System.out.println("\t\t\t3. Update a Client.");
        System.out.println("\t\t\t4. Print all Clients.");
        System.out.println("\t\t\t5. Filter clients by Name.");
        System.out.println("\t\t\t6. Clients with longest Name.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private static void printMovieMenu() {
        System.out.println("\n\n\t\t\t1. Add a Movie.");
        System.out.println("\t\t\t2. Delete a Movie.");
        System.out.println("\t\t\t3. Update a Movie.");
        System.out.println("\t\t\t4. Print all Movies.");
        System.out.println("\t\t\t5. Filter Movies by Title.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private static void printRentalMenu() {
        System.out.println("\n\n\t\t\t1. Add an Rental.");
        System.out.println("\t\t\t2. Delete an Rental.");
        System.out.println("\t\t\t3. Update an Rental.");
        System.out.println("\t\t\t4. Print all Rentals.");
        System.out.println("\t\t\t5. Filter Rental by Client ID.");
        System.out.println("\t\t\t6. The Most Rented Movie.");
        System.out.println("\t\t\t0. Back.\n");
    }

    private static ClientDto readClient() throws IOException {
        System.out.println("Read client {serialNumber, name}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();

            return new ClientDto(serialNumber, name);

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private static MovieDto readMovie() throws IOException {
        System.out.println("Read movie {serialNumber, title, director, duration}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            String serialNumber = bufferRead.readLine();
            String title = bufferRead.readLine();
            String director = bufferRead.readLine();
            int duration = Integer.parseInt(bufferRead.readLine());

            return new MovieDto(serialNumber, title, director, duration);

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private static RentalDto readRental() throws IOException {
        System.out.println("Read rental {clientId, movieId}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            int clientId = Integer.parseInt(bufferRead.readLine());
            int movieId = Integer.parseInt(bufferRead.readLine());

            return new RentalDto(clientId, movieId);

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private static String readId() {
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

    private static String readFilter() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            return bufferRead.readLine();

        } catch (IOException | RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
        return String.valueOf(-1);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab10.client.config"
                );

        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        System.out.println("Hello client!");

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
                            ClientDto client = readClient();
                            ClientDto savedClient = restTemplate.postForObject(
                                    CLIENTS_URL,
                                    client,
                                    ClientDto.class);
                            System.out.println("Client saved successfully.");
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            restTemplate.delete(CLIENTS_URL + "/{id}", id);
                            System.out.println("Client deleted successfully.");
                        } else if (command2 == 3) {
                            //Read the data that user want to be updated, together with the id
                            ClientDto client = readClient();
                            Long id = Long.valueOf(readId());

                            //Find the client with the given id
                            ClientsDto allClients = restTemplate.getForObject(CLIENTS_URL, ClientsDto.class);
                            ClientDto cl = allClients.getClients()
                                    .stream()
                                    .filter(c -> c.getId().equals(id))
                                    .findFirst().orElse(null);

                            //Set the given values for the client
                            cl.setSerialNumber(client.getSerialNumber());
                            cl.setName(client.getName());

                            //Update with restTemplate
                            restTemplate.put(CLIENTS_URL + "/{id}", cl, cl.getId());
                            System.out.println("Client updated successfully.");

                        } else if (command2 == 4) {
                            printAllClients(restTemplate);
                        } else if (command2 == 5) { //filter clients by name
                            System.out.print("Name for filter: ");
                            String name = readFilter();
                            System.out.println("You've entered: " + name);

                            ClientsDto allClients = restTemplate.getForObject(CLIENTS_URL, ClientsDto.class);
                            allClients.getClients()
                                    .stream()
                                    .filter(client -> client.getName().equals(name))
                                    .forEach(System.out::println);

                        } else if (command2 == 6) { //client with the longest name
                            ClientsDto allClients = restTemplate.getForObject(CLIENTS_URL, ClientsDto.class);
                            Optional<String> longestName = allClients.getClients()
                                    .stream()
                                    .map(ClientDto::getName)
                                    .reduce((Client1, Client2) -> Client1.length() >= Client2.length() ? Client1 : Client2);
                            System.out.println(longestName.toString());

                        } else if (command2 == 0) break;
                    }
                } else if (command1 == 2) {
                    while (true) {
                        printMovieMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            MovieDto movie = readMovie();
                            MovieDto savedMovie = restTemplate.postForObject(
                                    MOVIES_URL,
                                    movie,
                                    MovieDto.class);
                            System.out.println("Movie saved successfully.");
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            restTemplate.delete(MOVIES_URL + "/{id}", id);
                            System.out.println("Movie deleted successfully.");
                        } else if (command2 == 3) {
                            //Read the data that user want to be updated, together with the id
                            MovieDto movie = readMovie();
                            Long id = Long.valueOf(readId());

                            //Find the movie with the given id
                            MoviesDto allMovies = restTemplate.getForObject(MOVIES_URL, MoviesDto.class);
                            MovieDto mo = allMovies.getMovies()
                                    .stream()
                                    .filter(m -> m.getId().equals(id))
                                    .findFirst().orElse(null);

                            //Set the given values for the movie
                            mo.setSerialNumber(movie.getSerialNumber());
                            mo.setTitle(movie.getTitle());
                            mo.setDirector(movie.getDirector());
                            mo.setDuration(movie.getDuration());

                            //Update with restTemplate
                            restTemplate.put(MOVIES_URL + "/{id}", mo, mo.getId());
                            System.out.println("Movie updated successfully.");

                        } else if (command2 == 4) {
                            printAllMovies(restTemplate);
                        }
                        else if (command2 == 5) { // filter movie by title
                            System.out.print("Title for filter: ");
                            String title = readFilter();
                            System.out.println("You've entered: " + title);

                            MoviesDto allMovies = restTemplate.getForObject(MOVIES_URL, MoviesDto.class);
                            allMovies.getMovies()
                                    .stream()
                                    .filter(movie -> movie.getTitle().equals(title))
                                    .forEach(System.out::println);
                        }
                        else if (command2 == 0) break;
                    }
                } else if (command1 == 3) {
                    while (true) {
                        printRentalMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            RentalDto rental = readRental();
                            RentalDto savedRental = restTemplate.postForObject(
                                    RENTALS_URL,
                                    rental,
                                    RentalDto.class);
                            System.out.println("Rental saved successfully.");
                        } else if (command2 == 2) {
                            Long id = Long.valueOf(readId());
                            restTemplate.delete(RENTALS_URL + "/{id}", id);
                            System.out.println("Rental deleted successfully.");
                        } else if (command2 == 3) {
                            //Read the data that user want to be updated, together with the id
                            RentalDto rental = readRental();
                            Long id = Long.valueOf(readId());

                            //Find the rental with the given id
                            RentalsDto allRentals = restTemplate.getForObject(RENTALS_URL, RentalsDto.class);
                            RentalDto re = allRentals.getRentals()
                                    .stream()
                                    .filter(r -> r.getId().equals(id))
                                    .findFirst().orElse(null);

                            //Set the given values for the rental
                            re.setCid(rental.getCid());
                            re.setMid(rental.getMid());

                            //Update with restTemplate
                            restTemplate.put(RENTALS_URL + "/{id}", re, re.getId());
                            System.out.println("Rental updated successfully.");

                        } else if (command2 == 4) {
                            printAllRentals(restTemplate);
                        }
                        else if (command2 == 5) {
                            System.out.print("ClientId for filter: ");
                            String id = readFilter();
                            int intId = Integer.parseInt(id);
                            System.out.println("You've entered: " + id);

                            RentalsDto allRentals = restTemplate.getForObject(RENTALS_URL, RentalsDto.class);
                            allRentals.getRentals()
                                    .stream()
                                    .filter(rental -> rental.getCid() == intId)
                                    .forEach(System.out::println);

                        } else if (command2 == 6){
                            RentalsDto rentals = restTemplate.getForObject(RENTALS_URL, RentalsDto.class);
                            List<Integer> problems;
                            problems = rentals.getRentals().stream()
                                    .map(RentalDto::getMid)
                                    .collect(Collectors.toList());
                            Map<Object, Long> counts = problems.stream()
                                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                            System.out.println(counts.entrySet()
                                    .stream()
                                    .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                                    .get()
                                    .getKey());

                        } else if (command2 == 0) break;
                    }
                } else {
                    System.out.println("Bye client!");
                    break;
                }
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

    private static void printAllClients(RestTemplate restTemplate) {
        ClientsDto allClients = restTemplate.getForObject(CLIENTS_URL, ClientsDto.class);
        System.out.println(allClients);
    }

    private static void printAllMovies(RestTemplate restTemplate) {
        MoviesDto allMovies = restTemplate.getForObject(MOVIES_URL, MoviesDto.class);
        System.out.println(allMovies);
    }

    private static void printAllRentals(RestTemplate restTemplate) {
        RentalsDto allRentals = restTemplate.getForObject(RENTALS_URL, RentalsDto.class);
        System.out.println(allRentals);
    }
}
