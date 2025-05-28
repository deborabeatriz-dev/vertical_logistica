package com.dev.vertical_logistica.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ParseResultDtoTest {

    @Test
    void shouldCreateParseResultDto() {
        List<ParseResultDto.LineError> errors = new ArrayList<>();
        errors.add(new ParseResultDto.LineError(1, "invalid line", "Parse error"));

        ParseResultDto dto = new ParseResultDto(10, 1, errors);

        assertEquals(10, dto.getTotalLinesProcessed());
        assertEquals(1, dto.getTotalLinesWithErrors());
        assertEquals(1, dto.getLineErrors().size());
    }

    @Test
    void shouldCreateEmptyParseResultDto() {
        ParseResultDto dto = new ParseResultDto();

        assertNotNull(dto);
        
        dto.setTotalLinesProcessed(5);
        dto.setTotalLinesWithErrors(2);
        dto.setLineErrors(new ArrayList<>());

        assertEquals(5, dto.getTotalLinesProcessed());
        assertEquals(2, dto.getTotalLinesWithErrors());
        assertEquals(0, dto.getLineErrors().size());
    }

    @Test
    void shouldCreateLineError() {
        ParseResultDto.LineError error = new ParseResultDto.LineError(5, "test line", "error message");

        assertEquals(5, error.getLineNumber());
        assertEquals("test line", error.getContent());
        assertEquals("error message", error.getErrorMessage());
    }
}