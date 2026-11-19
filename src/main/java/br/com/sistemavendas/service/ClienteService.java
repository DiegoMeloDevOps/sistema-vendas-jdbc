package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ClienteDAO;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clientDAO){
        this.clienteDAO = clientDAO;
    }

}
