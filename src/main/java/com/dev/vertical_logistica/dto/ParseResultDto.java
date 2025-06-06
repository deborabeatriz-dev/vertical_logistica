package com.dev.vertical_logistica.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParseResultDto {
    private int totalLinesProcessed;
    private int totalLinesWithErrors;
    private List<LineError> lineErrors;

    @Data
    @AllArgsConstructor
    public static class LineError {
        private int lineNumber;
        private String content;
        private String errorMessage;
    }
}
