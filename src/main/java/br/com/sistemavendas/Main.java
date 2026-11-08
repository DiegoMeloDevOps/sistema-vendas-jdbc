package br.com.sistemavendas;


import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.model.Produto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Main {
    static void main() throws SQLException {

        // Instancie um objeto da Classe produtoDAO
        ProdutoDAO produtoDAO = new ProdutoDAO();
        // crie uma lista do tipo produto que se chama produtos
        // pois ira armazenar todos os produtos com seus campos
        List<Produto> produtos = produtoDAO.listaTodos();

        // percorra esta lista e exiba
        for(Produto produto: produtos){
            // o metodo toString foi sobreescrito na classe Produto
            // dessa forma vai retornar o texto definido + os atributos do objeto
            System.out.println(produto);
        }

        Produto produto = produtoDAO.buscaPorId(2);

        if(produto != null){
            System.out.println(produto);
        }else {
            System.out.println("Produto Não Encontrado");
        }

        Produto novoProduto = produtoDAO.Inserir("Pizza Bacon", new BigDecimal("19.90"),90);
        System.out.println("Produto Cadastrado");
        System.out.println(novoProduto);

    }
}
