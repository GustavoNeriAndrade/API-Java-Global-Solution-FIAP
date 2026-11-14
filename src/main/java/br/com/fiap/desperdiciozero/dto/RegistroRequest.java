package br.com.fiap.desperdiciozero.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Corpo de POST /locais/{id}/registros e PUT /registros/{id}.
 */
public class RegistroRequest {

    @NotNull(message = "Informe o local.")
    private Long localId;

    @NotNull(message = "Informe o recurso.")
    private Long recursoId;

    @NotNull(message = "Informe a causa.")
    private Long causaId;

    @NotNull(message = "Informe a quantidade desperdiçada.")
    @Positive(message = "A quantidade deve ser maior que zero.")
    @Digits(integer = 8, fraction = 2, message = "A quantidade aceita até 8 dígitos e 2 casas decimais.")
    private BigDecimal quantidade;

    @NotNull(message = "Informe a data do desperdício.")
    @PastOrPresent(message = "A data não pode ser no futuro.")
    private LocalDate data;

    @Size(max = 200, message = "A observação deve ter no máximo 200 caracteres.")
    private String observacao;

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
