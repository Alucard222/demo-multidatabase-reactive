package com.example.neo4j.demo.neo4j_config;

import org.neo4j.driver.Driver;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.DatabaseSelection;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;


@Configuration
public class Neo4jConfig {

    public static final String KEY_DATABASE_NAME = "database";

    /**
     * This is the key point of providing a dynamic database selection based on the reactive application context.
     * We check reactive context if it contains a key with the selected database. If it is not there, we make
     * use of the fact that we can have the original Spring configuration injected here in this configuration bean
     * and use the default database name as configured in `application.properties``
     *
     * @param neo4jDataProperties The original Spring Data Neo4j properties as provided by Spring Boot
     * @return
     */
    @Bean
    public ReactiveDatabaseSelectionProvider reactiveDatabaseSelectionProvider(
            Neo4jDataProperties neo4jDataProperties) {

        return () -> Mono.deferContextual(ctx ->
                Mono.justOrEmpty(ctx.<String>getOrEmpty(KEY_DATABASE_NAME))
                        .map(DatabaseSelection::byName)
                        .switchIfEmpty(Mono.just(DatabaseSelection.byName(neo4jDataProperties.getDatabase())))
        );
    }

    /**
     * The reactive transaction manager is not automatically configured by Spring Boot due to some limitations of the
     * framework. If you want to have {@link Transactional declarative and reactive @Transactional} methods, you need
     * to provide it yourself.
     *
     * @return
     */
    @Bean
    public ReactiveTransactionManager reactiveTransactionManager(Driver driver,
                                                                 ReactiveDatabaseSelectionProvider databaseSelectionProvider) {
        return new ReactiveNeo4jTransactionManager(driver, databaseSelectionProvider);
    }
}
