package com.dev.vertical_logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.dev.vertical_logistica.dto.ParseResultDto;

@ExtendWith(MockitoExtension.class)
public class ParseSystemLegacyServiceTest {
    
    @Mock
    private UserService userService;

    @InjectMocks
    private ParseSystemLegacyService parseSystemLegacyService;

    @Test
    void shouldProcessValidFile() throws Exception {
        String content = "0000000070                              Dev Teste00000000000007530000000003     1836.7420210308\n";
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", content.getBytes());

        Mockito.doNothing().when(userService).processUserOrderProduct(
            anyLong(), anyString(), anyLong(), any(), anyLong(), any()
        );

        ParseResultDto result = parseSystemLegacyService.processLegacyFile(file);

        assertEquals(1, result.getTotalLinesProcessed());
        assertEquals(0, result.getTotalLinesWithErrors());
    }

    @Test
    void shouldCaptureErrorOnInvalidLine() throws Exception {
        String content = "invalid line\n";
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", content.getBytes());

        ParseResultDto result = parseSystemLegacyService.processLegacyFile(file);

        assertEquals(1, result.getTotalLinesProcessed());
        assertEquals(1, result.getTotalLinesWithErrors());
    }

    @Test
    void shouldSkipEmptyLines() throws Exception {
        String content = "\n";
        MultipartFile file = new MockMultipartFile("file", "data.txt", "text/plain", content.getBytes());

        ParseResultDto result = parseSystemLegacyService.processLegacyFile(file);

        assertEquals(1, result.getTotalLinesProcessed());
        assertEquals(0, result.getTotalLinesWithErrors());
    }

    @Test
    void shouldThrowExceptionWhenFileProcessingFails() throws Exception {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        Mockito.when(file.getOriginalFilename()).thenReturn("file.txt");
        Mockito.when(file.getInputStream()).thenThrow(new RuntimeException("Simulated read error"));

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
            parseSystemLegacyService.processLegacyFile(file);
        });

        assertEquals("Falha no processamento do arquivo", exception.getMessage());
    }

}
