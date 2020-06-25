package repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;

import domain.Client;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientRepositoryImpl implements ClientRepo {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public Optional<Client> findOne(Long ID) {

        String sql = "SELECT * FROM client WHERE ID=?";
        return Optional.ofNullable(jdbcOperations.queryForObject(sql, new Object[] { ID }, (rs, i) -> {
            return new Client(rs.getString("SerialNumber"), rs.getString("Name"));
        }));
    }

    @Override
    public List<Client> findAllClients() {
        String sql = "SELECT * FROM client";
        return jdbcOperations.query(sql, (rs, i) -> {
            Long ID = Long.valueOf(rs.getInt("ID"));
            String serialNumber = rs.getString("SerialNumber");
            String name = rs.getString("Name");
            Client Client = new Client(serialNumber, name);
            Client.setId(ID);
            return Client;
        });
    }

    @Override
    public void save(Client Client) {
        String sql = "INSERT INTO client (ID, SerialNumber, Name) VALUES (?,?,?)";
        jdbcOperations.update(sql, Client.getId(), Client.getSerialNumber(), Client.getName());
    }

    @Override
    public void update(Client Client) {
        String sql = "UPDATE client SET SerialNumber=?, Name=? WHERE ID=?";
        jdbcOperations.update(sql, Client.getSerialNumber(), Client.getName(),
                Integer.valueOf(Client.getId().toString()));
    }

    @Override
    public void delete(Long ID) {
        String sql = "DELETE FROM client WHERE ID = ?";
        jdbcOperations.update(sql, ID);
    }

}
