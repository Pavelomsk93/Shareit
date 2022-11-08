package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.enums.Status;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/bookings")
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public List<BookingDto> getAllBookings(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        Status state = Status.from(stateParam);
        if (state == null) {
            throw new IllegalArgumentException("Unknown state: " + stateParam);
        }
        log.info("GetMapping/Получение всех бронирований пользователя с id: " + userId);
        return bookingService.getAllBookings(userId, stateParam);
    }

    @GetMapping(value = "/owner")
    public List<BookingDto> getAllBookingItemsUser(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(name = "state", defaultValue = "ALL") String stateParam) {
        Status state = Status.from(stateParam);
        if (state == null) {
            throw new IllegalArgumentException("Unknown state: " + stateParam);
        }
        log.info("GetMapping/Получение всех бронирований для вещей пользователя с id: " + userId);
        return bookingService.getAllBookingItemsUser(userId, stateParam);
    }

    @GetMapping(value = "/{bookingId}")
    public BookingDto getBookingById(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId) {
        log.info("GetMapping/Получение бронирования по id: " + bookingId +
                " для пользователя с id: " + userId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @PostMapping
    public BookingDto createBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @Valid @RequestBody BookingCreateDto bookingDto) {
        log.info("PostMapping/Создание бронирования:" + bookingDto +
                " пользователя с id: " + userId);
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping(value = "/{bookingId}")
    public BookingDto patchBooking(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @PathVariable Long bookingId,
            @RequestParam Boolean approved) {
        log.info("PatchMapping/Обновление бронирования с id: " + bookingId +
                " для пользователя с id: " + userId +
                " статус доступности вещи для бронирования: " + approved);
        return bookingService.patchBooking(userId, bookingId, approved);
    }

    @DeleteMapping("/{bookingId}")
    public void deleteById(@PathVariable Long bookingId) {
        log.info("DeleteMapping/Удаление бронирования по id: " + bookingId);
        bookingService.removeBookingById(bookingId);
    }
}

