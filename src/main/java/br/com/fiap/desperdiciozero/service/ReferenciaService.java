package br.com.fiap.desperdiciozero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.desperdiciozero.dao.CausaDAO;
import br.com.fiap.desperdiciozero.dao.DicaDAO;
import br.com.fiap.desperdiciozero.dao.RecursoDAO;
import br.com.fiap.desperdiciozero.model.Causa;
import br.com.fiap.desperdiciozero.model.Dica;
import br.com.fiap.desperdiciozero.model.Recurso;

/** Dados de referencia (somente leitura): recursos, causas e dicas. */
@Service
public class ReferenciaService {

    private final RecursoDAO recursoDao;
    private final CausaDAO causaDao;
    private final DicaDAO dicaDao;

    public ReferenciaService(RecursoDAO recursoDao, CausaDAO causaDao, DicaDAO dicaDao) {
        this.recursoDao = recursoDao;
        this.causaDao = causaDao;
        this.dicaDao = dicaDao;
    }

    public List<Recurso> listarRecursos() {
        return recursoDao.listar();
    }

    public List<Causa> listarCausas(Long recursoId) {
        return recursoId == null ? causaDao.listar() : causaDao.listarPorRecurso(recursoId);
    }

    public List<Dica> listarDicas(Long recursoId) {
        return recursoId == null ? dicaDao.listar() : dicaDao.listarPorRecurso(recursoId);
    }
}
