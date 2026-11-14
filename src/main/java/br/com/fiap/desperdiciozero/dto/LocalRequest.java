package br.com.fiap.desperdiciozero.dto;

import jakarta.validation.constraints.*;

/**
 * Corpo de POST /locais e PUT /locais/{id}.
 */
public class LocalRequest {

    @NotBlank(message = "Informe o nome do local.")
    @Size(min = 3, max = 100, message = "O nome deve ter de 3 a 100 caracteres.")
    private String nome;

    @NotBlank(message = "Informe o tipo do local.")
    @Pattern(regexp = "CASA|COMUNIDADE|NEGOCIO", message = "O tipo deve ser CASA, COMUNIDADE ou NEGOCIO.")
    private String tipo;

    @NotBlank(message = "Informe a cidade.")
    @Size(max = 60, message = "A cidade deve ter no máximo 60 caracteres.")
    private String cidade;

    @NotBlank(message = "Informe o bairro.")
    @Size(max = 60, message = "O bairro deve ter no máximo 60 caracteres.")
    private String bairro;

    @NotNull(message = "Informe a quantidade de pessoas.")
    @Min(value = 1, message = "A quantidade de pessoas deve ser maior que zero.")
    @Max(value = 999999, message = "A quantidade de pessoas deve ser de no máximo 999999.")
    private Integer qtPessoas;

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
}
