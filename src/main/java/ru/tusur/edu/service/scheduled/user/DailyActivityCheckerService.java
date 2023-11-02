package ru.tusur.edu.service.scheduled.user;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tusur.edu.security.entity.User;
import ru.tusur.edu.security.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyUserActivityChecker {

    private final UserService userService;

    @Scheduled(cron = "@daily")
    public void checkDailyActivity() {
        try {
            List<User> users = userService.findAll();
            List<User> updatedUsers = users.stream()
                    .map(this::processUserActivity)
                    .collect(Collectors.toList());
            userService.saveAll(updatedUsers);
        } catch (Exception e) {
            // Обработка ошибок при получении пользователей
            // Можно добавить логгирование или другую обработку ошибок
        }
    }

    private User processUserActivity(User user) {
        if (user.getDailyActivity() == 100) {
            user.incrementDaysInARow();
        }
        user.setDailyActivity(0.0);
        return user;
    }
}
