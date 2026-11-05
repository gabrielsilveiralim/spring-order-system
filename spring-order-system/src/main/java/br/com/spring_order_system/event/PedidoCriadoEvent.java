package br.com.spring_order_system.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PedidoCriadoEvent(
        UUID pedidoId,
        UUID clienteId,
        BigDecimal valorTotal,
        LocalDateTime criadoEm
) {
}