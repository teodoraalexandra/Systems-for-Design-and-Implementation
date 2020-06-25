package lab12.core.service;

import lab12.core.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientServiceImpl extends EntityServiceImpl<Client> {

    @Autowired
    public ClientServiceImpl(JpaRepository<Client, Long> repository) {
        super(repository);
    }
}
