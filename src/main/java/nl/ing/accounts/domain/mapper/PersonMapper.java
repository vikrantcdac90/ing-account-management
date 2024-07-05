package nl.ing.accounts.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import nl.ing.accounts.domain.dto.PersonDto;
import nl.ing.accounts.domain.dto.UpdatePersonDto;
import nl.ing.accounts.domain.model.Person;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonMapper {

	@Mapping(target = "id", ignore = true)
	void updatePersonFromDto(PersonDto personDto, @MappingTarget Person person);

	@Mapping(target = "id", ignore = true)
	void updatePersonFromUpdateDto(UpdatePersonDto updatePersonDto, @MappingTarget Person person);

	PersonDto toDto(Person person);

	Person toEntity(PersonDto personDto);

	PersonDto toEntity(UpdatePersonDto personDto);
}
