package ru.practicum.shareit.booking.model;

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

    public static Status from(String stateParam) {
        for (Status value : Status.values()) {
            if (value.name().equals(stateParam)) {
                return value;
            }
        }
        return null;
    }
}
