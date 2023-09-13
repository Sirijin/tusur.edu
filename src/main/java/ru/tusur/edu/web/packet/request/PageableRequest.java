package ru.tusur.edu.web.packet.request;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@Hidden
public class PageableRequest {

    @NotNull(message = "page number cannot be empty")
    @PositiveOrZero(message = "page number cannot be negative")
    private Integer page;

    @NotNull(message = "count items per page cannot be empty")
    @Positive(message = "count items per page must be positive")
    private Integer itemsPerPage;

    public Pageable getPageable() {
        return PageRequest.of(page, itemsPerPage);
    }
}
