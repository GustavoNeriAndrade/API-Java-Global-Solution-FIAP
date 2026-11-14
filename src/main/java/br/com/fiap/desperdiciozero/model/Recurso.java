package br.com.fiap.desperdiciozero.model;

import java.math.BigDecimal;

/**
 * Agua, energia ou alimento (tabela tb_recurso).
 */
public class Recurso {

    private Long id;

    private String nome;

    private String unidade;

    private BigDecimal custoUnitario;

    private BigDecimal fatorCo2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public BigDecimal getCustoUnitario() {
        return custoUnitario;
    }

    public void setCustoUnitario(BigDecimal custoUnitario) {
        this.custoUnitario = custoUnitario;
    }

    public BigDecimal getFatorCo2() {
        return fatorCo2;
    }

    public void setFatorCo2(BigDecimal fatorCo2) {
        this.fatorCo2 = fatorCo2;
    }
}
