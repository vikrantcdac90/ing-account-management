package nl.ing.accounts.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import nl.ing.accounts.domain.dao.AccountRepository;
import nl.ing.accounts.domain.dao.PersonRepository;
import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.PersonDto;
import nl.ing.accounts.domain.mapper.AccountMapper;
import nl.ing.accounts.domain.mapper.PersonMapper;
import nl.ing.accounts.domain.model.Account;
import nl.ing.accounts.domain.model.AccountType;
import nl.ing.accounts.domain.model.Person;

@ExtendWith(MockitoExtension.class)
class AccountsServiceTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private PersonRepository personRepository;

	@Mock
	private AccountMapper accountMapper;

	@Mock
	private PersonMapper personMapper;

	@InjectMocks
	private AccountsService accountsService;

	private UUID accountId;
	private Account account;
	private AccountDto accountDto;
	private Person person;
	private PersonDto personDto;

	@BeforeEach
	void setUp() {
		accountId = UUID.randomUUID();

		// Create a Person instance
		person = new Person();
		person.setId(1L); // Assuming ID is of type Long in Person class
		person.setFirstName("John");
		person.setLastName("Doe");
		person.setDateOfBirth(LocalDate.of(1990, 1, 1));
		person.setEmail("john.doe@example.com");

		// Create an Account instance
		account = new Account();
		account.setId(accountId);
		account.setType(AccountType.SAVINGS);
		account.setOpeningDate(LocalDate.now());
		account.setTemporary(false);
		account.setClosureDate(LocalDate.now().plusYears(1));
		account.setInitialDeposit(100.0);
		account.setHolder(person);

		// Create a PersonDto instance
		personDto = new PersonDto();
		personDto.setId(1L); // Set the ID accordingly
		personDto.setFirstName("John");
		personDto.setLastName("Doe");
		personDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
		personDto.setEmail("john.doe@example.com");

		// Create an AccountDto instance
		accountDto = new AccountDto();
		accountDto.setId(accountId);
		accountDto.setType(AccountType.SAVINGS);
		accountDto.setOpeningDate(LocalDate.now());
		accountDto.setTemporary(false);
		accountDto.setClosureDate(LocalDate.now().plusYears(1));
		accountDto.setInitialDeposit(100.0);
		accountDto.setHolder(personDto);
	}

	@Test
	void getAllAccounts() {
		// Mock repository behavior
		when(accountRepository.findAll()).thenReturn(Arrays.asList(account));
		when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

		// Call service method
		List<AccountDto> result = accountsService.getAllAccounts();

		// Verify results
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(accountId, result.get(0).getId());
		assertEquals(personDto, result.get(0).getHolder());

		// Verify repository interactions
		verify(accountRepository, times(1)).findAll();
		verifyNoMoreInteractions(accountRepository, personRepository);
	}

	@Test
	void getAccountById() {
		// Mock repository behavior
		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

		// Call service method
		AccountDto result = accountsService.getAccountById(accountId);

		// Verify results
		assertNotNull(result);
		assertEquals(accountId, result.getId());
		assertEquals(personDto, result.getHolder());

		// Verify repository interactions
		verify(accountRepository, times(1)).findById(accountId);
		verifyNoMoreInteractions(accountRepository, personRepository);
	}

	@Test
	void createAccount() {
		// Mock repository behavior
		when(personMapper.toEntity(any(PersonDto.class))).thenReturn(person);
		when(personRepository.save(any(Person.class))).thenReturn(person);
		when(accountMapper.toEntity(any(AccountDto.class))).thenReturn(account);
		when(accountRepository.save(any(Account.class))).thenReturn(account);
		when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

		// Call service method
		AccountDto result = accountsService.createAccount(accountDto);

		// Verify results
		assertNotNull(result);
		assertEquals(accountId, result.getId());
		assertEquals(personDto, result.getHolder());

		// Verify repository interactions
		verify(personRepository, times(1)).save(any(Person.class));
		verify(accountRepository, times(1)).save(any(Account.class));
		verifyNoMoreInteractions(accountRepository, personRepository);
	}

	@Test
	void deleteAccount() {
		// Mock repository behavior
		when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
		when(accountRepository.existsByHolder(person)).thenReturn(false);

		// Call service method
		accountsService.deleteAccount(accountId);

		// Verify repository interactions
		verify(accountRepository, times(1)).findById(accountId);
		verify(accountRepository, times(1)).delete(any(Account.class));
		verify(personRepository, times(1)).delete(any(Person.class));
		verifyNoMoreInteractions(accountRepository, personRepository);
	}
}
