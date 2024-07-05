package nl.ing.accounts.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.ing.accounts.domain.model.AccountType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

	private UUID id;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Account Type is mandatory")
	private AccountType type;

	@PastOrPresent(message = "Opening date must be in the past or present")
	@NotNull(message = "Opening date is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate openingDate;

	@NotNull(message = "Temporary status is mandatory")
	private Boolean temporary;

	@FutureOrPresent(message = "Closure date must be in the future or present")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate closureDate;

	@Min(value = 0, message = "Initial deposit must be greater than or equal to 0")
	private Double initialDeposit;

	@NotNull(message = "Account Holder is mandatory")
	@Valid // To validate nested Person object
	private PersonDto holder;
}