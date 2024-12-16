package com.order.enuns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum StatusPedido {
    RECEBIDO(1, "Recebido"),
    PROCESSANDO(2, "Processando"),
    PAGO(3, "Pago"),
    EM_SEPARACAO(4, "Em Separação"),
    DESPACHADO(5, "Despachado"),
    EM_TRANSITO(6, "Em Trânsito"),
    ENTREGUE(7, "Entregue"),
    CANCELADO(8, "Cancelado"),
    DEVOLVIDO(9, "Devolvido"),
    AGUARDANDO_PAGAMENTO(10, "Aguardando Pagamento"),
    AGUARDANDO_RETIRADA(11, "Aguardando Retirada");

    private final int codigo;
    private final String descricao;
}