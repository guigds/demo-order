package com.order.service;

import com.order.dto.ProdutoDto;
import com.order.exception.ProdutoNotFoundException;
import com.order.mapper.ProdutoMapper;
import com.order.model.Pedido;
import com.order.model.Produto;
import com.order.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoDto criarProduto(ProdutoDto produtoDto) {
        Produto produto = produtoMapper.produtoDtoToProduto(produtoDto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.produtoToProdutoDto(produtoSalvo);
    }

    public List<ProdutoDto> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::produtoToProdutoDto)
                .collect(Collectors.toList());
    }

    public void validaProdutosExistentes(Pedido pedidoDto) {
        pedidoDto.getProdutos()
                .stream()
                .map(Produto::getId)
                .forEach(id -> produtoRepository.findById(id) // TODO BUSCAR PRODUTOS DOS REDIS....
                        .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado para o ID: " + id)));
    }

}
