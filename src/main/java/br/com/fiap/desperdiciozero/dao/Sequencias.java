package br.com.fiap.desperdiciozero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Busca o proximo valor das sequences sq_tb_* (padrao da disciplina).
 * O nome da sequence vem sempre de uma constante dos DAOs, nunca de dado do usuario.
 */
final class Sequencias {

    private Sequencias() {
    }

    static long proximoValor(Connection con, String sequencia) throws SQLException {
        String sql = "SELECT " + sequencia + ".NEXTVAL FROM dual";
        try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getLong(1);
        }
    }
}
