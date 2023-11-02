package ru.tusur.edu.service.scheduled.user;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.service.UserService;

import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyActivityProcessor {

    private final UserService userService;

    @SneakyThrows
    public void processStuff() {
        userService.saveAll(userService.findAll().stream()
                .map(this::processUserActivity)
                .collect(Collectors.toList()));
    }

    private User processUserActivity(User user) {
        if (user.getDailyActivity() == 100.0) {
            user.incrementDaysInARow();
        }
        user.setDailyActivity(0.0);
        return user;
    }
}
