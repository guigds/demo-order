package com.order.service;

import com.order.dto.ProdutoDto;
import com.order.exception.ProdutoNotFoundException;
import com.order.mapper.ProdutoMapper;
import com.order.model.Pedido;
import com.order.model.Produto;
import com.order.repository.ProdutoRepository;
import com.order.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutoMapper produtoMapper;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoDto produtoDto;
    private Produto produto;

    @BeforeEach
    public void setup() {
        produtoDto = new ProdutoDto();
        produto = new Produto();

    }

    @Test
    public void testCriarProduto() {
        when(produtoMapper.produtoDtoToProduto(produtoDto)).thenReturn(produto);
        when(produtoMapper.produtoToProdutoDto(produto)).thenReturn(produtoDto);

        when(produtoRepository.save(produto)).thenReturn(produto);

        ProdutoDto resultado = produtoService.criarProduto(produtoDto);

        verify(produtoMapper).produtoDtoToProduto(produtoDto);
        verify(produtoRepository).save(produto);
        verify(produtoMapper).produtoToProdutoDto(produto);

        assertEquals(produtoDto, resultado);
    }

    @Test
    public void testCriarProdutoComErro() {
        when(produtoMapper.produtoDtoToProduto(produtoDto)).thenReturn(produto);

        doThrow(new RuntimeException("Erro ao salvar o produto")).when(produtoRepository).save(produto);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            produtoService.criarProduto(produtoDto);
        });

        assertEquals("Erro ao salvar o produto", exception.getMessage());

        verify(produtoMapper).produtoDtoToProduto(produtoDto);
        verify(produtoRepository).save(produto);
        verify(produtoMapper, never()).produtoToProdutoDto(produto);
    }

    @Test
    public void testListarProdutos() {
        List<Produto> produtos = Collections.singletonList(produto);
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<ProdutoDto> produtoDtos = produtoService.listarProdutos();

        assertEquals(1, produtoDtos.size());
        verify(produtoRepository).findAll();
    }

    @Test
    public void testValidaProdutosExistentes() {
        Pedido pedido = new Pedido();
        pedido.setProdutos(Collections.singletonList(produto));
        
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.of(produto));

        produtoService.validaProdutosExistentes(pedido);

        verify(produtoRepository).findById(produto.getId());
    }

    @Test
    public void testValidaProdutosExistentes_ProdutoNotFoundException() {
        Pedido pedido = new Pedido();
        pedido.setProdutos(Collections.singletonList(produto));
        
        when(produtoRepository.findById(produto.getId())).thenReturn(Optional.empty());

        assertThrows(ProdutoNotFoundException.class, () -> produtoService.validaProdutosExistentes(pedido));
    }
}