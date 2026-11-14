package br.com.fiap.desperdiciozero.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Registro devolvido pela API.
 */
public class RegistroResponse {

    private Long id;

    private Long localId;

    private Long recursoId;

    private Long causaId;

    private BigDecimal quantidade;

    private LocalDate data;

    private String observacao;

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

    public Long getCausaId() {
        return causaId;
    }

    public void setCausaId(Long causaId) {
        this.causaId = causaId;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
