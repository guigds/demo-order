package com.order.mapper;

import com.order.dto.PedidoRequestDto;
import com.order.model.Pedido;
import com.order.dto.PedidoDto;
import com.order.model.Produto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoMapper INSTANCE = Mappers.getMapper(PedidoMapper.class);

    @Mapping(source = "produtos", target = "produtos")
    @Mapping(target = "precoTotal", expression = "java(calcularPrecoTotal(pedido.getProdutos()))")
    PedidoDto pedidoToPedidoDto(Pedido pedido);

    @Mapping(source = "produtos", target = "produtos")
    Pedido pedidoRequestDtoToPedido(PedidoRequestDto pedidoDto);

    default BigDecimal calcularPrecoTotal(List<Produto> produtos) {
        return produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}