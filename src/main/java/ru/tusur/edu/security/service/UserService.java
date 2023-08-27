package ru.tusur.edu.security.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.security.authentication.CustomPrincipal;
import ru.tusur.edu.security.authentication.JwtService;
import ru.tusur.edu.security.entity.Role;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.mapper.RoleMapper;
import ru.tusur.edu.security.mapper.UserMapper;
import ru.tusur.edu.security.repository.UserRepository;
import ru.tusur.edu.security.type.RoleSet;
import ru.tusur.edu.security.web.packet.dto.UserDto;
import ru.tusur.edu.security.web.packet.dto.UserResponse;
import ru.tusur.edu.security.web.packet.request.LoginRequest;
import ru.tusur.edu.security.web.packet.request.RegisterRequest;
import ru.tusur.edu.security.web.packet.response.LoginResponse;
import ru.tusur.edu.security.web.packet.response.RegisterResponse;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

    @SneakyThrows
    public UserResponse findAll() {
        return UserResponse
                .builder()
                .users(userRepository.findAll().stream()
                        .map(userMapper::map)
                        .filter(Optional::isPresent)
                        .flatMap(Optional::stream)
                        .collect(Collectors.toSet()))
                .total(userRepository.count())
                .build();
    }

    @SneakyThrows
    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).flatMap(userMapper::map);
    }

    @SneakyThrows
    @Transactional
    public Optional<UserDto> saveUser(UserDto dto) {
        String password = passwordEncoder.encode(dto.getPassword());
        return userMapper.map(dto, password)
                .map(userRepository::save)
                .flatMap(userMapper::map);
    }

    @SneakyThrows
    @Transactional
    public Optional<UserDto> updateUser(Long id, UserDto dto) {

        if (!userRepository.existsById(id) || userRepository.findById(id).isEmpty()) {
            return Optional.empty();
        }

        User user = getUserById(id);
        Role role = roleMapper.map(dto.getRole());
        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            user.setUsername(dto.getUsername());
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getRole() != null && !user.getRole().equals(role)) {
            user.setRole(role);
        }

        return userMapper.map(userRepository.save(user));
    }

    @SneakyThrows
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.existsById(id) || userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @SneakyThrows
    @Transactional
    public LoginResponse loginUser(LoginRequest request) {
        User user = getUserByUsername(request.getUsername());
        String token = jwtService.createToken(user);
        return LoginResponse.of(token);
    }

    @SneakyThrows
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("User with this username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("This email is already in use");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Role role = roleService.findByName(RoleSet.USER_ROLE).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        User savedUser = userRepository.save(userMapper.map(request, encodedPassword, role).orElseThrow(() -> new Exception("Failed to map User to UserDto")));

        UserDto userDto = userMapper.map(savedUser).orElseThrow(() -> new Exception("Failed to map User to UserDto"));
        return new RegisterResponse(userDto);
    }

    @SneakyThrows
    public UserDto getUserInfo(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        User user = userRepository.findById(principal.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        return userMapper.map(user)
                .orElseThrow(() -> new RuntimeException("UserDto mapping failed"));
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
