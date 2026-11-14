package br.com.fiap.desperdiciozero.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Teto mensal de desperdicio por local e recurso (tabela tb_meta, associativa N:N).
 */
public class Meta {

    private Long id;

    private Long localId;

    private Long recursoId;

    private BigDecimal metaMensal;

    private LocalDate dataInicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
    }

    public BigDecimal getMetaMensal() {
        return metaMensal;
    }

    public void setMetaMensal(BigDecimal metaMensal) {
        this.metaMensal = metaMensal;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }
}
