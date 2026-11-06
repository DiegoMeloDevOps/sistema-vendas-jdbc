package com.diegomelo;

import java.sql.*;

public class ConsultarProdutos {

    static void main() {
        String url = "jdbc:postgresql://localhost:5432/loja_java";
        String usuario = "postgres";
        String senha = "admin";

        // estabelecendo conexão com o banco
        try {
            // conection para representar a conexao
            // DriverManager para obter a conexao
            Connection conexao = DriverManager.getConnection(url,usuario,senha);

            // criamos uma variavel que possui uma query onde ? é um paramentro que ira ser preenchido
            String sql = "SELECT * FROM produtos WHERE quantidade > ?";

            // criamos um objeto que ira executar comando e passamos com argumento a variavel sql
            // ou seja ja passamos o comando que ele ira executar na variavel
            PreparedStatement comando = conexao.prepareStatement(sql);

            // variavel para alterar o valor do filtro
            int valorFiltro = 15;

            // aqui preenchemos o parametro "?" passamos o indice do parametro e o valor a ser preenchido
            comando.setDouble(1,valorFiltro);

            // O objeto resultado irao armazenar o resultado da query executada
            ResultSet resultado = comando.executeQuery();

            // percorre todos os registro da tabela
            while (resultado.next()){

                // faz a conversao do "tipo da tabela sql para um tipo de variavel java atraves do getTipo do valor do campo
                int id = resultado.getInt("id");
                String nome = resultado.getString("nome");
                double preco = resultado.getDouble("preco");
                int quantidade = resultado.getInt("quantidade");

                // mostra os valores dos campos que foram passados nas variaveis a cada interação do while
                System.out.println("ID: "+ id);
                System.out.println("Produto: "+nome);
                System.out.println("Preco R$: "+preco);
                System.out.println("Quantidade: "+quantidade);

                System.out.println("==========================");

            }
            // fechando os recursos
            resultado.close();
            comando.close();
            conexao.close();




        }


        catch(SQLException e){
            System.out.println("Erro ao Tentar realizar Consulta");
            e.printStackTrace();
        }


    }
}
