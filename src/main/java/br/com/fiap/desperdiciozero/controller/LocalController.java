package br.com.fiap.desperdiciozero.controller;

import java.net.URI;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import br.com.fiap.desperdiciozero.dto.LocalRequest;
import br.com.fiap.desperdiciozero.dto.LocalResponse;
import br.com.fiap.desperdiciozero.model.Local;
import br.com.fiap.desperdiciozero.service.LocalService;

@RestController
@RequestMapping("/locais")
public class LocalController {

    private final LocalService service;
    private final ModelMapper mapper;

    public LocalController(LocalService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<LocalResponse> criar(@Valid @RequestBody LocalRequest request) {
        Local criado = service.criar(mapper.map(request, Local.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(criado, LocalResponse.class));
    }

    @GetMapping
    public List<LocalResponse> listar() {
        return service.listar().stream().map(l -> mapper.map(l, LocalResponse.class)).toList();
    }

    @GetMapping("/{id}")
    public LocalResponse buscar(@PathVariable Long id) {
        return mapper.map(service.buscar(id), LocalResponse.class);
    }

    @PutMapping("/{id}")
    public LocalResponse atualizar(@PathVariable Long id, @Valid @RequestBody LocalRequest request) {
        Local atualizado = service.atualizar(id, mapper.map(request, Local.class));
        return mapper.map(atualizado, LocalResponse.class);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
