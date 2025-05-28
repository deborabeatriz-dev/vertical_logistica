package com.dev.vertical_logistica.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.dev.vertical_logistica.dto.ParseResultDto;
import com.dev.vertical_logistica.service.ParseSystemLegacyService;

public class ParseControllerTest {
    
    private ParseSystemLegacyService parseService;
    private ParseController parseController;

    @BeforeEach
    void setup() {
        parseService = mock(ParseSystemLegacyService.class);
        parseController = new ParseController(parseService);
    }

    @Test
    void testUploadFile_success() throws Exception {
        byte[] content = "fake content".getBytes();
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);

        ParseResultDto mockResult = new ParseResultDto();
        mockResult.setTotalLinesProcessed(1);
        mockResult.setTotalLinesWithErrors(0);

        when(parseService.processLegacyFile(file)).thenReturn(mockResult);

        ResponseEntity<?> response = parseController.uploadFile(file);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof ParseResultDto);

        ParseResultDto resultDto = (ParseResultDto) response.getBody();
        assertEquals(1, resultDto.getTotalLinesProcessed());
        assertEquals(0, resultDto.getTotalLinesWithErrors());
    }

    @Test
    void testUploadFile_failure() throws Exception {
        byte[] content = "fake content".getBytes();
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content);

        when(parseService.processLegacyFile(file)).thenThrow(new RuntimeException("Falha no processamento"));

        ResponseEntity<?> response = parseController.uploadFile(file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Falha no processamento"));
    }
}
