package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Produto;

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
    public List<Produto> listaTodos() throws SQLException{

        // criamos a variavel que armazena o comando sql
        String sql = "SELECT id,nome,preco,quantidade FROM produtos";

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

    public Produto buscaPorId(int id)throws SQLException{

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


}
