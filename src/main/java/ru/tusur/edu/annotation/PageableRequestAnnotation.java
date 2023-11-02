package ru.tusur.edu.annotation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Parameters(value = {
        @Parameter(name = "page", description = "page number", schema = @Schema(type = "integer", example = "0"), required = true),
        @Parameter(name = "itemsPerPage", description = "amount of items per page", schema = @Schema(type = "integer", example = "25"), required = true)
})
public @interface PageableRequestAnnotation {
}
