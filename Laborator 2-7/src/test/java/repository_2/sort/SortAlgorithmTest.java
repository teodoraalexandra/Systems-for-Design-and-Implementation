package repository_2.sort;

import repository_2.domain.Client;
import org.junit.Test;
import repository_2.repository.sorting.Sort;
import repository_2.repository.sorting.SortAlgorithm;

import java.util.ArrayList;

public class SortAlgorithmTest {

    @Test
    public void minimalistTest(){
        Sort sort = new Sort("id");
        ArrayList<Client> arr = new ArrayList<>();
        Client client1 = new Client("Maria", "Pop", 20);
        client1.setId((long) 1);
        Client client2 = new Client("Mircea", "Abrudan", 30);
        client2.setId((long) 2);
        Client client3 = new Client("Alexandra", "Popescu", 40);
        client3.setId((long) 3);

        arr.add(client1);
        arr.add(client2);
        arr.add(client3);

        SortAlgorithm<Long, Client> test = new SortAlgorithm<>(arr,sort);
        Long id = (long)1;
        assert id.equals(test.sort().get(0).getId());

        sort = new Sort("firstName");
        test = new SortAlgorithm<>(arr, sort);
        assert test.sort().get(0).getFirstName().equals("Alexandra");
    }

    @Test
    public void testCompoundedSort(){
        Client client1 = new Client("Marcela", "Preda", 35);
        client1.setId((long) 1);
        Client client2 = new Client("Ana", "Popescu", 40);
        client2.setId((long) 2);
        Client client3 = new Client("Ion", "Ionescu", 58);
        client3.setId((long) 3);
        Client client4 = new Client("Ana", "Magdas", 34);
        client4.setId((long) 4);
        Client client5 = new Client("Victor", "Ilie", 20);
        client5.setId((long) 5);
        Client client6 = new Client("Maria", "Varga", 45);
        client6.setId((long) 6);
        ArrayList<Client> arr = new ArrayList<>();
        arr.add(client1);
        arr.add(client2);
        arr.add(client3);
        arr.add(client4);
        arr.add(client5);
        arr.add(client6);

        Sort sort = new Sort("firstName").and(new Sort(Boolean.FALSE,"id"));
        SortAlgorithm<Long, Client> test = new SortAlgorithm<>(arr, sort);
        assert test.sort().get(0).getFirstName().equals("Ana");
        assert test.sort().get(0).getId().equals((long) 4);
    }

    @Test
    public void descSortTest(){
        Client client1 = new Client("Victor", "Preda", 35);
        client1.setId((long) 1);
        Client client2 = new Client("Ana", "Popescu", 40);
        client2.setId((long) 2);
        Client client3 = new Client("Ion", "Ionescu", 58);
        client3.setId((long) 3);
        Client client4 = new Client("Ana", "Magdas", 34);
        client4.setId((long) 4);
        Client client5 = new Client("Victor", "Ilie", 20);
        client5.setId((long) 5);
        Client client6 = new Client("Maria", "Varga", 45);
        client6.setId((long) 6);
        ArrayList<Client> arr = new ArrayList<>();
        arr.add(client1);
        arr.add(client2);
        arr.add(client3);
        arr.add(client4);
        arr.add(client5);
        arr.add(client6);

        Sort sort = new Sort(Boolean.FALSE,"firstName", "id");
        SortAlgorithm<Long,Client> test = new SortAlgorithm<>(arr,sort);
        assert test.sort().get(0).getFirstName().equals("Victor");
        assert test.sort().get(0).getId().equals((long) 5);
    }
}

