package br.com.fiap.desperdiciozero.dao;

import java.sql.SQLException;

import br.com.fiap.desperdiciozero.exception.ErroDeAcessoADadosException;
import br.com.fiap.desperdiciozero.exception.RegraDeNegocioException;
import br.com.fiap.desperdiciozero.exception.RequisicaoInvalidaException;

/**
 * Converte erros do Oracle (ORA-xxxxx) em excecoes do dominio, para que o handler
 * devolva o status certo e uma mensagem clara, sem expor o SQL.
 */
final class TradutorErroSql {

    private TradutorErroSql() {
    }

    static RuntimeException traduzir(SQLException e, String contexto) {
        String texto = e.getMessage() == null ? "" : e.getMessage().toUpperCase();
        switch (e.getErrorCode()) {
            case 1: // ORA-00001: unique constraint violated
                if (texto.contains("UQ_META_LOCAL_REC")) {
                    return new RegraDeNegocioException("Este local já tem uma meta para esse recurso. Edite a meta existente.");
                }
                return new RegraDeNegocioException("Já existe um registro com esses dados.");
            case 2292: // ORA-02292: child record found
                if (texto.contains("FK_REGISTRO_LOCAL")) {
                    return new RegraDeNegocioException("Este local já tem registros e não pode ser excluído.");
                }
                return new RegraDeNegocioException("Este item possui dados vinculados e não pode ser excluído.");
            case 2291: // ORA-02291: parent key not found
                if (texto.contains("FK_REGISTRO_CAUSA")) {
                    return new RegraDeNegocioException("Essa causa não pertence ao recurso escolhido.");
                }
                return new RequisicaoInvalidaException("O local, o recurso ou a causa informado não existe.");
            case 2290: // ORA-02290: check constraint violated
                if (texto.contains("CK_CONTATO_EMAIL")) {
                    return new RequisicaoInvalidaException("O e-mail informado é inválido.");
                }
                if (texto.contains("CK_CONTATO_ASSUNTO")) {
                    return new RequisicaoInvalidaException("O assunto informado é inválido.");
                }
                return new RequisicaoInvalidaException("Os dados enviados violam uma regra do banco de dados.");
            case 1400: // ORA-01400: cannot insert NULL
                return new RequisicaoInvalidaException("Há campos obrigatórios sem preenchimento.");
            case 12899: // ORA-12899: value too large for column
                return new RequisicaoInvalidaException("Um dos campos excede o tamanho permitido.");
            default:
                return new ErroDeAcessoADadosException(contexto, e);
        }
    }
}
