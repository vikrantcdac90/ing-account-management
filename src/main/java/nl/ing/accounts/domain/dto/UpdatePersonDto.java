package nl.ing.accounts.domain.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePersonDto {

	private Long id;

	@Size(min = 3, max = 35, message = "First Name must be at least 3 characters long")
	private String firstName;

	@Size(min = 3, max = 35, message = "Last Name must be at least 3 characters long")
	private String lastName;

	@Past(message = "Date of birth must be in past")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;

	@Email(message = "Email should be valid")
	private String email;


}
