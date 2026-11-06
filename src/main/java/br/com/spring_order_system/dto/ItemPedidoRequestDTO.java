package br.com.spring_order_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ItemPedidoRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        String nomeProduto,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @NotNull(message = "O preço unitário é obrigatório")
        @Positive(message = "O preço unitário deve ser maior que zero")
        BigDecimal precoUnitario
) {
}