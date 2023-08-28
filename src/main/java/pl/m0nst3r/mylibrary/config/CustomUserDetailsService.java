package pl.m0nst3r.mylibrary.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.m0nst3r.mylibrary.user.UserService;
import pl.m0nst3r.mylibrary.user.dto.UserDto;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findUserByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User with email %s not found".formatted(username)));
    }


    private UserDetails createUserDetails(UserDto userDto) {
        return User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .roles(userDto.getRoles().toArray(String[]::new))
                .build();

    }
}
