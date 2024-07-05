package nl.ing.accounts.domain.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.UpdateAccountDto;
import nl.ing.accounts.domain.mapper.AccountMapper;
import nl.ing.accounts.domain.mapper.PersonMapper;
import nl.ing.accounts.domain.response.AccountApiResponse;
import nl.ing.accounts.domain.service.AccountsService;
import nl.ing.accounts.domain.utility.AccountServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Tag(name = "Accounts", description = "API for managing accounts")
public class AccountsController {

    private final AccountsService accountService;
    private final AccountMapper accountMapper;
    private final PersonMapper personMapper;

    @Autowired
    public AccountsController(AccountsService accountService, AccountMapper accountMapper, PersonMapper personMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.personMapper = personMapper;
    }

    @Operation(summary = "Get all accounts", description = "Retrieve all accounts")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved accounts"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<AccountApiResponse<List<AccountDto>>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(new AccountApiResponse<>(true, "Accounts retrieved successfully", accounts));
    }

    @Operation(summary = "Get account by ID", description = "Retrieve an account by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved account"),
        @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AccountApiResponse<AccountDto>> getAccountById(@PathVariable UUID id) {
        AccountDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(new AccountApiResponse<>(true, "Account retrieved successfully", account));
    }

    @Operation(summary = "Create a new account", description = "Create a new account with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created account"),
        @ApiResponse(responseCode = "400", description = "Validation errors or bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/create")
    public ResponseEntity<AccountApiResponse<?>> createAccount(@Valid @RequestBody AccountDto accountDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AccountApiResponse<>(false, "Validation errors", errors));
        }

        try {
            AccountServiceUtil.validateCreateAccountUserData(accountDto);
            UUID accountId = UUID.randomUUID();
            accountDto.setId(accountId);
            AccountDto createdAccount = accountService.createAccount(accountDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AccountApiResponse<>(true, "Account created successfully", createdAccount));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountApiResponse<>(false, "Failed to create account: " + e.getMessage()));
        }
    }

    @Operation(summary = "Update an account", description = "Update an account with the provided details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated account"),
        @ApiResponse(responseCode = "400", description = "Validation errors or bad request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccountApiResponse<?>> updateAccount(@PathVariable UUID id,
            @Valid @RequestBody UpdateAccountDto accountDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AccountApiResponse<>(false, "Validation errors", errors));
        }
        accountDto.setId(id);
        try {
            AccountDto updatedAccount = accountService.updateAccount(id, accountDto);
            return ResponseEntity.ok(new AccountApiResponse<>(true, "Account updated successfully", updatedAccount));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new AccountApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountApiResponse<>(false, "Failed to update account: " + e.getMessage()));
        }
    }

    @Operation(summary = "Delete an account", description = "Delete an account by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted account"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountApiResponse<?>> deleteAccount(@PathVariable UUID id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.ok(new AccountApiResponse<>(true, "Account deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AccountApiResponse<>(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountApiResponse<>(false, "Failed to delete account: " + e.getMessage()));
        }
    }
}
