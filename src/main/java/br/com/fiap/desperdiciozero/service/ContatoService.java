package br.com.fiap.desperdiciozero.service;

import org.springframework.stereotype.Service;

import br.com.fiap.desperdiciozero.dao.ContatoDAO;
import br.com.fiap.desperdiciozero.model.Contato;

@Service
public class ContatoService {

    private final ContatoDAO contatoDao;

    public ContatoService(ContatoDAO contatoDao) {
        this.contatoDao = contatoDao;
    }

    /** Remove espacos das pontas e guarda o e-mail em minusculas antes de gravar. */
    public Contato criar(Contato contato) {
        contato.setNome(contato.getNome().trim());
        contato.setEmail(contato.getEmail().trim().toLowerCase());
        contato.setMensagem(contato.getMensagem().trim());
        return contatoDao.inserir(contato);
    }
}
