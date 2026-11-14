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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import br.com.fiap.desperdiciozero.dto.MetaRequest;
import br.com.fiap.desperdiciozero.dto.MetaResponse;
import br.com.fiap.desperdiciozero.exception.RequisicaoInvalidaException;
import br.com.fiap.desperdiciozero.model.Meta;
import br.com.fiap.desperdiciozero.service.MetaService;

@RestController
public class MetaController {

    private final MetaService service;
    private final ModelMapper mapper;

    public MetaController(MetaService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/locais/{localId}/metas")
    public List<MetaResponse> listarPorLocal(@PathVariable Long localId) {
        return service.listarPorLocal(localId).stream().map(m -> mapper.map(m, MetaResponse.class)).toList();
    }

    @PostMapping("/locais/{localId}/metas")
    public ResponseEntity<MetaResponse> criar(@PathVariable Long localId, @Valid @RequestBody MetaRequest request) {
        if (!localId.equals(request.getLocalId())) {
            throw new RequisicaoInvalidaException("O local informado no corpo é diferente do local da rota.");
        }
        Meta criada = service.criar(localId, mapper.map(request, Meta.class));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/metas/{id}").buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(criada, MetaResponse.class));
    }

    @GetMapping("/metas/{id}")
    public MetaResponse buscar(@PathVariable Long id) {
        return mapper.map(service.buscar(id), MetaResponse.class);
    }

    @PutMapping("/metas/{id}")
    public MetaResponse atualizar(@PathVariable Long id, @Valid @RequestBody MetaRequest request) {
        Meta atualizada = service.atualizar(id, mapper.map(request, Meta.class));
        return mapper.map(atualizada, MetaResponse.class);
    }

    @DeleteMapping("/metas/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
