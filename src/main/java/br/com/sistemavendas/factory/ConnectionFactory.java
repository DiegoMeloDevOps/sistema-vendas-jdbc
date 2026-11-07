package br.com.sistemavendas.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL = "jdbc:postgresql://localhost:5432/loja_java";
    private static final String USUARIO = System.getenv("DB_USER");
    private static final String SENHA = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws SQLException{

        if(USUARIO == null || SENHA == null){
            throw new IllegalStateException(
                    "Configure as varivéis de DB_USER e DB_PASSWORD"
            );
        }
        return DriverManager.getConnection(URL,USUARIO,SENHA);
    }


}
