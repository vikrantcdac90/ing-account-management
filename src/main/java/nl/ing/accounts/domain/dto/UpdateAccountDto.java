package nl.ing.accounts.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.ing.accounts.domain.model.AccountType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountDto {

	private UUID id;

	@Enumerated(EnumType.STRING)
	private AccountType type;

	@PastOrPresent(message = "Opening date must be in the past or present")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate openingDate;

	private Boolean temporary;

	@FutureOrPresent(message = "Closure date must be in the future or present")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate closureDate;

	private Double initialDeposit;

	@Valid // To validate nested Person object
	private UpdatePersonDto holder;
}
