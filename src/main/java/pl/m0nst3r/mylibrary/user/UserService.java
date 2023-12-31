package pl.m0nst3r.mylibrary.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.m0nst3r.mylibrary.user.dto.UserDto;
import pl.m0nst3r.mylibrary.user.dto.UserRegistrationDto;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserDto> findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDtoMapper::map);
    }

    @Transactional
    public ResponseEntity<?> register(UserRegistrationDto userRegistrationDto) {
        User user = new User();
        user.setEmail(userRegistrationDto.getEmail());
        user.setName(userRegistrationDto.getName());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword())); //{bcrypt}
        UserRole userRole = userRoleRepository.findByName(USER_ROLE)
                .orElseThrow(() -> new NoSuchElementException("ERROR: Role is not found.)"));
        user.getRoles().add(userRole);
        userRepository.save(user);
        return ResponseEntity.ok("User added to database.");
    }
}
