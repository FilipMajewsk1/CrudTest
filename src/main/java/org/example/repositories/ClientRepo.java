package org.example.repositories;

import org.example.models.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepo {
    Client save(Client client);
    Optional<Client> findById(int id);
    List<Client> findAll();
    void deleteById(int id);
}
