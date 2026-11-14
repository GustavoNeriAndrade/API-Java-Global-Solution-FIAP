package br.com.fiap.desperdiciozero.model;

import java.time.LocalDate;

public class Contato {

    private Long id;
    private String nome;
    private String email;
    private String assunto;
    private String mensagem;
    private LocalDate dataEnvio;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDate getDataEnvio() { return dataEnvio; }
    public void setDataEnvio(LocalDate dataEnvio) { this.dataEnvio = dataEnvio; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
