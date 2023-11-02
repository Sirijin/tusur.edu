package ru.tusur.edu.service.scheduled.user;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DailyActivityCheckerService {

    private final DailyActivityProcessor dailyActivityProcessor;

    @Scheduled(cron = "@daily")
    public void checkDailyActivity() {
        try {
            dailyActivityProcessor.processStuff();
        } catch (Exception ignored) {
        }
    }

}
