package br.com.fiap.desperdiciozero.dto;

import jakarta.validation.constraints.*;

public class ContatoRequest {

    @NotBlank(message = "Informe o seu nome.")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres.")
    private String nome;

    @NotBlank(message = "Informe o seu e-mail.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 120, message = "O e-mail deve ter no máximo 120 caracteres.")
    private String email;

    @NotBlank(message = "Escolha o assunto.")
    @Pattern(regexp = "DUVIDA|SUGESTAO|PROBLEMA|ELOGIO|PARCERIA", message = "O assunto deve ser DUVIDA, SUGESTAO, PROBLEMA, ELOGIO ou PARCERIA.")
    private String assunto;

    @NotBlank(message = "Escreva a sua mensagem.")
    @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres.")
    private String mensagem;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAssunto() { return assunto; }
    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
