package br.com.fiap.desperdiciozero.controller;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import br.com.fiap.desperdiciozero.dto.ContatoRequest;
import br.com.fiap.desperdiciozero.dto.ContatoResponse;
import br.com.fiap.desperdiciozero.model.Contato;
import br.com.fiap.desperdiciozero.service.ContatoService;

/**
 * Pagina de contato (rota publica, sem login). So existe o POST: a API nao tem autenticacao,
 * entao listar as mensagens exporia nome e e-mail de quem escreveu. As mensagens sao lidas
 * direto na tabela tb_contato.
 */
@RestController
@RequestMapping("/contatos")
public class ContatoController {

    private final ContatoService service;
    private final ModelMapper mapper;

    public ContatoController(ContatoService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<ContatoResponse> criar(@Valid @RequestBody ContatoRequest request) {
        Contato criado = service.criar(mapper.map(request, Contato.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(criado, ContatoResponse.class));
    }
}
