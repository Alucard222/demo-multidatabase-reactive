package org.neo4j.sdn6.demo.person;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveNeo4jRepository<PersonEntity, Long> {

    @Query("MATCH (p:Person{name:'Tom Hanks'})-[r:ACTED_IN]->(m:Movie) RETURN p,r,m")
    Flux<PersonEntity> tomHanksCareer();

}
