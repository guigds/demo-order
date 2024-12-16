package com.order.service;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.dto.ProdutoRequestDto;
import com.order.enuns.StatusPedido;
import com.order.exception.ProdutoDuplicadoException;
import com.order.mapper.PedidoMapper;
import com.order.model.Pedido;
import com.order.repository.PedidoRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final PedidoMapper pedidoMapper;

    public void criarPedido(@Valid PedidoRequestDto pedidoDto) {
        log.info("Criando pedido: {}", pedidoDto);

        // Valida produtos duplicados antes de processar o pedido
        validarProdutosDuplicados(pedidoDto);

        Pedido pedido = pedidoMapper.pedidoRequestDtoToPedido(pedidoDto);
        produtoService.validaProdutosExistentes(pedido);

        pedido.setDataEntrada(LocalDate.now());
        pedido.setStatus(StatusPedido.RECEBIDO);

        pedidoRepository.save(pedido);

        log.info("Pedido criado com sucesso");
    }

    public List<PedidoDto> findAll() {
        log.debug("Buscando todos os pedidos");
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper.INSTANCE::pedidoToPedidoDto)
                .collect(Collectors.toList());
    }

    private void validarProdutosDuplicados(PedidoRequestDto pedidoDto) {
        // Obtém a lista de produtos do pedido
        List<Long> produtosIds = pedidoDto.getProdutos()
                .stream()
                .map(ProdutoRequestDto::getId)
                .collect(Collectors.toList());

        // Verifica se há produtos duplicados
        Set<Long> produtosUnicos = new HashSet<>(produtosIds);

        if (produtosUnicos.size() < produtosIds.size()) {
            log.error("Produtos duplicados encontrados no pedido");
            throw new ProdutoDuplicadoException("Não são permitidos produtos duplicados no mesmo pedido");
        }
    }

}
