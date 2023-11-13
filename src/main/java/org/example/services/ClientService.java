package org.example.services;

import org.example.models.Client;
import org.example.repositories.ClientRepo;

import java.util.List;
import java.util.Optional;

public class ClientService {
    private final ClientRepo clientRepository;

    public ClientService(ClientRepo clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client addClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> getClient(int id) {
        return clientRepository.findById(id);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public void deleteClient(int id) {
        clientRepository.deleteById(id);
    }

}
