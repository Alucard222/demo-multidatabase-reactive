package org.neo4j.sdn6.demo.city;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.sdn6.demo.BaseNode;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("City")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class City extends BaseNode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
