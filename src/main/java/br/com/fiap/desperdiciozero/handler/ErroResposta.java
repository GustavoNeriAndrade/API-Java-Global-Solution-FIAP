package br.com.fiap.desperdiciozero.handler;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/** Corpo padrao de todo erro da API: { "status": 409, "message": "..." }. */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErroResposta {

    private final int status;
    private final String message;
    private final Map<String, String> campos;

    public ErroResposta(int status, String message, Map<String, String> campos) {
        this.status = status;
        this.message = message;
        this.campos = campos;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getCampos() {
        return campos;
    }
}
