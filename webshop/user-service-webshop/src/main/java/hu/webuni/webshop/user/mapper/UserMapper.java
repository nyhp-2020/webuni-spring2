package hu.webuni.webshop.user.mapper;

import org.mapstruct.Mapper;

import hu.webuni.webshop.user.dto.WebshopUserDto;
import hu.webuni.webshop.user.model.WebshopUser;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	WebshopUser dtoToWebshopUser(WebshopUserDto webshopUserDto);
	WebshopUserDto webshopUserToDto(WebshopUser webshopUser);
}
