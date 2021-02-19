package org.neo4j.sdn6.demo.person;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import java.util.List;

@Node("Person")
public class PersonEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer born;

    @Relationship(type = "KNOWS", direction = Direction.OUTGOING)
    private List<PersonEntity> friends;

    public PersonEntity(Long id, String name, Integer born) {
        this.id = id;
        this.name = name;
        this.born = born;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBorn() {
        return born;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }

    public List<PersonEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<PersonEntity> friends) {
        this.friends = friends;
    }
}
