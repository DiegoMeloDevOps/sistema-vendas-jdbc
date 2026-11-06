package com.diegomelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeletarProduto {
    static void main() {

        String url = "jdbc:postgresql://localhost:5432/loja_java";
        String usuario = "postgres";
        String senha = "admin";

        String sql = "DELETE FROM produtos WHERE id = ?";

        try {

            int id = 3;

            Connection conexao = DriverManager.getConnection(url,usuario,senha);
            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setInt(1,id);

            int linhasAfetadas = comando.executeUpdate();

            if(linhasAfetadas > 0){
                System.out.println("Produto com o ID: "+id+" Deletado com Sucesso!");
            }else{
                System.out.println("Erro ao Deletar , "+id+" Incorreto!");
            }
            comando.close();
            conexao.close();

        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Produto!");
            e.printStackTrace();
        }


    }
}
