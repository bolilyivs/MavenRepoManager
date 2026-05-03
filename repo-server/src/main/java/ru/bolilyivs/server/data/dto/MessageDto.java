package ru.bolilyivs.server.data.dto;

import io.micronaut.serde.annotation.Serdeable;
import io.swagger.v3.oas.annotations.media.Schema;

@Serdeable
@Schema(name = "MessageDto", description = "Сообщение")
public record MessageDto<T>(
        @Schema(description = "Сообщение")
        T message
) {
    public static <T> MessageDto<T> of(T msg) {
        return new MessageDto<>(msg);
    }
}
