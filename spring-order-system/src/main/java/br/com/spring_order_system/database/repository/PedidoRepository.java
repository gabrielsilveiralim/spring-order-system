package br.com.spring_order_system.database.repository;

import br.com.spring_order_system.database.model.Pedido;
import br.com.spring_order_system.database.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    List<Pedido> findByClienteId(UUID clienteId);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByClienteIdAndStatus(UUID clienteId, StatusPedido status);
}