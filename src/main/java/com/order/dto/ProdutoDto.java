package com.order.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDto {
    private Long id;
    @NotBlank(message = "O NOME do produto não pode ser vazio.")
    private String nome;
    @NotBlank(message = "O DESCRIÇÃO do produto não pode ser vazio.")
    private String descricao;
    @NotNull(message = "O PREÇO do produto não pode ser vazio.")
    private BigDecimal preco;
    @NotNull(message = "O QUANTIDADE do produto não pode ser vazio.")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
