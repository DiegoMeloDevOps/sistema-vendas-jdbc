package com.diegomelo;

import java.sql.*;

public class CadastrarProduto {
    static void main() {

        String url = "jdbc:postgresql://localhost:5432/loja_java";
        String usuario = "postgres";
        String senha = "admin";

        String sql = "INSERT INTO produtos (nome,preco,quantidade) VALUES(?,?,?)";

        try {

            Connection conexao = DriverManager.getConnection(url,usuario,senha);

            PreparedStatement comando = conexao.prepareStatement(sql);

            String nome = "Pizza Espinafre";
            double preco = 19.50;
            int quantidade = 8;

            comando.setString(1,nome);
            comando.setDouble(2,preco);
            comando.setInt(3,quantidade);

            int linhasAfetadas = comando.executeUpdate();

            System.out.println("Produto Cadastrado com Sucesso!");
            System.out.println("Linhas Inseridas "+linhasAfetadas);
            System.out.println("O produto "+nome+" Foi Adicionado!");

            comando.close();
            conexao.close();

        }



        catch (SQLException e) {
            System.out.println("Erro ao Cadastrar o Produto!");
            e.printStackTrace();
        }

    }
}
