package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public Cliente salvarCliente(String nome,String email,String telefone) throws SQLException{

        // Não sabemos o id do cliente ainda ent quando cadastrarmos vamo obter o id do cliente
        String sql = "INSERT INTO clientes (nome,email,telefone) VALUES(?,?,?) RETURNING id_cliente ";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1,nome);
            stmt.setString(2,email);
            stmt.setString(3,telefone);

            try (ResultSet resultado = stmt.executeQuery()){
                if (resultado.next()){
                    long idGerado = resultado.getLong("id_cliente");

                    return new Cliente(idGerado,nome,email,telefone);

                }
                return null;
            }

            }

        }

        public List<Cliente> buscarTodosClientes() throws SQLException{

            String sql = "SELECT * FROM clientes ORDER BY nome ASC";

            List<Cliente> clientes = new ArrayList<>();

            try (Connection conexao = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(sql);
                 ResultSet resultado = stmt.executeQuery();){



                while (resultado.next()){
                    Cliente cliente = new Cliente(
                            resultado.getLong("id_cliente"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("telefone")
                    );

                    clientes.add(cliente);
                }
                return clientes;
            }

        }

        public Cliente buscarClienteId(Long id_cliente) throws SQLException{
            String sql = "SELECT * FROM clientes WHERE id_cliente = ?";

            try (Connection conexao = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(sql)){

                stmt.setLong(1,id_cliente);

                ResultSet resultado = stmt.executeQuery();

                if (resultado.next()){
                    return new Cliente(
                            resultado.getLong("id_cliente"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("telefone")
                    );
                }
                return null;

            }

        }

        public boolean atualizarCliente(Long id_cliente,String nome,String email,String telefone) throws SQLException{

            String sql = "UPDATE clientes SET nome = ?,email = ?, telefone = ? WHERE id_cliente = ?";

            try (Connection conexao = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conexao.prepareStatement(sql)){

                stmt.setString(1,nome);
                stmt.setString(2,email);
                stmt.setString(3,telefone);
                stmt.setLong(4,id_cliente);

                int validaAtualizacao = stmt.executeUpdate();

                if(validaAtualizacao > 0){
                    return true;
                }else {
                    return false;
                }

            }

        }

        public boolean deletarCliente(Long id_cliente) throws SQLException{
            String sql = "DELETE FROM clientes WHERE id_cliente = ?";

            try(Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)){

                stmt.setLong(1,id_cliente);

                int validaDelecaoCliente = stmt.executeUpdate();

                if (validaDelecaoCliente > 0){
                    return true;
                }else{
                    return false;
                }


            }

        }
    }


