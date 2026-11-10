package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.model.Produto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO;

    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }

    public Produto cadastrarProduto(String nome, BigDecimal preco, int quantidade) throws SQLException{
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome do Produto é obrigatorio!");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O preço deve ser maior que zero!");
        }

        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade não pode ser negativa!");
        }
        return produtoDAO.inserirProduto(nome, preco, quantidade);
    }

    public List<Produto> listarProdutos() throws SQLException {
        return produtoDAO.listaTodosProdutos();
    }

    public Produto buscaProdutoId(int id) throws SQLException{

        if(id < 1){
            throw new IllegalArgumentException("O id deve ser maior que zero");
        }

        return produtoDAO.buscaPorIdProduto(id);
    }

    public boolean atualizarProduto(Produto produto)throws SQLException{
        if (produto == null){
            throw new IllegalArgumentException("O Produto Não pode ser Vazio!");
        }

        if (produto.getId() <1){
            throw new IllegalArgumentException("O id deve ser preenchido!");
        }

        validarDados(
                produto.getNome(),
                produto.getPreco(),
                produto.getQuantidade()
        );


        return produtoDAO.atualizarProduto(produto);
    }

    public boolean deletarProduto(int id)throws SQLException{
        if (id < 1){
            throw new IllegalArgumentException("O id deve ser maior que zero");
        }
        return produtoDAO.deletarProduto(id);
    }

    private void validarDados(String nome,BigDecimal preco,int quantidade){

        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome não pode ficar Vazio!");
        }

        if(preco == null){
            throw new IllegalArgumentException("O Preço não pode ficar Vazio!");
        }

        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade não pode ser menor que zero!");
        }


    }

}
