package br.com.fiap.desperdiciozero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import br.com.fiap.desperdiciozero.model.Dica;

@Repository
public class DicaDAO {

    private static final String COLUNAS = "cd_dica, cd_recurso, nm_titulo, ds_dica, vl_economia_pct";

    private final DataSource dataSource;

    public DicaDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Dica> listar() {
        String sql = "SELECT " + COLUNAS + " FROM tb_dica ORDER BY cd_dica";
        try (Connection con = dataSource.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            List<Dica> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(mapear(rs));
            }
            return lista;
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar dicas");
        }
    }

    public List<Dica> listarPorRecurso(Long recursoId) {
        String sql = "SELECT " + COLUNAS + " FROM tb_dica WHERE cd_recurso = ? ORDER BY vl_economia_pct DESC, cd_dica";
        try (Connection con = dataSource.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, recursoId);
            try (ResultSet rs = ps.executeQuery()) {
                List<Dica> lista = new ArrayList<>();
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
                return lista;
            }
        } catch (SQLException e) {
            throw TradutorErroSql.traduzir(e, "Erro ao listar dicas do recurso");
        }
    }

    private Dica mapear(ResultSet rs) throws SQLException {
        Dica dica = new Dica();
        dica.setId(rs.getLong("cd_dica"));
        dica.setRecursoId(rs.getLong("cd_recurso"));
        dica.setTitulo(rs.getString("nm_titulo"));
        dica.setDescricao(rs.getString("ds_dica"));
        dica.setEconomiaPct(rs.getBigDecimal("vl_economia_pct"));
        return dica;
    }
}
