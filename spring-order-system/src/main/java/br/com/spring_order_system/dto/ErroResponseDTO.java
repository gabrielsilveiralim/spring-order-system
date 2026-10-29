package br.com.spring_order_system.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErroResponseDTO(
        int status,
        String mensagem,
        List<String> detalhes,
        LocalDateTime timestamp
) {

    public ErroResponseDTO(int status, String mensagem, List<String> detalhes) {
        this(status, mensagem, detalhes, LocalDateTime.now());
    }
}