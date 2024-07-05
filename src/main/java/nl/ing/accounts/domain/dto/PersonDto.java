package nl.ing.accounts.domain.dto;

import java.time.LocalDate;
import java.time.Period;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

	@Id
	private Long id;

	@NotBlank(message = "First Name is mandatory")
	@Size(min = 3, max = 35, message = "Name must be at least 3 characters long")
	private String firstName;

	@Size(min = 3, max = 35, message = "Last Name must be at least 3 characters long")
	@NotBlank(message = "Last name is mandatory")
	private String lastName;

	@Past(message = "Date of birth must be in past")
	@NotNull(message = "Date of birth is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;

	@NotNull(message = "Email is mandatory")
	@Email(message = "Email should be valid")
	private String email;

	@AssertTrue(message = "Person must be at least 18 years old")
	public boolean isAdult() {
		return dateOfBirth != null && Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
	}

}
