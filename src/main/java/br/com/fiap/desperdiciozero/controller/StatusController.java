package br.com.fiap.desperdiciozero.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Rota simples para conferir se a API esta no ar (tambem serve de health check no Render). */
@RestController
public class StatusController {

    @GetMapping("/")
    public Map<String, String> status() {
        return Map.of("api", "Desperdicio Zero", "status", "no ar");
    }
}
