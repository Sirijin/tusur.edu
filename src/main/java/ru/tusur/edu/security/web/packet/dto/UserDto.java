package ru.tusur.edu.security.web.packet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ru.tusur.edu.security.type.RoleSet;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {

    @PositiveOrZero
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NonNull
    @NotBlank
    private String email;

    @NonNull
    @NotBlank
    private String username;

    @NonNull
    @NotBlank
    private String password;

    @NonNull
    @NotBlank
    private String firstName;

    @NonNull
    @NotBlank
    private String middleName;

    @NonNull
    @NotBlank
    private String lastName;

    @NonNull
    @DateTimeFormat
    private LocalDate birthday;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer balance;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Double dailyActivity;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer daysInARow;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private RoleSet role;
}
