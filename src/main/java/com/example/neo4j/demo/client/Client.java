package com.example.neo4j.demo.client;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import com.example.neo4j.demo.ticket.Ticket;

import java.util.List;

@Node("Client")
public class Client {

    /**
     * Assigned id (an actual property and not the identy function).
     * Assigned means that is assigned from the client side and in this
     * case, assigned with a generated value, orchestrated by SDN 6.
     */
    @Id
    private Long id;

    @Relationship(direction = Relationship.Direction.OUTGOING)
    private final List<Ticket> purchasedTickets;

    public Client(Long id, List<Ticket> purchasedTickets) {
        this.id = id;
        this.purchasedTickets = purchasedTickets;
    }

    Long getId() {
        return id;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    @Override public String toString() {
        return "Client{" +
                "purchasedTickets=" + purchasedTickets +
                '}';
    }
}
