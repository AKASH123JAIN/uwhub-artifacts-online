package com.ew.ajbackend.uwhubuser;


import com.ew.ajbackend.system.Result;
import com.ew.ajbackend.system.StatusCode;
import com.ew.ajbackend.uwhubuser.converter.UserDtoToUserConverter;
import com.ew.ajbackend.uwhubuser.converter.UserToUserDtoConverter;
import com.ew.ajbackend.uwhubuser.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.endpoint.base-url}/users")
public class UserController {

    private final UserService userService;

    private final UserDtoToUserConverter userDtoToUserConverter; // Convert userDto to user.

    private final UserToUserDtoConverter userToUserDtoConverter; // Convert user to userDto.


    public UserController(UserService userService, UserDtoToUserConverter userDtoToUserConverter, UserToUserDtoConverter userToUserDtoConverter) {
        this.userService = userService;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserDtoConverter = userToUserDtoConverter;
    }

    @GetMapping
    public Result findAllUsers() {
        List<UWHubUser> foundUWHubUsers = this.userService.findAll();

        // Convert foundUsers to a list of UserDtos.
        List<UserDto> userDtos = foundUWHubUsers.stream()
                .map(this.userToUserDtoConverter::convert)
                .collect(Collectors.toList());

        // Note that UserDto does not contain password field.
        return new Result(true, StatusCode.SUCCESS, "Find All Success", userDtos);
    }

    @GetMapping("/{userId}")
    public Result findUserById(@PathVariable Integer userId) {
        UWHubUser foundHogwartsUser = this.userService.findById(userId);
        UserDto userDto = this.userToUserDtoConverter.convert(foundHogwartsUser);
        return new Result(true, StatusCode.SUCCESS, "Find One Success", userDto);
    }

    /**
     * We are not using UserDto, but User, since we require password.
     *
     * @param newHogwartsUser
     * @return
     */
    @PostMapping
    public Result addUser(@Valid @RequestBody UWHubUser newHogwartsUser) {
        UWHubUser savedUser = this.userService.save(newHogwartsUser);
        UserDto savedUserDto = this.userToUserDtoConverter.convert(savedUser);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedUserDto);
    }

    // We are not using this to update password, need another changePassword method in this class.
    @PutMapping("/{userId}")
    public Result updateUser(@PathVariable Integer userId, @Valid @RequestBody UserDto userDto) {
        UWHubUser update = this.userDtoToUserConverter.convert(userDto);
        UWHubUser updatedHogwartsUser = this.userService.update(userId, update);
        UserDto updatedUserDto = this.userToUserDtoConverter.convert(updatedHogwartsUser);
        return new Result(true, StatusCode.SUCCESS, "Update Success", updatedUserDto);
    }

    @DeleteMapping("/{userId}")
    public Result deleteUser(@PathVariable Integer userId) {
        this.userService.delete(userId);
        return new Result(true, StatusCode.SUCCESS, "Delete Success");
    }

}