package br.com.fiap.desperdiciozero.exception;

/**
 * Regra do dominio violada (meta duplicada, local com registros...): o handler devolve 409.
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
