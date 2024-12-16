package com.order.controller;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDto>> listarPedidos() {
        List<PedidoDto> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping()
    public ResponseEntity<String> criarPedido(@Valid @RequestBody PedidoRequestDto pedidoDto) {
        pedidoService.criarPedido(pedidoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Pedido criado com sucesso");
    }
}