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

import static org.junit.jupiter.api.Assertions.*;
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
    public void criarPedidoDeveSalvarPedidoQuandoSucesso() {
        // Suponha que estas são as implementações corretas
        PedidoRequestDto pedidoRequestDto = mock(PedidoRequestDto.class);

        when(pedidoMapper.pedidoRequestDtoToPedido(pedidoRequestDto)).thenReturn(pedido);

        // Simula o comportamento de validação sem erros
        doNothing().when(produtoService).validaProdutosExistentes(pedido);

        // Executa o método
        pedidoService.criarPedido(pedidoRequestDto);

        // Verifica se o método de salvar foi chamado
        verify(pedidoRepository, times(1)).save(pedido);
        // Verifica se o status e a data de entrada foram corretamente configurados
        assertEquals(StatusPedido.RECEBIDO, pedido.getStatus());
        assertNotNull(pedido.getDataEntrada());
    }

    @Test
    public void criarPedidoDeveLancarExcecaoQuandoValidacaoFalha() {
        PedidoRequestDto pedidoRequestDto = mock(PedidoRequestDto.class);

        when(pedidoMapper.pedidoRequestDtoToPedido(pedidoRequestDto)).thenReturn(pedido);

        // Simula uma falha na validação dos produtos
        doThrow(new IllegalArgumentException("Produto não encontrado")).when(produtoService).validaProdutosExistentes(pedido);

        // Executa o método esperando que uma exceção seja lançada
        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.criarPedido(pedidoRequestDto);
        });

        // Verifica que o método save nunca é chamado devido à falha na validação
        verify(pedidoRepository, never()).save(pedido);
    }

    @Test
    public void findAllDeveRetornarListaDePedidosQuandoExistemPedidos() {
        // Cria mocks de pedidos do banco
        PedidoDto pedidoDto = new PedidoDto();

        // Define o comportamento dos mocks
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        // Chama o método findAll
        List<PedidoDto> result = pedidoService.findAll();

        // Verifica se os resultados estão corretos
        assertEquals(1, result.size());
    }

    @Test
    public void findAllDeveRetornarListaVaziaQuandoNaoExistemPedidos() {
        // Define o retorno como lista vazia
        when(pedidoRepository.findAll()).thenReturn(Collections.emptyList());

        // Chama o método findAll
        List<PedidoDto> result = pedidoService.findAll();

        // Verifica se o resultado é uma lista vazia
        assertTrue(result.isEmpty());
    }

    // Teste adicional para capturar comportamentos inesperados,
    // embora findAll do repository geralmente não lance exceções
    @Test
    public void findAllDeveLancarExcecaoSeFindAllDoRepositorioFalhar() {
        when(pedidoRepository.findAll()).thenThrow(new RuntimeException("Erro no repositório"));

        assertThrows(RuntimeException.class, () -> {
            pedidoService.findAll();
        });

        // Opcionalmente, verificar logs ou outras ações decorrentes da exceção
    }
}