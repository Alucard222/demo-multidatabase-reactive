package org.neo4j.sdn6.demo.controller;

import org.neo4j.sdn6.demo.person.PersonEntity;
import org.neo4j.sdn6.demo.person.PersonRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/actors")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/tomHanks")
    Flux<PersonEntity> getActedInMovie(){
        return personRepository.tomHanksCareer();
    }

}
