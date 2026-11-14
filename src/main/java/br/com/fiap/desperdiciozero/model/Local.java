package br.com.fiap.desperdiciozero.model;

import java.time.LocalDate;

/**
 * Casa, comunidade ou negocio que monitora o desperdicio (tabela tb_local).
 */
public class Local {

    private Long id;

    private String nome;

    private String tipo;

    private String cidade;

    private String bairro;

    private Integer qtPessoas;

    private LocalDate dataCadastro;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getQtPessoas() {
        return qtPessoas;
    }

    public void setQtPessoas(Integer qtPessoas) {
        this.qtPessoas = qtPessoas;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
