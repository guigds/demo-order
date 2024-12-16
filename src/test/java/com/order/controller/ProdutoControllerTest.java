package com.order.controller;

import com.order.dto.ProdutoDto;
import com.order.exception.ProdutoDuplicadoException;
import com.order.exception.ProdutoNotFoundException;
import com.order.service.ProdutoService;
import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private ProdutoDto validProduto;
    private ProdutoDto produtoCriado;
    private ProdutoDto produtoInvalido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Arrange
        validProduto = ProdutoDto.builder()
                .id(1L)
                .nome("Produto Teste")
                .descricao("Uma descrição válida")
                .preco(BigDecimal.valueOf(100.0))
                .build();

        produtoCriado = ProdutoDto.builder()
                .id(1L)
                .nome("Produto Teste")
                .descricao("Uma descrição válida")
                .preco(BigDecimal.valueOf(100.0))
                .build();

        produtoInvalido = ProdutoDto.builder()
                .nome("")  // Nome inválido
                .preco(BigDecimal.ZERO)  // Preço inválido
                .build();
    }

    // Teste de Sucesso
    @Test
    @DisplayName("Deve criar produto com sucesso")
    void testCriarProduto_Sucesso() {

        // Mock do serviço
        when(produtoService.criarProduto(validProduto))
                .thenReturn(produtoCriado);

        // Act
        ResponseEntity<ProdutoDto> response = produtoController.criarProduto(validProduto);

        // Assert
        assertAll(
                () -> {
                    log.info("Resultado do teste: {}", response);
                    assertNotNull(response);
                },
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(produtoCriado, response.getBody()),
                () -> assertEquals(1L, response.getBody().getId())
        );

        // Verificação de chamada do serviço
        verify(produtoService, times(1)).criarProduto(validProduto);
    }

    // Teste de Erro - Produto Inválido
    @Test
    @DisplayName("Deve lançar exceção para produto inválido")
    void testCriarProduto_ProdutoInvalido() {

        // Mock para lançar exceção de validação
        when(produtoService.criarProduto(produtoInvalido))
                .thenThrow(new ProdutoNotFoundException("Produto inválido"));

        // Act & Assert
        ProdutoNotFoundException exception = assertThrows(
                ProdutoNotFoundException.class,
                () -> produtoController.criarProduto(produtoInvalido),
                "Deveria lançar ValidationException"
        );

        // Log da exceção
        log.error("Erro de validação: {}", exception.getMessage());

        // Verificação de chamada do serviço
        verify(produtoService, times(1)).criarProduto(produtoInvalido);
    }

    @Test
    @DisplayName("Deve lançar exceção para produto já existente")
    void testCriarProduto_ProdutoJaExistente() {

        // Mock para lançar exceção de produto já existente
        when(produtoService.criarProduto(validProduto))
                .thenThrow(new ProdutoDuplicadoException("Produto já cadastrado"));

        // Act & Assert
        ProdutoDuplicadoException exception = assertThrows(
                ProdutoDuplicadoException.class,
                () -> produtoController.criarProduto(validProduto),
                "Deveria lançar ProdutoExistenteException"
        );

        // Log da exceção
        log.warn("Produto já existente: {}", exception.getMessage());

        // Verificação de chamada do serviço
        verify(produtoService, times(1)).criarProduto(validProduto);
    }

    @Test
    void testListarProdutos() {

        List<ProdutoDto> produtoList = Arrays.asList(validProduto, produtoCriado);
        when(produtoService.listarProdutos()).thenReturn(produtoList);

        // Act
        ResponseEntity<List<ProdutoDto>> response = produtoController.listarProdutos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoList, response.getBody());
    }
}