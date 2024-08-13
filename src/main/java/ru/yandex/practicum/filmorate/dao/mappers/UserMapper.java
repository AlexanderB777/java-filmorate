package ru.yandex.practicum.filmorate.dao.mappers;

import org.mapstruct.Mapper;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

    List<UserDto> toDto(List<User> users);

    List<User> toEntity(List<UserDto> userDtoList);
}
