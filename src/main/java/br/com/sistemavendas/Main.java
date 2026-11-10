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
        List<Produto> produtos = produtoDAO.listaTodosProdutos();

        // percorra esta lista e exiba
        for(Produto produto: produtos){
            // o metodo toString foi sobreescrito na classe Produto
            // dessa forma vai retornar o texto definido + os atributos do objeto
            System.out.println(produto);
        }

        Produto produto = produtoDAO.buscaPorIdProduto(2);

        if(produto != null){
            System.out.println(produto);
        }else {
            System.out.println("Produto Não Encontrado");
        }

        Produto novoProduto = produtoDAO.inserirProduto("Pizza Bacon", new BigDecimal("19.00"),40);
        System.out.println("Produto Cadastrado");
        System.out.println(novoProduto);

        // para mudar os valores do produto para gerar atualização
        novoProduto.setNome("Pizza Bacon Especial");
        novoProduto.setPreco(new BigDecimal("25.00"));
        novoProduto.setQuantidade(50);

        // passando o objeto como parametro simulando como se fosse a entrada de um usuario
        boolean atualizarProduto = produtoDAO.atualizarProduto(novoProduto);

        if (atualizarProduto){
            System.out.println("Atualizado Com Sucesso!");
        }else{
            System.out.println("Erro ao Atualizar!");
        }

        boolean deletarProduto = produtoDAO.deletarProduto(novoProduto.getId());

        if (deletarProduto){
            System.out.println("Deletado com Sucesso!");
        }else {
            System.out.println("Erro ao Deletar!");
        }

    }







}
