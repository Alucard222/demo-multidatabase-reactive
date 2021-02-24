package org.neo4j.sdn6.demo.city;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.sdn6.demo.BaseEntity;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("City")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class CityEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "CLOSE_TO", direction = Relationship.Direction.OUTGOING)
    private List<CityEntity> closeCities;
}
