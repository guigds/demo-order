package com.order.mapper;

import com.order.dto.ProdutoDto;
import com.order.dto.ProdutoRequestDto;
import com.order.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

    ProdutoDto produtoToProdutoDto(Produto produto);

    Produto produtoDtoToProduto(ProdutoDto produtoDto);
    Produto produtoDtoToProduto(ProdutoRequestDto produtoDto);
}