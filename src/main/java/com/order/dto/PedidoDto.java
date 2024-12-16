package com.order.dto;

import com.order.enuns.StatusPedido;
import com.order.model.Produto;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDto {
    private Long id;
    private StatusPedido status;
    private List<Produto> produtos;
    private BigDecimal precoTotal;
    private Date dataEntrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }

    public Date getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }
}
