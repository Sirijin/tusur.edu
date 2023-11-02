package ru.tusur.edu.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.tusur.edu.security.authentication.CustomPrincipal;

public class UserUtil {

    private UserUtil() {
    }

    public static Long getUserId() {
        return ((CustomPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }
}
