package br.com.fiap.desperdiciozero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.desperdiciozero.dao.LocalDAO;
import br.com.fiap.desperdiciozero.dao.MetaDAO;
import br.com.fiap.desperdiciozero.dao.RecursoDAO;
import br.com.fiap.desperdiciozero.exception.RecursoNaoEncontradoException;
import br.com.fiap.desperdiciozero.exception.RegraDeNegocioException;
import br.com.fiap.desperdiciozero.exception.RequisicaoInvalidaException;
import br.com.fiap.desperdiciozero.model.Meta;

@Service
public class MetaService {

    private final MetaDAO metaDao;
    private final LocalDAO localDao;
    private final RecursoDAO recursoDao;

    public MetaService(MetaDAO metaDao, LocalDAO localDao, RecursoDAO recursoDao) {
        this.metaDao = metaDao;
        this.localDao = localDao;
        this.recursoDao = recursoDao;
    }

    public List<Meta> listarPorLocal(Long localId) {
        exigirLocal(localId);
        return metaDao.listarPorLocal(localId);
    }

    public Meta criar(Long localId, Meta meta) {
        exigirLocal(localId);
        meta.setLocalId(localId);
        exigirRecurso(meta.getRecursoId());
        exigirMetaUnica(localId, meta.getRecursoId(), null);
        return metaDao.inserir(meta);
    }

    public Meta buscar(Long id) {
        return metaDao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Meta não encontrada."));
    }

    public Meta atualizar(Long id, Meta dados) {
        buscar(id);
        dados.setId(id);
        if (!localDao.existe(dados.getLocalId())) {
            throw new RequisicaoInvalidaException("O local informado não existe.");
        }
        exigirRecurso(dados.getRecursoId());
        exigirMetaUnica(dados.getLocalId(), dados.getRecursoId(), id);
        metaDao.atualizar(dados);
        return dados;
    }

    public void remover(Long id) {
        buscar(id);
        metaDao.remover(id);
    }

    private void exigirLocal(Long localId) {
        if (!localDao.existe(localId)) {
            throw new RecursoNaoEncontradoException("Local não encontrado.");
        }
    }

    private void exigirRecurso(Long recursoId) {
        recursoDao.buscarPorId(recursoId)
                .orElseThrow(() -> new RequisicaoInvalidaException("O recurso informado não existe."));
    }

    /** Regra de negocio: uma meta por local e recurso (409). O banco tambem garante com UNIQUE. */
    private void exigirMetaUnica(Long localId, Long recursoId, Long ignorarId) {
        metaDao.buscarPorLocalERecurso(localId, recursoId).ifPresent(existente -> {
            if (!existente.getId().equals(ignorarId)) {
                throw new RegraDeNegocioException("Este local já tem uma meta para esse recurso. Edite a meta existente.");
            }
        });
    }
}
