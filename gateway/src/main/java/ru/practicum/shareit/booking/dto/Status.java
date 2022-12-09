package ru.practicum.shareit.booking.dto;

import java.util.Optional;

public enum Status {
    //Все
    ALL,
    //Текущие
    CURRENT,
    //Завершенные
    PAST,
    //Будущие
    FUTURE,
    //Ожидающие подтверждения
    WAITING,
    //Отклонённые
    REJECTED,
    //Одобренный
    APPROVED,
    //Отмененный
    CANCELED;

    public static Optional<Status> from(String stringState) {
        for (Status status : values()) {
            if (status.name().equalsIgnoreCase(stringState)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
