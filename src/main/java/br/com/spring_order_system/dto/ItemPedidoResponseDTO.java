package br.com.spring_order_system.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponseDTO(
        UUID id,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {
}