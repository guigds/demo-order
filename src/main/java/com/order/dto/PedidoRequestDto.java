package com.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDto {

    @Valid
    @NotEmpty(message = "A lista de produtos não pode estar vazia")
    private List<ProdutoRequestDto> produtos;

    public List<ProdutoRequestDto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoRequestDto> produtos) {
        this.produtos = produtos;
    }
}
