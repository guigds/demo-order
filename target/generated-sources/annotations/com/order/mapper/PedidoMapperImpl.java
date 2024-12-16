package com.order.mapper;

import com.order.dto.PedidoDto;
import com.order.dto.PedidoRequestDto;
import com.order.dto.ProdutoRequestDto;
import com.order.model.Pedido;
import com.order.model.Produto;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-16T10:27:00-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public PedidoDto pedidoToPedidoDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        PedidoDto pedidoDto = new PedidoDto();

        List<Produto> list = pedido.getProdutos();
        if ( list != null ) {
            pedidoDto.setProdutos( new ArrayList<Produto>( list ) );
        }
        pedidoDto.setId( pedido.getId() );
        pedidoDto.setStatus( pedido.getStatus() );
        if ( pedido.getDataEntrada() != null ) {
            pedidoDto.setDataEntrada( Date.from( pedido.getDataEntrada().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }

        pedidoDto.setPrecoTotal( calcularPrecoTotal(pedido.getProdutos()) );

        return pedidoDto;
    }

    @Override
    public Pedido pedidoRequestDtoToPedido(PedidoRequestDto pedidoDto) {
        if ( pedidoDto == null ) {
            return null;
        }

        Pedido pedido = new Pedido();

        pedido.setProdutos( produtoRequestDtoListToProdutoList( pedidoDto.getProdutos() ) );

        return pedido;
    }

    protected Produto produtoRequestDtoToProduto(ProdutoRequestDto produtoRequestDto) {
        if ( produtoRequestDto == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setId( produtoRequestDto.getId() );

        return produto;
    }

    protected List<Produto> produtoRequestDtoListToProdutoList(List<ProdutoRequestDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Produto> list1 = new ArrayList<Produto>( list.size() );
        for ( ProdutoRequestDto produtoRequestDto : list ) {
            list1.add( produtoRequestDtoToProduto( produtoRequestDto ) );
        }

        return list1;
    }
}
