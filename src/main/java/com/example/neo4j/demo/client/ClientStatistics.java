package com.example.neo4j.demo.client;

/**
 * Client DTO projection
 */
public class ClientStatistics {

    private final Long id;

    private final double totalSpend;

    private final int tickets;

    private final double spendPerTicket;

    public ClientStatistics(Long id, double totalSpend, int tickets, double spendPerTicket) {
        this.id = id;
        this.totalSpend = totalSpend;
        this.tickets = tickets;
        this.spendPerTicket = spendPerTicket;
    }

    public Long getId() {
        return id;
    }

    public double getTotalSpend() {
        return totalSpend;
    }

    public int getTickets() {
        return tickets;
    }

    public double getSpendPerTicket() {
        return spendPerTicket;
    }

    @Override public String toString() {
        return "ClientStatistics{" +
                "id=" + id +
                ", totalSpend=" + totalSpend +
                ", tickets=" + tickets +
                ", spendPerTicket=" + spendPerTicket +
                '}';
    }

}
