package com.example.neo4j.demo.product;

/**
 * Product DTO projection
 */
public class ProductStatistics {

    private final String description;

    private final double totalSpend;

    private final int purchases;

    ProductStatistics(String description, double totalSpend, int purchases) {
        this.description = description;
        this.totalSpend = totalSpend;
        this.purchases = purchases;
    }

    @Override public String toString() {
        return "ProductStatistics{" +
                "description='" + description + '\'' +
                ", totalSpend=" + totalSpend +
                ", purchases=" + purchases +
                '}';
    }
}
