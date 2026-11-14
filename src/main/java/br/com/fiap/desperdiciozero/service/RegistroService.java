package br.com.fiap.desperdiciozero.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.desperdiciozero.dao.CausaDAO;
import br.com.fiap.desperdiciozero.dao.LocalDAO;
import br.com.fiap.desperdiciozero.dao.RecursoDAO;
import br.com.fiap.desperdiciozero.dao.RegistroDAO;
import br.com.fiap.desperdiciozero.exception.RecursoNaoEncontradoException;
import br.com.fiap.desperdiciozero.exception.RegraDeNegocioException;
import br.com.fiap.desperdiciozero.exception.RequisicaoInvalidaException;
import br.com.fiap.desperdiciozero.model.Causa;
import br.com.fiap.desperdiciozero.model.Registro;

@Service
public class RegistroService {

    private final RegistroDAO registroDao;
    private final LocalDAO localDao;
    private final RecursoDAO recursoDao;
    private final CausaDAO causaDao;

    public RegistroService(RegistroDAO registroDao, LocalDAO localDao, RecursoDAO recursoDao, CausaDAO causaDao) {
        this.registroDao = registroDao;
        this.localDao = localDao;
        this.recursoDao = recursoDao;
        this.causaDao = causaDao;
    }

    public List<Registro> listarPorLocal(Long localId) {
        exigirLocal(localId);
        return registroDao.listarPorLocal(localId);
    }

    public Registro criar(Long localId, Registro registro) {
        exigirLocal(localId);
        registro.setLocalId(localId);
        validarReferencias(registro);
        return registroDao.inserir(registro);
    }

    public Registro buscar(Long id) {
        return registroDao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Registro não encontrado."));
    }

    public Registro atualizar(Long id, Registro dados) {
        buscar(id);
        dados.setId(id);
        if (!localDao.existe(dados.getLocalId())) {
            throw new RequisicaoInvalidaException("O local informado não existe.");
        }
        validarReferencias(dados);
        registroDao.atualizar(dados);
        return dados;
    }

    public void remover(Long id) {
        buscar(id);
        registroDao.remover(id);
    }

    private void exigirLocal(Long localId) {
        if (!localDao.existe(localId)) {
            throw new RecursoNaoEncontradoException("Local não encontrado.");
        }
    }

    /** Recurso e causa precisam existir e a causa precisa pertencer ao recurso (regra de negocio, 409). */
    private void validarReferencias(Registro registro) {
        recursoDao.buscarPorId(registro.getRecursoId())
                .orElseThrow(() -> new RequisicaoInvalidaException("O recurso informado não existe."));
        Causa causa = causaDao.buscarPorId(registro.getCausaId())
                .orElseThrow(() -> new RequisicaoInvalidaException("A causa informada não existe."));
        if (!causa.getRecursoId().equals(registro.getRecursoId())) {
            throw new RegraDeNegocioException("Essa causa não pertence ao recurso escolhido.");
        }
    }
}
