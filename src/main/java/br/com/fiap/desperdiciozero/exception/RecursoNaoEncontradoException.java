package br.com.fiap.desperdiciozero.exception;

/**
 * Id inexistente: o handler devolve 404.
 */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
