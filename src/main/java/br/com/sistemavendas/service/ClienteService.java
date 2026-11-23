package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ClienteDAO;
import br.com.sistemavendas.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ClienteService {

    private final ClienteDAO clienteDAO;

    public ClienteService(ClienteDAO clientDAO){
        this.clienteDAO = clientDAO;
    }

    // regra de negocio listar todos os clientes
    public List<Cliente> listarTodosClientes() throws SQLException{
        return clienteDAO.buscarTodosClientes();
    }
    // regra de negocio cadastrar clientes
    public Cliente cadastrarCliente(String nome,String email,String telefone) throws SQLException{

        if ( nome == null || nome.isBlank() ){
            throw new IllegalArgumentException("Nome não pode estar vazio!");
        }

        if ( email == null || email.isBlank() ){
            throw new IllegalArgumentException("Email não pode estar vazio!");
        }

        if (telefone == null || telefone.length() < 9){
            throw new IllegalArgumentException("Telefone não pode estar vazio!");
        }

        return clienteDAO.salvarCliente(nome,email,telefone);

    }

    public Cliente buscarClienteId(Long id_cliente)throws SQLException{

        if (id_cliente == null || id_cliente <1){
            throw new IllegalArgumentException("O id não pode estar vazio ou ser menor que 1!");
        }

        Cliente cliente = clienteDAO.buscarClienteId(id_cliente);

        if (cliente == null){
            throw new IllegalArgumentException("O cliente não existe!");
        }

        return clienteDAO.buscarClienteId(id_cliente);
    }

    public boolean atualizarCliente(long id_cliente,String nome,String email,String telefone) throws SQLException{

        if ( id_cliente <1){
            throw new IllegalArgumentException("O id não pode estar vazio ou ser menor que 1!");
        }

        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome não pode estar vazio ou nulo");
        }

        if ( email == null || email.isBlank() ){
            throw new IllegalArgumentException("O email não pode estar vazio ou nulo");
        }

        if (telefone == null || telefone.isBlank() || telefone.length() < 9){
            throw new IllegalArgumentException("O telefone não pode estar vazio,Nulo ou ser menor que 9 digitos");
        }

        boolean validaCliente = clienteDAO.atualizarCliente(id_cliente,nome,email,telefone);

        if (validaCliente){
            System.out.println("Cliente Atualizado!");
            return true;
        }
        System.out.println("Cliente não Foi atualizado!");
        return false;
    }

    public boolean deletarCliente(Long id_cliente) throws SQLException{

        if (id_cliente == null || id_cliente < 1){
            throw new IllegalArgumentException("O id não pode ser vazio ou menor que 1!");
        }

        boolean validaDelecao = clienteDAO.deletarCliente(id_cliente);
        if (validaDelecao){
            System.out.println("Cliente deletado com sucesso!!");
            return clienteDAO.deletarCliente(id_cliente);
        }
        System.out.println("Cliente não Existe!");
        return clienteDAO.deletarCliente(id_cliente);


    }

    public boolean existeCliente(Integer id_cliente) throws SQLException{

        if (id_cliente == null || id_cliente <= 0){
            throw new IllegalArgumentException("O id não pode ser nulo ou vazio!");
        }

          boolean verificador = clienteDAO.existeCliente(id_cliente);

        if (!verificador){
            throw new IllegalArgumentException("O cliente não existe");
        }
        return verificador;
    }

}
