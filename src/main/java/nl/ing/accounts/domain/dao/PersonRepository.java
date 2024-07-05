package nl.ing.accounts.domain.dao;

import nl.ing.accounts.domain.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}