package br.com.fiap.desperdiciozero.exception;

/**
 * Dados inconsistentes que a Bean Validation nao cobre: o handler devolve 400.
 */
public class RequisicaoInvalidaException extends RuntimeException {

    public RequisicaoInvalidaException(String mensagem) {
        super(mensagem);
    }
}
