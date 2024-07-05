package nl.ing.accounts.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import nl.ing.accounts.domain.dto.AccountDto;
import nl.ing.accounts.domain.dto.UpdateAccountDto;
import nl.ing.accounts.domain.model.Account;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = PersonMapper.class)
public interface AccountMapper {

	@Mapping(target = "id", ignore = true)
	void updateAccountFromDto(AccountDto accountDto, @MappingTarget Account account);

	@Mapping(target = "id", ignore = true)
	void updateAccountFromUpdateDto(UpdateAccountDto updateAccountDto, @MappingTarget Account account);

	AccountDto toDto(Account account);

	Account toEntity(AccountDto accountDto);

	AccountDto toAccountEntity(UpdateAccountDto upaccountdto);

}
