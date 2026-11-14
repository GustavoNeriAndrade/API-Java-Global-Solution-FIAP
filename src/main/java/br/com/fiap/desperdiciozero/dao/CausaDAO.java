package br.com.fiap.desperdiciozero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.fiap.desperdiciozero.model.Causa;

@Repository
public class CausaDAO {

    private static final String COLUNAS = "cd_causa, cd_recurso, nm_causa, ds_causa";

    private final DataSource dataSource;

    public CausaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Causa> listar() {
        String sql = "SELECT " + COLUNAS + " FROM tb_causa ORDER BY cd_causa";
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            List<Causa> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar causas");
        }
    }

    public List<Causa> listarPorRecurso(Long recursoId) {
        String sql = "SELECT " + COLUNAS + " FROM tb_causa WHERE cd_recurso = ? ORDER BY cd_causa";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, recursoId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Causa> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar causas do recurso");
        }
    }

    public Optional<Causa> buscarPorId(Long id) {
        String sql = "SELECT " + COLUNAS + " FROM tb_causa WHERE cd_causa = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar causa");
        }
    }

    private Causa mapear(ResultSet rs) throws SQLException {
        Causa causa = new Causa();
        causa.setId(rs.getLong("cd_causa"));
        causa.setRecursoId(rs.getLong("cd_recurso"));
        causa.setNome(rs.getString("nm_causa"));
        causa.setDescricao(rs.getString("ds_causa"));
        return causa;
    }
}
