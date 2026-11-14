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

import br.com.fiap.desperdiciozero.dto.RegistroRequest;
import br.com.fiap.desperdiciozero.dto.RegistroResponse;
import br.com.fiap.desperdiciozero.exception.RequisicaoInvalidaException;
import br.com.fiap.desperdiciozero.model.Registro;
import br.com.fiap.desperdiciozero.service.RegistroService;

@RestController
public class RegistroController {

    private final RegistroService service;
    private final ModelMapper mapper;

    public RegistroController(RegistroService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/locais/{localId}/registros")
    public List<RegistroResponse> listarPorLocal(@PathVariable Long localId) {
        return service.listarPorLocal(localId).stream().map(r -> mapper.map(r, RegistroResponse.class)).toList();
    }

    @PostMapping("/locais/{localId}/registros")
    public ResponseEntity<RegistroResponse> criar(@PathVariable Long localId,
            @Valid @RequestBody RegistroRequest request) {
        if (!localId.equals(request.getLocalId())) {
            throw new RequisicaoInvalidaException("O local informado no corpo é diferente do local da rota.");
        }
        Registro criado = service.criar(localId, mapper.map(request, Registro.class));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/registros/{id}").buildAndExpand(criado.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.map(criado, RegistroResponse.class));
    }

    @GetMapping("/registros/{id}")
    public RegistroResponse buscar(@PathVariable Long id) {
        return mapper.map(service.buscar(id), RegistroResponse.class);
    }

    @PutMapping("/registros/{id}")
    public RegistroResponse atualizar(@PathVariable Long id, @Valid @RequestBody RegistroRequest request) {
        Registro atualizado = service.atualizar(id, mapper.map(request, Registro.class));
        return mapper.map(atualizado, RegistroResponse.class);
    }

    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
