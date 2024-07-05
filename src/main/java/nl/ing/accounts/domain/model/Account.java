package nl.ing.accounts.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	@Id
	private UUID id;

	@Enumerated(EnumType.STRING)
	@NotNull
	private AccountType type;

	@PastOrPresent
	@NotNull
	private LocalDate openingDate;

	@NotNull
	private Boolean temporary;

	@FutureOrPresent
	private LocalDate closureDate;

	@Min(0)
	private Double initialDeposit;

	@ManyToOne
	@JoinColumn(name = "person_id")
	@NotNull
	private Person holder;

}
