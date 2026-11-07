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

    public List<Produto> listaTodos() throws SQLException{

        String sql = "SELECT id,nome,preco,quantidade FROM produtos";

        List<Produto> produtos = new ArrayList<>();

        try(Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery()){

            while (resultado.next()){
                Produto produto = new Produto(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getBigDecimal("preco"),
                        resultado.getInt("quantidade")
                );
                produtos.add(produto);
            }
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
