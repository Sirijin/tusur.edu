package ru.tusur.edu.security.web.request;

import lombok.NonNull;

import java.time.LocalDate;

public record RegisterRequest(@NonNull String username, @NonNull String email, @NonNull String firstName,
                              @NonNull String middleName, @NonNull String lastName, @NonNull LocalDate birthday) {
}
