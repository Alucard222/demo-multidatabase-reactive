package org.neo4j.sdn6.demo.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.sdn6.demo.BaseNode;
import org.neo4j.sdn6.demo.city.City;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

@Node("Person")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity extends BaseNode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer born;

    @Relationship(type = "KNOWS", direction = Direction.OUTGOING)
    private List<PersonEntity> friends;

    @Relationship(type = "LIVES_IN", direction = Direction.OUTGOING)
    private City location;
}
