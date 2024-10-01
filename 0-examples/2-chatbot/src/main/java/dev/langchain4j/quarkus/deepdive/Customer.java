package dev.langchain4j.quarkus.deepdive;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Customer extends PanacheEntity {

    String firstName;
    String lastName;
}
