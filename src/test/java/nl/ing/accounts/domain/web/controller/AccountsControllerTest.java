package nl.ing.accounts.domain.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.UpdateAccountDto;
import nl.ing.accounts.domain.mapper.AccountMapper;
import nl.ing.accounts.domain.mapper.PersonMapper;
import nl.ing.accounts.domain.response.AccountApiResponse;
import nl.ing.accounts.domain.service.AccountsService;

public class AccountsControllerTest {

	@Mock
	private AccountsService accountsService;

	@Mock
	private AccountMapper accountMapper;

	@Mock
	private PersonMapper personMapper;

	@InjectMocks
	private AccountsController accountsController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getAllAccounts_shouldReturnAllAccounts() {
		// Mock data
		List<AccountDto> accounts = Arrays.asList(new AccountDto(UUID.randomUUID(), null, null, null, null, null, null),
				new AccountDto(UUID.randomUUID(), null, null, null, null, null, null));
		when(accountsService.getAllAccounts()).thenReturn(accounts);

		// Call controller method
		ResponseEntity<AccountApiResponse<List<AccountDto>>> responseEntity = accountsController.getAllAccounts();

		// Verify
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(accounts, responseEntity.getBody().getData());
	}

	@Test
	public void getAccountById_shouldReturnAccountById() {
		// Mock data
		UUID accountId = UUID.randomUUID();
		AccountDto accountDto = new AccountDto(accountId, null, null, null, null, null, null);
		when(accountsService.getAccountById(accountId)).thenReturn(accountDto);

		// Call controller method
		ResponseEntity<AccountApiResponse<AccountDto>> responseEntity = accountsController.getAccountById(accountId);

		// Verify
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(accountDto, responseEntity.getBody().getData());
	}

	

	@Test
	public void updateAccount_shouldUpdateAccount() {
		// Mock data
		UUID accountId = UUID.randomUUID();
		UpdateAccountDto updateAccountDto = new UpdateAccountDto(accountId, null, null, null, null, null, null);
		AccountDto updatedAccountDto = new AccountDto(accountId, null, null, null, null, null, null);
		when(accountsService.updateAccount(eq(accountId), any(UpdateAccountDto.class))).thenReturn(updatedAccountDto);

		// Call controller method
		ResponseEntity<AccountApiResponse<?>> responseEntity = accountsController.updateAccount(accountId,
				updateAccountDto, mockBindingResult());

		// Verify
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(updatedAccountDto, responseEntity.getBody().getData());
	}

	@Test
	public void deleteAccount_shouldDeleteAccount() {
		// Mock data
		UUID accountId = UUID.randomUUID();
		doNothing().when(accountsService).deleteAccount(accountId);

		// Call controller method
		ResponseEntity<AccountApiResponse<?>> responseEntity = accountsController.deleteAccount(accountId);

		// Verify
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(true, responseEntity.getBody().isSuccess());
	}

	private BindingResult mockBindingResult() {
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);
		when(bindingResult.getFieldErrors()).thenReturn(Collections.emptyList());
		return bindingResult;
	}
}
