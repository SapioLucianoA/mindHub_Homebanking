package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping("/clients")
    public List<ClientDTO> getAllClient(){

        List<Client> clients = clientRepository.findAll();

        Stream<Client> clientStream = clients.stream();

        Stream<ClientDTO> clientDTOStream = clientStream.map(client -> new ClientDTO(client));

        List<ClientDTO> clientDTOS = clientDTOStream.collect(Collectors.toList());


        return clientDTOS;
    };
    @PostMapping("/Clients")
    public Client create(@RequestBody Client client){
        return clientRepository.save(client);
    }
    @PutMapping("/client/{id}")
    public Client update(@RequestBody Client client, @PathVariable Long id ){
        Client clientBase = clientRepository.getOne(id);
        clientBase.setName(client.getName());
        clientBase.setLastName(client.getLastName());
        clientBase.setMail(client.getMail());

        return clientRepository.save(client);
    }

    @DeleteMapping("/client/{id}")
    public void delete(@PathVariable Long id){
        clientRepository.deleteById(id);
    }


}
