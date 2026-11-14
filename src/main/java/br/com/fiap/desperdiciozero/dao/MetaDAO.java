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

import br.com.fiap.desperdiciozero.model.Meta;

@Repository
public class MetaDAO {

    private static final String COLUNAS = "cd_meta, cd_local, cd_recurso, qt_meta_mensal, dt_inicio";

    private final DataSource dataSource;

    public MetaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Meta inserir(Meta meta) {
        String sql = "INSERT INTO tb_meta (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection()) {
            long id = Sequencias.proximoValor(con, "sq_tb_meta");
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setLong(2, meta.getLocalId());
                ps.setLong(3, meta.getRecursoId());
                ps.setBigDecimal(4, meta.getMetaMensal());
                ps.setDate(5, Date.valueOf(meta.getDataInicio()));
                ps.executeUpdate();
            }
            meta.setId(id);
            return meta;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao inserir meta");
        }
    }

    public Optional<Meta> buscarPorId(Long id) {
        String sql = "SELECT " + COLUNAS + " FROM tb_meta WHERE cd_meta = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar meta");
        }
    }

    public Optional<Meta> buscarPorLocalERecurso(Long localId, Long recursoId) {
        String sql = "SELECT " + COLUNAS + " FROM tb_meta WHERE cd_local = ? AND cd_recurso = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, localId);
            ps.setLong(2, recursoId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar meta do local");
        }
    }

    public List<Meta> listarPorLocal(Long localId) {
        String sql = "SELECT " + COLUNAS + " FROM tb_meta WHERE cd_local = ? ORDER BY cd_meta";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, localId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Meta> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar metas");
        }
    }

    public boolean atualizar(Meta meta) {
        String sql = "UPDATE tb_meta SET cd_local = ?, cd_recurso = ?, qt_meta_mensal = ?, dt_inicio = ? WHERE cd_meta = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, meta.getLocalId());
            ps.setLong(2, meta.getRecursoId());
            ps.setBigDecimal(3, meta.getMetaMensal());
            ps.setDate(4, Date.valueOf(meta.getDataInicio()));
            ps.setLong(5, meta.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao atualizar meta");
        }
    }

    public boolean remover(Long id) {
        String sql = "DELETE FROM tb_meta WHERE cd_meta = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao remover meta");
        }
    }

    private Meta mapear(ResultSet rs) throws SQLException {
        Meta meta = new Meta();
        meta.setId(rs.getLong("cd_meta"));
        meta.setLocalId(rs.getLong("cd_local"));
        meta.setRecursoId(rs.getLong("cd_recurso"));
        meta.setMetaMensal(rs.getBigDecimal("qt_meta_mensal"));
        meta.setDataInicio(rs.getDate("dt_inicio").toLocalDate());
        return meta;
    }
}
