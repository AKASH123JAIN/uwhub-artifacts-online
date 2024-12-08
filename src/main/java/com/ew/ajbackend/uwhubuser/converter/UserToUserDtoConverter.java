package com.ew.ajbackend.uwhubuser.converter;

import com.ew.ajbackend.uwhubuser.UWHubUser;
import com.ew.ajbackend.uwhubuser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<UWHubUser, UserDto> {
    @Override
    public UserDto convert(UWHubUser source) {
        // We are not setting password in DTO.
        final UserDto userDto = new UserDto(source.getId(),
                source.getUsername(),
                source.isEnabled(),
                source.getRoles());
        return userDto;
    }
}
