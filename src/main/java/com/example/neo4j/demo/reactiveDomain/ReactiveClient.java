package com.example.neo4j.demo.reactiveDomain;

import java.util.List;

public class ReactiveClient {
    private final long id;
    private final double totalSpend;
    private final double spendPerTicket;
    private final int mallID;

    public ReactiveClient(long id, double totalSpend, double spendPerTicket, int mallID){
        this.id = id;
        this.totalSpend = totalSpend;
        this.spendPerTicket = spendPerTicket;
        this.mallID = mallID;
    }

    public long getId(){return this.id;}
    public int getMallID(){return this.mallID;}
    public double getTotalSpend(){return this.totalSpend;}
    public double getSpendPerTicket(){return this.spendPerTicket;}

}
