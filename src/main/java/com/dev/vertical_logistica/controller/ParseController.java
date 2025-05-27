package com.dev.vertical_logistica.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dev.vertical_logistica.dto.ParseResultDto;
import com.dev.vertical_logistica.service.ParseSystemLegacyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/parse")
@RequiredArgsConstructor
public class ParseController {

    private final ParseSystemLegacyService parseService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ParseResultDto result = parseService.processLegacyFile(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao processar arquivo: " + e.getMessage());
        }
    }
}
