package br.com.fiap.desperdiciozero.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Corpo de POST /locais/{id}/metas e PUT /metas/{id}.
 */
public class MetaRequest {

    @NotNull(message = "Informe o local.")
    private Long localId;

    @NotNull(message = "Informe o recurso.")
    private Long recursoId;

    @NotNull(message = "Informe a meta mensal.")
    @Positive(message = "A meta mensal deve ser maior que zero.")
    @Digits(integer = 8, fraction = 2, message = "A meta aceita até 8 dígitos e 2 casas decimais.")
    private BigDecimal metaMensal;

    @NotNull(message = "Informe a data de início da meta.")
    private LocalDate dataInicio;

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
