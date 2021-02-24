package org.neo4j.sdn6.demo.person;

import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveNeo4jRepository<PersonEntity, Long> {

    @Query("MATCH (p:Person{name:'Tom Hanks'})-[r:ACTED_IN]->(m:Movie) RETURN p,r,m")
    Flux<PersonEntity> tomHanksCareer();

    @Query("MATCH (p1:Person{name: $name})" +
            "OPTIONAL MATCH (p1)-[r:KNOWS]->(p2:Person)" +
            "RETURN p1, COLLECT(r), COLLECT(p2)")
    Flux<PersonEntity> findPersonEntitiesByName(String name);

    @Query("MATCH (p:Person {name: $name}) " +
            "MATCH (p)-[r:KNOWS]->(q:Person) " +
            "RETURN p, COLLECT(r), COLLECT(q)")
    Flux<PersonEntity> getPath(String name);

    @Query("MATCH (p:Person {name: $name}) " +
            "CALL apoc.path.subgraphAll(p, { " +
            "relationshipFilter: 'KNOWS', " +
            "   labelFilter: 'Person'," +
            "   minLevel: 0," +
            "   maxLevel: 3" +
            "})" +
            "YIELD nodes, relationships " +
            "RETURN p, nodes, relationships;")
    Flux<PersonEntity> getSubGraph(String name);
}
