package br.com.spring_order_system.service;

import br.com.spring_order_system.database.model.Cliente;
import br.com.spring_order_system.database.model.ItemPedido;
import br.com.spring_order_system.database.model.Pedido;
import br.com.spring_order_system.database.repository.ClienteRepository;
import br.com.spring_order_system.database.repository.PedidoRepository;
import br.com.spring_order_system.dto.ItemPedidoRequestDTO;
import br.com.spring_order_system.dto.ItemPedidoResponseDTO;
import br.com.spring_order_system.dto.PedidoRequestDTO;
import br.com.spring_order_system.dto.PedidoResponseDTO;
import br.com.spring_order_system.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(requestDTO.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente não encontrado: " + requestDTO.clienteId()));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        for (ItemPedidoRequestDTO itemDTO : requestDTO.itens()) {
            ItemPedido item = new ItemPedido();
            item.setNomeProduto(itemDTO.nomeProduto());
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoUnitario(itemDTO.precoUnitario());
            pedido.adicionarItem(item);
        }

        pedido.setValorTotal(calcularValorTotal(pedido.getItens()));

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        // TODO: publicar evento PedidoCriado na outbox para processamento assíncrono

        return paraResponseDTO(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pedido não encontrado: " + id));
        return paraResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarTodos() {
        return pedidoRepository.findAll().stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    private BigDecimal calcularValorTotal(List<ItemPedido> itens) {
        return itens.stream()
                .map(ItemPedido::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private PedidoResponseDTO paraResponseDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itensDTO = pedido.getItens().stream()
                .map(item -> new ItemPedidoResponseDTO(
                        item.getId(),
                        item.getNomeProduto(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubtotal()
                ))
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                itensDTO,
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getDataCriacao(),
                pedido.getDataAtualizacao()
        );
    }
}