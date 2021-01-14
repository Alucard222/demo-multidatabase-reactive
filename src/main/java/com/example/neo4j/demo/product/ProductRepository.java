package com.example.neo4j.demo.product;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveNeo4jRepository<Product, Long> {


    @Query("MATCH path = (:Client {id: $id})-[:PURCHASED]->()-->(:TicketItem)-->(:Product) RETURN path")
    Flux<Product> findAllByClientId(@Param("id") Long id);

    /**
     * This custom query uses the fabric database, which is the configured default database.
     * Make sure you _don't_ use a Reactor context with the database name it, otherwise the query would fail.
     *
     * @return
     */
    @Query("UNWIND fabric.graphIds() AS graphId\n"
            + "CALL {\n"
            + "  USE fabric.graph(graphId)\n"
            + "  MATCH (item:TicketItem)-->(product:Product)\n"
            + "  WITH product, apoc.math.round(sum(item.netAmount), 2) AS totalSpend,\n"
            + "       count(*) AS purchases\n"
            + "  RETURN product, totalSpend, purchases\n"
            + "  ORDER BY totalSpend DESC LIMIT 100\n"
            + "}\n"
            // The map here is needed to correctly aggregate products from different database
            // and make SDN map it back to a product.
            + "RETURN product{.description}, sum(totalSpend) AS totalSpend, sum(purchases) AS purchases\n"
            + "ORDER BY totalSpend DESC")
    Flux<ProductStatistics> findTop100SellingProductsAcrossMalls();
}
