package com.example.neo4j.demo.controller;

import com.example.neo4j.demo.reactiveDomain.ReactiveClient;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.neo4j.driver.types.TypeSystem;

import org.springframework.data.neo4j.core.ReactiveNeo4jClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/client/reactiveClient")
public class ClientReactiveController {

    private final ReactiveNeo4jClient client;

    public ClientReactiveController(ReactiveNeo4jClient client){this.client = client;}

    @GetMapping("/top/{count}")
    Flux<ReactiveClient> getTop(int count){
        return client
                .query( "" +
                        "UNWIND fabric.graphIds() AS graphId\n" +
                        "CALL {\n" +
                        "  USE fabric.graph(graphId)\n" +
                        "  MATCH (client:Client)-[:PURCHASED]->(ticket)-[:HAS_TICKETITEM]->(item:TicketItem)\n" +
                        "    WHERE client.id <> \"Unknown\"\n" +
                        "  WITH client, count(DISTINCT ticket) AS tickets,\n" +
                        "       apoc.math.round(sum(item.netAmount), 2) AS totalSpend\n" +
                        "  RETURN client, client.id as id, totalSpend, tickets, apoc.math.round(totalSpend / tickets, 2) AS spendPerTicket\n" +
                        "  ORDER BY totalSpend DESC\n" +
                        "  LIMIT $count}\n" +
                        "RETURN client, id, totalSpend, tickets, spendPerTicket, graphId\n" +
                        "ORDER BY totalSpend DESC\n" +
                        "LIMIT $count")
                .in("fabric")
                .bind(count).to("count")
                .fetchAs(ReactiveClient.class)
                .mappedBy((ts, result) -> {
                    return new ReactiveClient(
                            result.get("id").asLong(), result.get("totalSpend").asDouble(),
                            result.get("spendPerTicket").asDouble(), result.get("graphId").asInt());
                }).all();
    }
}
