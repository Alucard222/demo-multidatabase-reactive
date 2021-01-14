package com.example.neo4j.demo.controller;

import com.example.neo4j.demo.product.Product;
import com.example.neo4j.demo.product.ProductRepository;
import com.example.neo4j.demo.product.ProductStatistics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    public static final String KEY_DATABASE_NAME = "database";

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/client/{id}/{mall}")
    Flux<Product> findAllProductsByClientIdInMall(@PathVariable Long id, @PathVariable String mall){
        return inDatabase(mall, productRepository.findAllByClientId(id));
    }

    @GetMapping("/top/selling/products/all/malls")
    Flux<ProductStatistics> findTop100SellingProductsAcrossMalls(){
        return productRepository.findTop100SellingProductsAcrossMalls();
    }

    // The following two operators allow us to decorate the reactor context.

    static <T> Mono<T> inDatabase(String database, Mono<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

    static <T> Flux<T> inDatabase(String database, Flux<T> original) {
        return original.contextWrite(context -> context.put(KEY_DATABASE_NAME, database));
    }

}
