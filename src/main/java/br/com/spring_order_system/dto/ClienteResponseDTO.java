package br.com.spring_order_system.dto;

import java.util.UUID;

public record ClienteResponseDTO(
        UUID id,
        String nome,
        String email,
        String cpf
) {
}