package br.com.spring_order_system.dto;

import br.com.spring_order_system.database.model.StatusPedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponseDTO(
        UUID id,
        UUID clienteId,
        String nomeCliente,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal valorTotal,
        StatusPedido status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
}