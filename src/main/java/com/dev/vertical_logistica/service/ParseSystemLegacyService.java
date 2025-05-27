package com.dev.vertical_logistica.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParseSystemLegacyService {

    private final UserService userService;
    
    public void processLegacyFile(MultipartFile file) {
        log.info("Iniciando processamento do arquivo legado: {}", file.getOriginalFilename());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null ) {
                lineNumber++;
                if(line.trim().isEmpty()) {
                    log.debug("Linha {} vazia, pulando...", lineNumber);
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
                            lineNumber, userId, name, orderId, productId, price, date);

                    userService.processUserOrderProduct(userId, name, orderId, date, productId, price);
                } catch (Exception e) {
                    log.error("Erro ao processar linha {}: {}", lineNumber, line, e);
                    throw new RuntimeException("Falha ao processar a linha " + lineNumber + ". Verifique o formato do arquivo.", e);
                }
            }

            log.info("Processamento do arquivo legado finalizado. Total linhas processadas: {}", lineNumber);
        } catch (Exception e) {
            log.error("Erro ao processar o arquivo legado", e);
            throw new RuntimeException("Falha no processamento do arquivo", e);
        }
    }

}
