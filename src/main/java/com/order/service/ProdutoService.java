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
        log.info("Criando novo produto: {}", produtoDto);
        Produto produto = produtoMapper.produtoDtoToProduto(produtoDto);
        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.produtoToProdutoDto(produtoSalvo);
    }

    public List<ProdutoDto> listarProdutos() {
        log.info("Listando todos os produtos");
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::produtoToProdutoDto)
                .collect(Collectors.toList());
    }

    public void validaProdutosExistentes(Pedido pedido) {
        log.info("Validando produtos do pedido");
        pedido.getProdutos()
                .stream()
                .map(Produto::getId)
                .forEach(id -> produtoRepository.findById(id)
                        .orElseThrow(() -> {
                            log.error("Produto não encontrado para o ID: {}", id);
                            return new ProdutoNotFoundException("Produto não encontrado para o ID: " + id);
                        }));
    }

    public ProdutoDto atualizarProduto(ProdutoDto produtoDto) {
        log.info("Atualizando produto: {}", produtoDto);

        // Verifica se o produto existe
        Produto produtoExistente = produtoRepository.findById(produtoDto.getId())
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        // Atualiza os campos do produto
        Produto produtoAtualizado = produtoMapper.produtoDtoToProduto(produtoDto);
        Produto salvo = produtoRepository.save(produtoAtualizado);

        return produtoMapper.produtoToProdutoDto(salvo);
    }

    public void removerProduto(Long id) {
        log.info("Removendo produto com ID: {}", id);

        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }

}
