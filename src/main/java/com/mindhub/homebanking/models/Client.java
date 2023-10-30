package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private Long id;
    private String name;
    
    private String lastName;
    
    private String email;
    private String password;
    private ClientRole clientRole;



   @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>(); //genera un espacio de memoria de la aplicacion


    @JsonManagedReference
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client() {
    }

    public Client(String name, String lastName, String email, String password, ClientRole clientRole) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.clientRole = clientRole;
    }

    public ClientRole getClientRole() {
        return clientRole;
    }

    public void setClientRole(ClientRole clientRole) {
        this.clientRole = clientRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    @JsonManagedReference
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }
    public Set<Card> getCards(){return cards;}
    public void addCards(Card card){
        card.setClient(this);
        cards.add(card);
    }

    public Set<ClientLoan> getClientLoans() {return clientLoans;};

    public void addClientLoans (ClientLoan clientLoan) {
        clientLoan.setClient(this);
        clientLoans.add(clientLoan);

    };
    @JsonManagedReference
    public List<Loan> getLoans(){
        return clientLoans.stream()
                .map(clientLoan -> clientLoan.getLoan())
                .collect(Collectors.toList());
    }



    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mail='" + email + '\'' +
                ", Accounts='" + accounts + '\'' +
                ", Loans='" + clientLoans + '\'' +
                ", cards='" + cards + '\'' +
                '}';
    }
}
