package com.diegomelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AtualizarProduto {
    static void main() {

        String url = "jdbc:postgresql://localhost:5432/loja_java";
        String usuario = "postgres";
        String senha = "admin";

        String sql = "UPDATE produtos SET preco = ? WHERE id = ?";

        try {

            double preco = 25.00;
            int id = 6;

            Connection conexao = DriverManager.getConnection(url,usuario,senha);
            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setDouble(1,preco);
            comando.setInt(2,id);

            int linhasAfetada = comando.executeUpdate();

            if(linhasAfetada > 0){
                System.out.println("O Produto do ID:"+ id+" teve seu preço atualizado para: "+preco);
            }else{
                System.out.println("ERRO! Produto não Encontrado");
            }

            comando.close();
            conexao.close();

        }
        catch (SQLException e){
            System.out.println("Erro ao Atualizar Preço!");
            e.printStackTrace();
        }


    }
}
