package com.order.controller;

import com.order.dto.ProdutoDto;
import com.order.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoDto> criarProduto(@RequestBody ProdutoDto produtoDto) {
        ProdutoDto produtoCriado = produtoService.criarProduto(produtoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoCriado);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> listarProdutos() {
        List<ProdutoDto> produtos = produtoService.listarProdutos();
        return ResponseEntity.ok(produtos);
    }
}