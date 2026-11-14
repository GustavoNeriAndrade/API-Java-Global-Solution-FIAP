package br.com.fiap.desperdiciozero.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.fiap.desperdiciozero.model.Registro;

@Repository
public class RegistroDAO {

    private static final String COLUNAS = "cd_registro, cd_local, cd_recurso, cd_causa, qt_desperdicio, dt_registro, ds_observacao";

    private final DataSource dataSource;

    public RegistroDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Registro inserir(Registro registro) {
        String sql = "INSERT INTO tb_registro (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection()) {
            long id = Sequencias.proximoValor(con, "sq_tb_registro");
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, registro.getLocalId());
                ps.setLong(3, registro.getRecursoId());
                ps.setLong(4, registro.getCausaId());
                ps.setBigDecimal(5, registro.getQuantidade());
                ps.setDate(6, Date.valueOf(registro.getData()));
                ps.setString(7, registro.getObservacao());
                ps.executeUpdate();
            }
            registro.setId(id);
            return registro;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao inserir registro");
        }
    }

    public Optional<Registro> buscarPorId(Long id) {
        String sql = "SELECT " + COLUNAS + " FROM tb_registro WHERE cd_registro = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar registro");
        }
    }

    public List<Registro> listarPorLocal(Long localId) {
        String sql = "SELECT " + COLUNAS + " FROM tb_registro WHERE cd_local = ? ORDER BY dt_registro DESC, cd_registro DESC";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, localId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Registro> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar registros");
        }
    }

    public boolean existePorLocal(Long localId) {
        String sql = "SELECT COUNT(*) FROM tb_registro WHERE cd_local = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, localId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getLong(1) > 0;
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao verificar registros do local");
        }
    }

    public boolean atualizar(Registro registro) {
        String sql = "UPDATE tb_registro SET cd_local = ?, cd_recurso = ?, cd_causa = ?, qt_desperdicio = ?, dt_registro = ?, ds_observacao = ? WHERE cd_registro = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, registro.getLocalId());
            ps.setLong(2, registro.getRecursoId());
            ps.setLong(3, registro.getCausaId());
            ps.setBigDecimal(4, registro.getQuantidade());
            ps.setDate(5, Date.valueOf(registro.getData()));
            ps.setString(6, registro.getObservacao());
            ps.setLong(7, registro.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao atualizar registro");
        }
    }

    public boolean remover(Long id) {
        String sql = "DELETE FROM tb_registro WHERE cd_registro = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao remover registro");
        }
    }

    private Registro mapear(ResultSet rs) throws SQLException {
        Registro registro = new Registro();
        registro.setId(rs.getLong("cd_registro"));
        registro.setLocalId(rs.getLong("cd_local"));
        registro.setRecursoId(rs.getLong("cd_recurso"));
        registro.setCausaId(rs.getLong("cd_causa"));
        registro.setQuantidade(rs.getBigDecimal("qt_desperdicio"));
        registro.setData(rs.getDate("dt_registro").toLocalDate());
        registro.setObservacao(rs.getString("ds_observacao"));
        return registro;
    }
}
