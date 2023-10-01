package com.mindhub.homebanking.resource;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/client/v1")
public class ClientResource {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/Clients")
        public List<Client> findAll(){
        return clientRepository.findAll();
    }
    @PostMapping("/Clients")
    public Client create(@RequestBody Client client){
        return clientRepository.save(client);
    }
    @PutMapping("/book/{id}")
    public Client update(@RequestBody Client client, @PathVariable Long id ){
        Client clientBase = clientRepository.getOne(id);
        clientBase.setName(client.getName());
        clientBase.setLastName(client.getLastName());
        clientBase.setMail(client.getMail());

        return clientRepository.save(client);
    }

    @DeleteMapping("/book/{id}")
    public void delete(@PathVariable Long id){
        clientRepository.deleteById(id);
    }

}
