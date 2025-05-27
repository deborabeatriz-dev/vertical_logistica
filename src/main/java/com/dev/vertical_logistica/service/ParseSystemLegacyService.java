package com.dev.vertical_logistica.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dev.vertical_logistica.dto.ParseResultDto;
import com.dev.vertical_logistica.dto.ParseResultDto.LineError;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParseSystemLegacyService {

    private final UserService userService;
    
    public ParseResultDto processLegacyFile(MultipartFile file) {
        log.info("Iniciando processamento do arquivo legado: {}", file.getOriginalFilename());

        int totalLines = 0;
        List<LineError> errors = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null ) {
                totalLines++;
                if(line.trim().isEmpty()) {
                    log.debug("Linha {} vazia, pulando...", totalLines);
                    continue;
                }

                try {
                    Long userId = Long.parseLong(line.substring(0, 10));
                    String name = line.substring(10, 55).trim();
                    Long orderId = Long.parseLong(line.substring(55, 65));
                    Long productId = Long.parseLong(line.substring(65, 75));
                    BigDecimal price = new BigDecimal(line.substring(75, 87).trim());
                    LocalDate date = LocalDate.parse(line.substring(87, 95), DateTimeFormatter.ofPattern("yyyyMMdd"));

                    log.info("Linha {} processada: userId={}, userName={}, orderId={}, productId={}, price={}, date={}",
                            totalLines, userId, name, orderId, productId, price, date);

                    userService.processUserOrderProduct(userId, name, orderId, date, productId, price);
                } catch (Exception e) {
                    log.error("Erro ao processar linha {}: {}", totalLines, line, e);
                    errors.add(new LineError(totalLines, line, e.getMessage()));
                }
            }

            log.info("Processamento do arquivo legado finalizado. Total linhas processadas: {}", totalLines);
        } catch (Exception e) {
            log.error("Erro ao processar o arquivo legado", e);
            throw new RuntimeException("Falha no processamento do arquivo", e);
        }

        return new ParseResultDto(totalLines, errors.size(), errors);
    }

}
