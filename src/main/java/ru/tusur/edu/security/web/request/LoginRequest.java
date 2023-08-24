package ru.tusur.edu.security.web.request;

import lombok.NonNull;

public record LoginRequest(@NonNull String email, @NonNull String password) {
}
