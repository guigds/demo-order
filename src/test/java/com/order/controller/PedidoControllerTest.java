package com.order.controller;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarPedidos() {
        // Arrange
        PedidoDto pedido1 = new PedidoDto();  // Configure o PedidoDto com dados de teste
        PedidoDto pedido2 = new PedidoDto();
        List<PedidoDto> pedidoList = Arrays.asList(pedido1, pedido2);
        when(pedidoService.findAll()).thenReturn(pedidoList);

        // Act
        ResponseEntity<List<PedidoDto>> response = pedidoController.listarPedidos();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoList, response.getBody());
    }

    @Test
    void testCriarPedido() {
        // Arrange
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();  // Configure o PedidoRequestDto com dados de teste
        doNothing().when(pedidoService).criarPedido(pedidoRequestDto);

        // Act
        ResponseEntity<String> response = pedidoController.criarPedido(pedidoRequestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Pedido criado com sucesso", response.getBody());
    }
}