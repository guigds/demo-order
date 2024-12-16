package com.order.service;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.enuns.StatusPedido;
import com.order.mapper.PedidoMapper;
import com.order.model.Pedido;
import com.order.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final PedidoMapper pedidoMapper;

    public void criarPedido(PedidoRequestDto pedidoDto) {
        Pedido pedido = pedidoMapper.pedidoRequestDtoToPedido(pedidoDto);
        produtoService.validaProdutosExistentes(pedido);
        pedido.setDataEntrada(LocalDate.now());
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedidoRepository.save(pedido);
    }

    public List<PedidoDto> findAll() {
        return pedidoRepository.findAll()
                .stream()
                .map(PedidoMapper.INSTANCE::pedidoToPedidoDto)
                .collect(Collectors.toList());
    }

}
