package br.com.sistemavendas;


import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.model.Produto;

import java.sql.SQLException;
import java.util.List;

public class Main {
    static void main() throws SQLException {

        ProdutoDAO produtoDAO = new ProdutoDAO();

        List<Produto> produtos = produtoDAO.listaTodos();

        for(Produto produto: produtos){
            System.out.println(produto);
        }

        Produto produto = produtoDAO.buscaPorId(2);

        if(produto != null){
            System.out.println(produto);
        }else {
            System.out.println("Produto Não Encontrado");
        }

    }
}
