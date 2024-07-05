package nl.ing.accounts.domain.utility;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.UpdateAccountDto;
import nl.ing.accounts.domain.model.Account;

public class AccountServiceUtil {

	public static void validateCreateAccountUserData(AccountDto accountDto) {
		LocalDate openingDate = accountDto.getOpeningDate();
		LocalDate closureDate = accountDto.getClosureDate();
		boolean isTemporary = accountDto.getTemporary();
		if (accountDto.getInitialDeposit() <= 0) {
			throw new RuntimeException("Initial deposit cannot be negative or zero");
		}

		if (isTemporary) {
			if (closureDate == null) {
				throw new IllegalArgumentException("Closure date is mandatory for temporary accounts.");
			}
			if (!closureDate.isAfter(openingDate.plusMonths(2))) {
				throw new IllegalArgumentException("Closure date must be at least 2 months after the opening date.");
			}
		}

		LocalDate now = LocalDate.now();
		long diff = ChronoUnit.DAYS.between(openingDate, now);

		if (openingDate.isAfter(now) || diff > 30) {
			throw new RuntimeException("Opening date must be within the past 30 days and cannot be in the future");
		}

	}

	public static void validateUpdateAccountUserData(UpdateAccountDto updatedAccountDto, Account existingAccount) {

		// check if UUID is updated
		if (!existingAccount.getId().equals(updatedAccountDto.getId())) {
			throw new RuntimeException("UUID cant be updated after creation");
		}

		if (updatedAccountDto.getHolder().getDateOfBirth() != null) {
			LocalDate dob = updatedAccountDto.getHolder().getDateOfBirth();
			if (Period.between(dob, LocalDate.now()).getYears() < 18) {
				throw new RuntimeException("Person must be at least 18 years old");
			}
		}

		// Check if the AccountType is being changed
		if (updatedAccountDto.getType() != null && !updatedAccountDto.getType().equals(existingAccount.getType())) {
			throw new RuntimeException("Account type cannot be changed after creation");
		}

		// Check if the openingDate is being changed
		if (updatedAccountDto.getOpeningDate() != null
				&& !updatedAccountDto.getOpeningDate().equals(existingAccount.getOpeningDate())) {
			throw new RuntimeException("Opening date cannot be changed after creation");
		}

		boolean changedToTemporary = false;

		if (updatedAccountDto.getTemporary() != null) {
			if (updatedAccountDto.getTemporary() == true && existingAccount.getTemporary() == false)
				changedToTemporary = true;
		}

		if (updatedAccountDto.getClosureDate() != null) {
			if (changedToTemporary && updatedAccountDto.getClosureDate().isBefore(LocalDate.now().plusMonths(1))) {
				throw new RuntimeException(
						"Closure date must be at least 1 month after the modification date when converting to a temporary account");
			}
		}

	}

}
