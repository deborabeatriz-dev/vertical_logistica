package com.dev.vertical_logistica.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.dev.vertical_logistica.dto.UserDto;
import com.dev.vertical_logistica.service.UserService;

public class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setup() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
    }

    @Test
    void shouldCallServiceWithAllParameters() {
        Long orderId = 100L;
        LocalDate startDate = LocalDate.of(2025, 5, 1);
        LocalDate endDate = LocalDate.of(2025, 5, 31);
        List<UserDto> expectedResult = List.of(new UserDto());

        when(userService.getUsersWithFilters(orderId, startDate, endDate))
            .thenReturn(expectedResult);

        List<UserDto> result = userController.getUsersWithFilters(orderId, startDate, endDate);

        assertEquals(expectedResult, result);
        verify(userService).getUsersWithFilters(orderId, startDate, endDate);
    }

    @Test
    void shouldCallServiceWithNullParameters() {
        List<UserDto> expectedResult = new ArrayList<>();
        when(userService.getUsersWithFilters(null, null, null))
            .thenReturn(expectedResult);

        List<UserDto> result = userController.getUsersWithFilters(null, null, null);

        assertEquals(expectedResult, result);
        verify(userService).getUsersWithFilters(null, null, null);
    }

    @Test
    void shouldCallServiceWithOnlyOrderId() {
        Long orderId = 200L;
        List<UserDto> expectedResult = List.of(new UserDto());
        
        when(userService.getUsersWithFilters(orderId, null, null))
            .thenReturn(expectedResult);

        List<UserDto> result = userController.getUsersWithFilters(orderId, null, null);

        assertEquals(expectedResult, result);
        verify(userService).getUsersWithFilters(orderId, null, null);
    }

    @Test
    void shouldCallServiceWithOnlyDates() {
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 12, 31);
        List<UserDto> expectedResult = new ArrayList<>();
        
        when(userService.getUsersWithFilters(null, startDate, endDate))
            .thenReturn(expectedResult);

        List<UserDto> result = userController.getUsersWithFilters(null, startDate, endDate);

        assertEquals(expectedResult, result);
        verify(userService).getUsersWithFilters(null, startDate, endDate);
    }

    @Test
    void shouldReturnWhatServiceReturns() {
        List<UserDto> serviceResult = List.of(
            new UserDto(), 
            new UserDto(), 
            new UserDto()
        );
        
        when(userService.getUsersWithFilters(null, null, null))
            .thenReturn(serviceResult);

        List<UserDto> result = userController.getUsersWithFilters(null, null, null);

        assertEquals(3, result.size());
        assertEquals(serviceResult, result);
    }
}
