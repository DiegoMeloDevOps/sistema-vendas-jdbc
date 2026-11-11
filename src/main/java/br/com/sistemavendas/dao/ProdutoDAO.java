package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    // O DAO é responsavel pelas execução das Query ao banco
    // cada tabela é uma classeDAO com seus respectivos comando SQl
    // aqui criamos metodos de para realizar as query

    // query para consultar toda a tabela
    // criamos uma lista do tipo Produto que ira percorrer toda a tabela
    public List<Produto> listaTodosProdutos() throws SQLException{

        // criamos a variavel que armazena o comando sql
        String sql = "SELECT id,nome,preco,quantidade FROM produtos ORDER BY id ASC";

        // criamos uma lista do tipo produto que é um arraylist, visto que nao sabemos quantos itens tem a tabela
        // o arraylist sera o melhor para armazanar
        List<Produto> produtos = new ArrayList<>();

        // try-with-resource, tente conectar ao banco
        try(Connection conexao = ConnectionFactory.getConnection();
            // criando um objeto PreparedeStatement para executar uma query (neste caso n tem paramentro)
            PreparedStatement stmt = conexao.prepareStatement(sql);
            // apos conectar armazene o valor da query no objeto ResultSet
            ResultSet resultado = stmt.executeQuery()){

            // enquanto verdade percorra este objeto e a cada interação instancie um objeto da Classe Produto
            // chamando seu metodo construtor e convertendo os valores da tabela para tipo java
            while (resultado.next()){
                Produto produto = new Produto(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getBigDecimal("preco"),
                        resultado.getInt("quantidade")
                );
                // e a cada iteração do while apos criar, armazene este objeto no arraylist
                produtos.add(produto);
            }
            // retorna o arraylist produtos
            return produtos;
        }


    }

    public Produto buscaPorIdProduto(int id)throws SQLException{

        String sql = "SELECT id,nome,preco,quantidade FROM produtos WHERE id = ?";

        try(Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setInt(1,id);

            try (ResultSet resultado = stmt.executeQuery()){

                if(resultado.next()){
                    return new Produto(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getBigDecimal("preco"),
                            resultado.getInt("quantidade")
                    );
                }

            }
    

        }
        return null;
    }

    public Produto inserirProduto(String nome, BigDecimal preco, int quantidade) throws SQLException{

        // verificar se os campos estao corretos e não vazios
        if (nome == null || nome.isBlank()
                || preco == null
                || preco.compareTo(BigDecimal.ZERO) <= 0
                || quantidade < 0) {

            throw new IllegalArgumentException("Dados do produto inválidos!");
        }

        String sql = " INSERT INTO produtos (nome,preco,quantidade) VALUES (?,?,?) RETURNING id";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)){
            // faz a conversão para dos dados sql para o tipo java
            stmt.setString(1,nome);
            stmt.setBigDecimal(2,preco);
            stmt.setInt(3,quantidade);

            // Cria o objeto que vai armazenar o valor e executa a query e busca o ID do produto gerado
            try (ResultSet resultado = stmt.executeQuery()){
                // "busca" o objeto
                if (resultado.next()){
                    // aqui foi feito a conversão do valor SQL para java
                    int idGerado = resultado.getInt("id");
                    // e instacia o novo objeto com os valores corretos
                    return new Produto(idGerado,nome, preco, quantidade);
                }
            }



        }
        return null;
    }

    public boolean atualizarProduto(Produto produto)throws SQLException{


        String sql = "UPDATE produtos SET nome = ?,preco = ?,quantidade = ? WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);){

            stmt.setString(1,produto.getNome());
            stmt.setBigDecimal(2,produto.getPreco());
            stmt.setInt(3,produto.getQuantidade());
            stmt.setInt(4,produto.getId());

            int validaProduto = stmt.executeUpdate();


            if (validaProduto > 0){
                return true;
            }else{
                return false;
            }

        }





    }
    public boolean deletarProduto(int id) throws SQLException{
        String sql ="DELETE FROM produtos WHERE id = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);){

            stmt.setInt(1,id);

           int validadaExclusao =  stmt.executeUpdate();

            if(validadaExclusao > 0){
                return true;
            }else{
                return false;
            }

        }

    }

    public boolean atualizarEstoque(Connection conn, int id,int quantidade) throws SQLException{
        String sql = "UPDATE produtos SET quantidade = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,quantidade);
            stmt.setInt(2,id);

            return stmt.executeUpdate()>0;
        }


    }

}
