package nl.ing.accounts.domain.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nl.ing.accounts.domain.dao.AccountRepository;
import nl.ing.accounts.domain.dao.PersonRepository;
import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.PersonDto;
import nl.ing.accounts.domain.dto.UpdateAccountDto;
import nl.ing.accounts.domain.mapper.AccountMapper;
import nl.ing.accounts.domain.mapper.PersonMapper;
import nl.ing.accounts.domain.model.Account;
import nl.ing.accounts.domain.model.Person;
import nl.ing.accounts.domain.utility.AccountServiceUtil;

@Service
public class AccountsService {

	private final AccountRepository accountRepository;
	private final PersonRepository personRepository;
	private final AccountMapper accountMapper;
	private final PersonMapper personMapper;

	@Autowired
	public AccountsService(AccountRepository accountRepository, PersonRepository personRepository,
			AccountMapper accountMapper, PersonMapper personMapper) {
		this.accountRepository = accountRepository;
		this.personRepository = personRepository;
		this.accountMapper = accountMapper;
		this.personMapper = personMapper;
	}

	public List<AccountDto> getAllAccounts() {
		return accountRepository.findAll().stream().map(accountMapper::toDto).collect(Collectors.toList());
	}

	public AccountDto getAccountById(UUID id) {
		Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
		return accountMapper.toDto(account);
	}

	public AccountDto createAccount(AccountDto accountDto) {
		// Convert DTO to entity
		Account account = accountMapper.toEntity(accountDto);
		Person person = personMapper.toEntity(accountDto.getHolder());

		// Save person first
		Person savedPerson = personRepository.save(person);

		// Set the saved person to account and save account
		account.setHolder(savedPerson);
		Account savedAccount = accountRepository.save(account);

		return accountMapper.toDto(savedAccount);
	}

	@Transactional
	public AccountDto updateAccount(UUID id, UpdateAccountDto updatedAccountDto) {
		Account existingAccount = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		// validate all the data
		AccountServiceUtil.validateUpdateAccountUserData(updatedAccountDto, existingAccount);

		// Update existingAccount with updatedAccountDto fields
		// Use MapStruct to update only non-null fields
		accountMapper.updateAccountFromUpdateDto(updatedAccountDto, existingAccount);

		// Update or save person
		PersonDto updatedPerson = personMapper.toDto(existingAccount.getHolder());
		Person savedPerson = personRepository.save(personMapper.toEntity(updatedPerson));
		existingAccount.setHolder(savedPerson);

		Account updatedAccount = accountRepository.save(existingAccount);
		return accountMapper.toDto(updatedAccount);
	}

	public void deleteAccount(UUID id) {
		Account accountToDelete = accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found"));

		Person holder = accountToDelete.getHolder();

		accountRepository.delete(accountToDelete);

		// Check if the holder has any other accounts
		boolean hasOtherAccounts = accountRepository.existsByHolder(holder);
		if (!hasOtherAccounts) {
			personRepository.delete(holder);
		}
	}
}