package com.order.controller;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.dto.ProdutoRequestDto;
import com.order.exception.ProdutoDuplicadoException;
import com.order.exception.ProdutoNotFoundException;
import com.order.service.PedidoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // Método auxiliar para criar pedido válido
    private PedidoRequestDto criarPedidoValido() {
        return PedidoRequestDto.builder()
                .produtos(List.of(
                        ProdutoRequestDto.builder()
                                .id(1L)
                                .build()
                ))
                .build();
    }

    // Teste de Sucesso
    @Test
    @DisplayName("Deve criar pedido com sucesso")
    void testCriarPedido_Sucesso() {
        // Arrange
        PedidoRequestDto pedidoValido = criarPedidoValido();

        // Act
        ResponseEntity<String> response = pedidoController.criarPedido(pedidoValido);

        // Assert
        assertAll(
                () -> {
                    log.info("Resultado do teste: {}", response);
                    assertNotNull(response);
                },
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals("Pedido criado com sucesso", response.getBody())
        );

        // Verificação de chamada do serviço
        verify(pedidoService, times(1)).criarPedido(pedidoValido);
    }

    // Teste de Erro - Pedido Inválido
    @Test
    @DisplayName("Deve lançar exceção para pedido inválido")
    void testCriarPedido_PedidoInvalido() {
        // Arrange
        PedidoRequestDto pedidoInvalido = PedidoRequestDto.builder()
                .produtos(Collections.emptyList())  // Sem itens
                .build();

        // Mock para lançar exceção de validação
        doThrow(new ProdutoNotFoundException("Pedido inválido"))
                .when(pedidoService)
                .criarPedido(pedidoInvalido);

        // Act & Assert
        ProdutoNotFoundException exception = assertThrows(
                ProdutoNotFoundException.class,
                () -> pedidoController.criarPedido(pedidoInvalido),
                "Deveria lançar ValidationException"
        );

        // Log da exceção
        log.error("Erro de validação: {}", exception.getMessage());

        // Verificação de chamada do serviço
        verify(pedidoService, times(1)).criarPedido(pedidoInvalido);
    }

    // Teste de Erro - Produtos Duplicados
    @Test
    @DisplayName("Deve lançar exceção para produtos duplicados")
    void testCriarPedido_ProdutosDuplicados() {
        // Arrange
        PedidoRequestDto pedidoComProdutosDuplicados = PedidoRequestDto.builder()
                .produtos(List.of(
                        ProdutoRequestDto.builder()
                                .id(1L)
                                .build(),
                        ProdutoRequestDto.builder()
                                .id(1L)
                                .build()
                ))
                .build();

        // Mock para lançar exceção de produtos duplicados
        doThrow(new ProdutoDuplicadoException("Produtos duplicados no pedido"))
                .when(pedidoService)
                .criarPedido(pedidoComProdutosDuplicados);

        // Act & Assert
        ProdutoDuplicadoException exception = assertThrows(
                ProdutoDuplicadoException.class,
                () -> pedidoController.criarPedido(pedidoComProdutosDuplicados),
                "Deveria lançar ProdutoDuplicadoException"
        );

        // Log da exceção
        log.warn("Erro de produtos duplicados: {}", exception.getMessage());

        // Verificação de chamada do serviço
        verify(pedidoService, times(1)).criarPedido(pedidoComProdutosDuplicados);
    }

    // Teste de Erro - Produto Inexistente
    @Test
    @DisplayName("Deve lançar exceção para produto inexistente")
    void testCriarPedido_ProdutoInexistente() {
        // Arrange
        PedidoRequestDto pedidoComProdutoInexistente = PedidoRequestDto.builder()
                .produtos(List.of(
                        ProdutoRequestDto.builder()
                                .id(999L)  // ID de produto inexistente
                                .build()
                ))
                .build();

        // Mock para lançar exceção de produto inexistente
        doThrow(new ProdutoNotFoundException("Produto não encontrado"))
                .when(pedidoService)
                .criarPedido(pedidoComProdutoInexistente);

        // Act & Assert
        ProdutoNotFoundException exception = assertThrows(
                ProdutoNotFoundException.class,
                () -> pedidoController.criarPedido(pedidoComProdutoInexistente),
                "Deveria lançar ProdutoInexistenteException"
        );

        // Log da exceção
        log.error("Erro de produto inexistente: {}", exception.getMessage());

        // Verificação de chamada do serviço
        verify(pedidoService, times(1)).criarPedido(pedidoComProdutoInexistente);
    }
}