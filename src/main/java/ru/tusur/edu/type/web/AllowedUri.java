package ru.tusur.edu.type.web;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AllowedUri {
    AUTH(new String[]{"/api/v1/auth/**"}),
    SWAGGER(new String[]{"/swagger-ui/**", "/v3/api-docs/**"});

    private final String[] uris;

    public static String[] getAllowedUris() {
        return Arrays.stream(AllowedUri.values())
                .map(AllowedUri::getUris)
                .flatMap(Arrays::stream)
                .toArray(String[]::new);
    }
}
