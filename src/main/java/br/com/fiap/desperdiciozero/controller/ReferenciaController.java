package br.com.fiap.desperdiciozero.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.desperdiciozero.dto.CausaResponse;
import br.com.fiap.desperdiciozero.dto.DicaResponse;
import br.com.fiap.desperdiciozero.dto.RecursoResponse;
import br.com.fiap.desperdiciozero.service.ReferenciaService;

/** Leituras que alimentam os selects, as unidades e as dicas do front. */
@RestController
public class ReferenciaController {

    private final ReferenciaService service;
    private final ModelMapper mapper;

    public ReferenciaController(ReferenciaService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/recursos")
    public List<RecursoResponse> listarRecursos() {
        return service.listarRecursos().stream().map(r -> mapper.map(r, RecursoResponse.class)).toList();
    }

    @GetMapping("/causas")
    public List<CausaResponse> listarCausas(@RequestParam(required = false) Long recurso) {
        return service.listarCausas(recurso).stream().map(c -> mapper.map(c, CausaResponse.class)).toList();
    }

    @GetMapping("/dicas")
    public List<DicaResponse> listarDicas(@RequestParam(required = false) Long recurso) {
        return service.listarDicas(recurso).stream().map(d -> mapper.map(d, DicaResponse.class)).toList();
    }
}
