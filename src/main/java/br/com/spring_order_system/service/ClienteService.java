package br.com.spring_order_system.service;

import br.com.spring_order_system.database.model.Cliente;
import br.com.spring_order_system.database.repository.ClienteRepository;
import br.com.spring_order_system.dto.ClienteRequestDTO;
import br.com.spring_order_system.dto.ClienteResponseDTO;
import br.com.spring_order_system.exception.ConflitoException;
import br.com.spring_order_system.exception.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteResponseDTO criar(ClienteRequestDTO requestDTO) {
        if (clienteRepository.existsByEmail(requestDTO.email())) {
            throw new ConflitoException("Já existe um cliente com o e-mail: " + requestDTO.email());
        }
        if (clienteRepository.existsByCpf(requestDTO.cpf())) {
            throw new ConflitoException("Já existe um cliente com o CPF informado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(requestDTO.nome());
        cliente.setEmail(requestDTO.email());
        cliente.setCpf(requestDTO.cpf());

        return paraResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado: " + id));
        return paraResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::paraResponseDTO)
                .toList();
    }

    private ClienteResponseDTO paraResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getCpf()
        );
    }
}