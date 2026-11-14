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

import br.com.fiap.desperdiciozero.model.Local;

@Repository
public class LocalDAO {

    private static final String COLUNAS = "cd_local, nm_local, ds_tipo, nm_cidade, nm_bairro, qt_pessoas, dt_cadastro";

    private final DataSource dataSource;

    public LocalDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Local inserir(Local local) {
        String sql = "INSERT INTO tb_local (" + COLUNAS + ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = dataSource.getConnection()) {
            long id = Sequencias.proximoValor(con, "sq_tb_local");
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.setString(2, local.getNome());
                ps.setString(3, local.getTipo());
                ps.setString(4, local.getCidade());
                ps.setString(5, local.getBairro());
                ps.setInt(6, local.getQtPessoas());
                ps.setDate(7, Date.valueOf(local.getDataCadastro()));
                ps.executeUpdate();
            }
            local.setId(id);
            return local;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao inserir local");
        }
    }

    public List<Local> listar() {
        String sql = "SELECT " + COLUNAS + " FROM tb_local ORDER BY cd_local";
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            List<Local> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar locais");
        }
    }

    public Optional<Local> buscarPorId(Long id) {
        String sql = "SELECT " + COLUNAS + " FROM tb_local WHERE cd_local = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar local");
        }
    }

    public boolean existe(Long id) {
        String sql = "SELECT 1 FROM tb_local WHERE cd_local = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao verificar local");
        }
    }

    public boolean atualizar(Local local) {
        String sql = "UPDATE tb_local SET nm_local = ?, ds_tipo = ?, nm_cidade = ?, nm_bairro = ?, qt_pessoas = ? WHERE cd_local = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, local.getNome());
            ps.setString(2, local.getTipo());
            ps.setString(3, local.getCidade());
            ps.setString(4, local.getBairro());
            ps.setInt(5, local.getQtPessoas());
            ps.setLong(6, local.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao atualizar local");
        }
    }

    /** As metas do local saem junto (ON DELETE CASCADE em tb_meta). Local com registros gera ORA-02292. */
    public boolean remover(Long id) {
        String sql = "DELETE FROM tb_local WHERE cd_local = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao remover local");
        }
    }

    private Local mapear(ResultSet rs) throws SQLException {
        Local local = new Local();
        local.setId(rs.getLong("cd_local"));
        local.setNome(rs.getString("nm_local"));
        local.setTipo(rs.getString("ds_tipo"));
        local.setCidade(rs.getString("nm_cidade"));
        local.setBairro(rs.getString("nm_bairro"));
        local.setQtPessoas(rs.getInt("qt_pessoas"));
        local.setDataCadastro(rs.getDate("dt_cadastro").toLocalDate());
        return local;
    }
}
