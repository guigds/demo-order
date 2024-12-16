package com.order.service;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.dto.ProdutoDto;
import com.order.enuns.StatusPedido;
import com.order.mapper.PedidoMapper;
import com.order.model.Pedido;
import com.order.model.Produto;
import com.order.repository.PedidoRepository;
import com.order.service.PedidoService;
import com.order.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoService pedidoService;

    private PedidoRequestDto pedidoRequestDto;
    private Pedido pedido;

    @BeforeEach
    public void setup() {
        pedidoRequestDto = new PedidoRequestDto();
        pedido = new Pedido();
        pedido.setDataEntrada(LocalDate.now());
        pedido.setStatus(StatusPedido.RECEBIDO);
        pedido.setProdutos(Collections.singletonList(Produto.builder()
                .id(1L)
                        .descricao("descricao")
                        .nome("nome")
                        .preco(BigDecimal.ZERO)
                .build()));

    }

    @Test
    public void testCriarPedido() {
        when(pedidoMapper.pedidoRequestDtoToPedido(pedidoRequestDto)).thenReturn(pedido);
        pedidoService.criarPedido(pedidoRequestDto);

        verify(produtoService, times(1)).validaProdutosExistentes(pedido);
        verify(pedidoRepository, times(1)).save(pedido);
        assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
        assertEquals(LocalDate.now(), pedido.getDataEntrada());
    }

    @Test
    public void testFindAll() {
        List<Pedido> pedidos = Collections.singletonList(pedido);
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        List<PedidoDto> result = pedidoService.findAll();
        assertEquals(1, result.size());
        verify(pedidoRepository, times(1)).findAll();
    }
}