package br.com.fiap.desperdiciozero.dto;

import java.math.BigDecimal;

/**
 * Dica devolvida pela API.
 */
public class DicaResponse {

    private Long id;

    private Long recursoId;

    private String titulo;

    private String descricao;

    private BigDecimal economiaPct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getEconomiaPct() {
        return economiaPct;
    }

    public void setEconomiaPct(BigDecimal economiaPct) {
        this.economiaPct = economiaPct;
    }
}
