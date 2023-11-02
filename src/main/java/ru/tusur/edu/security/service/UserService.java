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
import ru.tusur.edu.security.mapper.UserMapper;
import ru.tusur.edu.security.repository.UserRepository;
import ru.tusur.edu.security.type.RoleSet;
import ru.tusur.edu.security.web.packet.dto.UserDto;
import ru.tusur.edu.security.web.packet.dto.UserResponse;
import ru.tusur.edu.security.web.packet.request.LoginRequest;
import ru.tusur.edu.security.web.packet.response.LoginResponse;
import ru.tusur.edu.security.web.packet.response.RegisterResponse;
import ru.tusur.edu.type.task.TaskDifficultyType;
import ru.tusur.edu.web.packet.request.PageableRequest;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    private final UserMapper mapper;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByUsername(username);
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public UserResponse findAllPageable(PageableRequest pageableRequest) {
        return UserResponse
                .builder()
                .users(userRepository.findAll(pageableRequest.getPageable()).getContent().stream().map(mapper::toDto).toList())
                .total(userRepository.count())
                .build();
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @SneakyThrows
    public User findOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public UserDto findOneDto(Long taskId) {
        return userRepository.findById(taskId).map(mapper::toDto).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + taskId));
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        User user = getUserByUsername(request.getUsername());
        String token = jwtService.createToken(user);
        return LoginResponse.of(token);
    }

    @SneakyThrows
    public UserDto save(UserDto dto) {
        User user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(roleService.map(dto.getRole()));

        return mapper.toDto(userRepository.save(user));
    }

    @SneakyThrows
    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    @SneakyThrows
    public UserDto update(Long id, UserDto updatedDto) {
        User user = findOne(id);
        Role role = roleService.map(updatedDto.getRole());
        updateFields(updatedDto, user, role);

        return mapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @SneakyThrows
    public RegisterResponse register(UserDto dto) {
        if (userRepository.existsByUsernameOrEmail(dto.getUsername(), dto.getEmail())) {
            throw new RuntimeException("This user already exists");
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        Role role = roleService.findByName(RoleSet.USER_ROLE).orElseThrow(() -> new EntityNotFoundException("Role not found"));
        User user = mapper.toEntity(dto);

        user.setPassword(encodedPassword);
        user.setRole(role);

        UserDto userDto = mapper.toDto(user);
        return new RegisterResponse(userDto);
    }

    @SneakyThrows
    @Transactional(readOnly = true)
    public UserDto getUserInfo(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        return mapper.toDto(findOne(principal.getId()));
    }

    @SneakyThrows
    @Transactional
    public void addBalance(User user, TaskDifficultyType difficultyType) {
        user.increaseBalanceBy(difficultyType.getCoinsReward());
    }

    @SneakyThrows
    @Transactional
    public void addActivity(User user, TaskDifficultyType difficultyType) {
        user.increaseDailyActivityBy(difficultyType.getActivityReward());
    }

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private void updateFields(UserDto dto, User user, Role role) {
        if (!dto.getUsername().equals(user.getUsername())) {
            user.setUsername(dto.getUsername());
        }

        if (!dto.getEmail().equals(user.getEmail())) {
            user.setEmail(dto.getEmail());
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (!user.getRole().equals(role)) {
            user.setRole(role);
        }
    }
}
