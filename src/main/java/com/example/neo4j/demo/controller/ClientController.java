package com.example.neo4j.demo.controller;

import com.example.neo4j.demo.client.ClientRepository;
import com.example.neo4j.demo.client.ClientStatistics;
import com.example.neo4j.demo.product.ProductRepository;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataProperties;
import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
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

    private final ProductRepository productRepository;

    private final ReactiveNeo4jClient neo4jClient;

    private final Neo4jDataProperties neo4jDataProperties;

    public static final String KEY_DATABASE_NAME = "database";

    ClientController(ClientRepository clientRepository, ProductRepository productRepository, ReactiveNeo4jClient neo4jClient, Neo4jDataProperties neo4jDataProperties) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.neo4jClient = neo4jClient;
        this.neo4jDataProperties = neo4jDataProperties;
    }

    @GetMapping("/top/spenders/{mall}")
    Flux<ClientStatistics> getTop10SpendersInDatabase(@PathVariable String mall){
        return inDatabase(mall, clientRepository.getTop10BiggestSpendingClients());
    }

    @GetMapping("/top/spenders/all/databases")
    Flux<ClientStatistics> getTop10SpendingClientsAcrossDatabases(){
        return clientRepository.getTop10SpendingClientsAcrossDatabases();
    }



    // The following two operators allow us to decorate the reactor context.

    static <T> Mono<T> inDatabase(String database, Mono<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

    static <T> Flux<T> inDatabase(String database, Flux<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

}
