package pl.m0nst3r.mylibrary.user;

import pl.m0nst3r.mylibrary.user.dto.UserDto;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDtoMapper {
    static UserDto map(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        Set<String> roles = user.getRoles()
                .stream()
                .map(UserRole::getName)
                .collect(Collectors.toSet());
        return new UserDto(email, password, roles);
    }
}
