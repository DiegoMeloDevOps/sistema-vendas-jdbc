package com.diegomelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgreSQL {
    static void main() {

        String url = "jdbc:postgresql://localhost:5432/loja_java";
        String usuario = "postgres";
        String senha = "admin";

        try {
            // classe conection representa a conexao com o banco
            // driverManager obtem a conexao
            Connection conexao = DriverManager.getConnection(
                    url,
                    usuario,
                    senha
            );

            System.out.println("Conexão Realizada com sucesso!");
            conexao.close();

            // para capturar os erros
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco");
            // serve para mostrar qual erro que deu ao tentar conectar
            e.printStackTrace();
        }


    }
}
