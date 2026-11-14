package br.com.fiap.desperdiciozero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.fiap.desperdiciozero.model.Contato;

@Repository
public class ContatoDAO {

    private static final String COLUNAS = "cd_contato, nm_contato, ds_email, ds_assunto, ds_mensagem, dt_envio, ds_status";

    private final DataSource dataSource;

    public ContatoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insere o contato. dt_envio e ds_status usam os valores padrao do banco (SYSDATE e 'NOVO'),
     * por isso o registro e lido de volta ao final para devolver esses valores.
     */
    public Contato inserir(Contato contato) {
        String sqlInsert = "INSERT INTO tb_contato (cd_contato, nm_contato, ds_email, ds_assunto, ds_mensagem) VALUES (?, ?, ?, ?, ?)";
        String sqlBusca = "SELECT " + COLUNAS + " FROM tb_contato WHERE cd_contato = ?";
        try (Connection con = dataSource.getConnection()) {
            long id = Sequencias.proximoValor(con, "sq_tb_contato");
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setLong(1, id);
                ps.setString(2, contato.getNome());
                ps.setString(3, contato.getEmail());
                ps.setString(4, contato.getAssunto());
                ps.setString(5, contato.getMensagem());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlBusca)) {
                ps.setLong(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao inserir contato");
        }
    }

    private Contato mapear(ResultSet rs) throws SQLException {
        Contato contato = new Contato();
        contato.setId(rs.getLong("cd_contato"));
        contato.setNome(rs.getString("nm_contato"));
        contato.setEmail(rs.getString("ds_email"));
        contato.setAssunto(rs.getString("ds_assunto"));
        contato.setMensagem(rs.getString("ds_mensagem"));
        contato.setDataEnvio(rs.getDate("dt_envio").toLocalDate());
        contato.setStatus(rs.getString("ds_status"));
        return contato;
    }
}
