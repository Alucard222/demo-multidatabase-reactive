package com.example.neo4j.demo.client;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

public interface ClientRepository extends ReactiveNeo4jRepository<Client, Long> {

    /**
     * Example query from the blog post.
     *
     * @return
     */
    @Query("MATCH (client:Client)-[:PURCHASED]->(ticket)-[:HAS_TICKETITEM]->(item:TicketItem)\n"
            + "WHERE client.id <> \"Unknown\"\n"
            + "WITH client, count(DISTINCT ticket) AS tickets,\n"
            + "apoc.math.round(sum(item.netAmount), 2) AS totalSpend\n"
            + "RETURN client, client.id as id, totalSpend, tickets,\n"
            + "       apoc.math.round(totalSpend / tickets, 2) AS spendPerTicket\n"
            + "ORDER BY totalSpend DESC\n"
            + "LIMIT 10")
    Flux<ClientStatistics> getTop10BiggestSpendingClients();

    @Query("UNWIND fabric.graphIds() AS graphId\n"
            + "CALL {\n"
            + "  USE fabric.graph(graphId)\n "
            + "  MATCH (client:Client)-[:PURCHASED]->(ticket)-[:HAS_TICKETITEM]->(item:TicketItem)\n"
            + "    WHERE client.id <> \"Unknown\"\n"
            + "  WITH client, count(DISTINCT ticket) AS tickets,\n"
            + "       apoc.math.round(sum(item.netAmount), 2) AS totalSpend\n"
            + "  RETURN client, client.id as id, totalSpend, tickets,\n"
            + "         apoc.math.round(totalSpend / tickets, 2) AS spendPerTicket\n"
            + "  ORDER BY totalSpend DESC\n"
            + "  LIMIT 5"
            + "}\n"
            + "RETURN client, id, totalSpend, tickets, spendPerTicket\n"
            + "ORDER BY totalSpend DESC\n")
    Flux<ClientStatistics> getTop10SpendingClientsAcrossDatabases();
}
