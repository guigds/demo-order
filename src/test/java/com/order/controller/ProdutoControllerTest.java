package com.order.controller;

import com.order.dto.ProdutoDto;
import com.order.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProdutoControllerTest {

    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoService produtoService;

    private ProdutoDto validProduto;
    private ProdutoDto produtoCriado;

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
    }

    @Test
    void testCriarProduto() {
        when(produtoService.criarProduto(validProduto)).thenReturn(produtoCriado);

        // Act
        ResponseEntity<ProdutoDto> response = produtoController.criarProduto(validProduto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoCriado, response.getBody());
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