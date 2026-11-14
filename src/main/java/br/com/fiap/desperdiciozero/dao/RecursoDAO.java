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

import br.com.fiap.desperdiciozero.model.Recurso;

@Repository
public class RecursoDAO {

    private static final String COLUNAS = "cd_recurso, nm_recurso, ds_unidade, vl_custo_unitario, vl_fator_co2";

    private final DataSource dataSource;

    public RecursoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Recurso> listar() {
        String sql = "SELECT " + COLUNAS + " FROM tb_recurso ORDER BY cd_recurso";
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            List<Recurso> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar recursos");
        }
    }

    public Optional<Recurso> buscarPorId(Long id) {
        String sql = "SELECT " + COLUNAS + " FROM tb_recurso WHERE cd_recurso = ?";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao buscar recurso");
        }
    }

    private Recurso mapear(ResultSet rs) throws SQLException {
        Recurso recurso = new Recurso();
        recurso.setId(rs.getLong("cd_recurso"));
        recurso.setNome(rs.getString("nm_recurso"));
        recurso.setUnidade(rs.getString("ds_unidade"));
        recurso.setCustoUnitario(rs.getBigDecimal("vl_custo_unitario"));
        recurso.setFatorCo2(rs.getBigDecimal("vl_fator_co2"));
        return recurso;
    }
}
