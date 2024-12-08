package com.ew.ajbackend.uwhubuser.converter;

import com.ew.ajbackend.uwhubuser.UWHubUser;
import com.ew.ajbackend.uwhubuser.dto.UserDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserConverter implements Converter<UserDto, UWHubUser> {

    @Override
    public UWHubUser convert(UserDto source) {
        UWHubUser hogwartsUser = new UWHubUser();
        hogwartsUser.setUsername(source.username());
        hogwartsUser.setEnabled(source.enabled());
        hogwartsUser.setRoles(source.roles());
        return hogwartsUser;
    }

}