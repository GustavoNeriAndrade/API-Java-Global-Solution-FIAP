package br.com.fiap.desperdiciozero.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.desperdiciozero.dao.LocalDAO;
import br.com.fiap.desperdiciozero.dao.RegistroDAO;
import br.com.fiap.desperdiciozero.exception.RecursoNaoEncontradoException;
import br.com.fiap.desperdiciozero.exception.RegraDeNegocioException;
import br.com.fiap.desperdiciozero.model.Local;

@Service
public class LocalService {

    private final LocalDAO localDao;
    private final RegistroDAO registroDao;

    public LocalService(LocalDAO localDao, RegistroDAO registroDao) {
        this.localDao = localDao;
        this.registroDao = registroDao;
    }

    public Local criar(Local local) {
        local.setDataCadastro(LocalDate.now());
        return localDao.inserir(local);
    }

    public List<Local> listar() {
        return localDao.listar();
    }

    public Local buscar(Long id) {
        return localDao.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Local não encontrado."));
    }

    public Local atualizar(Long id, Local dados) {
        Local atual = buscar(id);
        dados.setId(id);
        dados.setDataCadastro(atual.getDataCadastro());
        localDao.atualizar(dados);
        return dados;
    }

    /** Regra de negocio: local que ja tem registros nao pode ser excluido (409). */
    public void remover(Long id) {
        buscar(id);
        if (registroDao.existePorLocal(id)) {
            throw new RegraDeNegocioException("Este local já tem registros e não pode ser excluído.");
        }
        localDao.remover(id);
    }
}
