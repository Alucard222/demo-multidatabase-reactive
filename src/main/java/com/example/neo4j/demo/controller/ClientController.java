package com.example.neo4j.demo.controller;

import com.example.neo4j.demo.client.Client;
import com.example.neo4j.demo.client.ClientRepository;
import com.example.neo4j.demo.client.ClientStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientRepository clientRepository;
    public static final String KEY_DATABASE_NAME = "database";

    ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping("/{id}/{mall}")
    Mono<Client> findClientMallInformation(@PathVariable Long id, @PathVariable String mall){
        return inDatabase(mall, clientRepository.findById(id));
    }

    @GetMapping("/top/spenders/{mall}")
    Flux<ClientStatistics> getTop10SpendersInMall(@PathVariable String mall){
        return inDatabase(mall, clientRepository.getTop10BiggestSpendingClients());
    }

    @GetMapping("/top/spenders/all/malls")
    Flux<ClientStatistics> getTop10SpendingClientsAcrossMalls(){
        return clientRepository.getTop10SpendingClientsAcrossMalls();
    }

    // The following two operators allow us to decorate the reactor context.

    static <T> Mono<T> inDatabase(String database, Mono<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

    static <T> Flux<T> inDatabase(String database, Flux<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

}
