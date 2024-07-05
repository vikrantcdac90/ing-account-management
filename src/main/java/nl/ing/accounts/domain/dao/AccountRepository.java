package nl.ing.accounts.domain.dao;

import nl.ing.accounts.domain.model.Account;
import nl.ing.accounts.domain.model.Person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
	boolean existsByHolder(Person holder);
}
