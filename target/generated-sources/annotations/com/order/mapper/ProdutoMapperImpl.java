package com.order.mapper;

import com.order.dto.ProdutoDto;
import com.order.dto.ProdutoRequestDto;
import com.order.model.Produto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T10:27:00-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class ProdutoMapperImpl implements ProdutoMapper {

    @Override
    public ProdutoDto produtoToProdutoDto(Produto produto) {
        if ( produto == null ) {
            return null;
        }

        ProdutoDto produtoDto = new ProdutoDto();

        produtoDto.setId( produto.getId() );
        produtoDto.setNome( produto.getNome() );
        produtoDto.setDescricao( produto.getDescricao() );
        produtoDto.setPreco( produto.getPreco() );

        return produtoDto;
    }

    @Override
    public Produto produtoDtoToProduto(ProdutoDto produtoDto) {
        if ( produtoDto == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setId( produtoDto.getId() );
        produto.setNome( produtoDto.getNome() );
        produto.setDescricao( produtoDto.getDescricao() );
        produto.setPreco( produtoDto.getPreco() );

        return produto;
    }

    @Override
    public Produto produtoDtoToProduto(ProdutoRequestDto produtoDto) {
        if ( produtoDto == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setId( produtoDto.getId() );

        return produto;
    }
}
