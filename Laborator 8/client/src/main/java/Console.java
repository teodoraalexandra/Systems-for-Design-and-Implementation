import domain.*;
import jdk.dynalink.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


class Console {
    private ClientService ClientService;
    private domain.MovieService MovieService;
    private RentalService RentalService;

    Console(domain.ClientService ClientService, MovieService MovieService, domain.RentalService RentalService) {
        this.ClientService = ClientService;
        this.MovieService = MovieService;
        this.RentalService = RentalService;
    }

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
        System.out.println("\t\t\t6. Clients with Longest Name.");
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
            String clientId = bufferRead.readLine();
            String movieId = bufferRead.readLine();

            Rental rental = new Rental(clientId, movieId);
            rental.setId(id);

            return rental;

        } catch (IOException ex) {
            throw new IOException (ex.getMessage());
        } catch (NumberFormatException ex) {
            throw new NumberFormatException(ex.getMessage() + " the value must be integer!");
        }
    }

    private String readID() {
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

    void runConsole() {
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
                            Client Client = readClient();
                            ClientService.addClient(Client);
                            System.out.println("Client added !");
                        } else if (command2 == 2) {
                            Long ID = Long.valueOf(readID());
                            ClientService.deleteClient(ID);
                            System.out.println("Client deleted !");
                        } else if (command2 == 3) {
                            Client Client = readClient();
                            ClientService.updateClient(Client);
                            System.out.println("Client updated !");
                        } else if (command2 == 4) {
                            ClientService.findAllClients().forEach(Client -> System.out.println(Client.toString()));
                        }else if (command2==5) {

                            System.out.print("Name for filter: ");
                            String name = readFilter();
                            System.out.println("You've entered: " + name);
                            List<Client> filterClients = ClientService.findAllClients();
                            filterClients.stream().filter(client -> client.getName().equals(name)).forEach(client -> System.out.println(client.toString()));

                        }else if (command2 == 6){
                            List<Client> Clients = ClientService.findAllClients();
                            Optional<String> longestName = Clients.stream().map(Client -> Client.getName())
                                    .reduce((Client1, Client2) -> Client1.length() >= Client2.length() ? Client1 : Client2);
                            System.out.println(longestName.toString());
                        } else if (command2 == 0) break;
                    }
                } else if (command1 == 2) {
                    while (true) {
                        printMovieMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            Movie Movie = readMovie();
                            MovieService.addMovie(Movie);
                            System.out.println("Movie added !");
                        } else if (command2 == 2) {
                            Long ID = Long.valueOf(readID());
                            MovieService.deleteMovie(ID);
                            System.out.println("Movie deleted !");
                        } else if (command2 == 3) {
                            Movie Movie = readMovie();
                            MovieService.updateMovie(Movie);
                            System.out.println("Movie updated !");
                        } else if (command2 == 0) break;
                        else if (command2 == 4) {
                            MovieService.findAllMovies().forEach(Movie -> System.out.println(Movie.toString()));
                        }
                        else if (command2 == 5) {
                            System.out.print("Title for filter: ");
                            String title = readFilter();
                            System.out.println("You've entered: " + title);
                            List<Movie> filterMovies = MovieService.findAllMovies();
                            filterMovies.stream().filter(movie -> movie.getTitle().equals(title)).forEach(movie -> System.out.println(movie.toString()));
                        }

                    }
                } else if (command1 == 3) {
                    while (true) {
                        printRentalMenu();
                        command2 = Integer.parseInt(bufferRead.readLine());
                        if (command2 == 1) {
                            Rental Rental = readRental();
                            RentalService.addRental(Rental);
                            System.out.println("Rental added !");
                        } else if (command2 == 2) {
                            Long ID = Long.valueOf(readID());
                            RentalService.deleteRental(ID);
                            System.out.println("Rental deleted !");
                        } else if (command2 == 3) {
                            Rental Rental = readRental();
                            RentalService.updateRental(Rental);
                            System.out.println("Rental updated !");
                        } else if (command2 == 0) break;
                        else if (command2 == 4) {
                            RentalService.findAllRentals().forEach(Rental -> System.out.println(Rental.toString()));
                        }
                        else if (command2 == 5) {
                            System.out.print("ClientId for filter: ");
                            String id = readFilter();
                            System.out.println("You've entered: " + id);
                            List<Rental> filterRentals = RentalService.findAllRentals();
                            filterRentals.stream().filter(rental -> rental.getClientId().equals(id)).forEach(rental -> System.out.println(rental.toString()));
                        }
                        else if (command2 == 6){
                            Iterable<Rental> Rentals = RentalService.findAllRentals();
                            List<String> problems;
                            problems = StreamSupport.stream(Rentals.spliterator(), false).map(p -> p.getMovieId()).collect(Collectors.toList());
                            Map<Object, Long> counts = problems.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
                            System.out.println(counts.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey());
                        }
                    }
                } else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}