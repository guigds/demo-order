package com.order.exception;

public class ProdutoDuplicadoException extends RuntimeException {
    public ProdutoDuplicadoException(String mensagem) {
        super(mensagem);
    }
}