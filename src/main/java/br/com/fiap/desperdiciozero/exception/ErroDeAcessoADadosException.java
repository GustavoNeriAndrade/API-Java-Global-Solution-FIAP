package br.com.fiap.desperdiciozero.exception;

/**
 * Falha inesperada no banco: o handler devolve 500 com mensagem generica (o detalhe vai so para o log).
 */
public class ErroDeAcessoADadosException extends RuntimeException {

    public ErroDeAcessoADadosException(String mensagem) {
        super(mensagem);
    }

    public ErroDeAcessoADadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
