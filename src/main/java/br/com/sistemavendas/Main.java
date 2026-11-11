package br.com.sistemavendas;


import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.model.Produto;
import br.com.sistemavendas.service.ProdutoService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class Main {
    static void main() throws SQLException {

        ProdutoDAO produtoDAO = new ProdutoDAO();

        // criando o service que ira validadar as regras de negocios antes de aplicas as operações
        // sql do produtoDAO
        ProdutoService produtoService = new ProdutoService(produtoDAO);

        // criando um objeto da classe produtom , depois chamaremos o service para vaidar e se passar, realiza a operação sql
        Produto novoProduto = produtoService.cadastrarProduto("Pizza Abacaxi",new BigDecimal("19.90"),10);
        // validando os dados e mensagem caso ocorra algum erro
        if(novoProduto != null){
            System.out.println("Produto: "+novoProduto.getNome()+" cadastrado!");
        }else {
            System.out.println("Erro! Produto Não Cadastrado!");
        }

        List<Produto> produtos = produtoService.listarProdutos();
        for(Produto produtosLista : produtos){
            System.out.println("ID: "+produtosLista.getId()+" Produto: "+produtosLista.getNome()+" Preço R$: "+produtosLista.getPreco()+" Quantidade: "+produtosLista.getQuantidade());
        }

        Produto produto = produtoService.buscaProdutoId(novoProduto.getId());
        if (produto != null){
            System.out.println("ID: "+produto.getId()+" Produto: "+produto.getNome()+" Preco R$: "+produto.getPreco()+" Quantidade: "+produto.getQuantidade());
        }else{
            System.out.println("Produto Não Encontrado! ");
        }

        // como no atualizar produto o metodo recebe um Produto , estamos "simulando" uma mudança no cadastro
        produto.setNome("Pizza Abacaxi Especial!");
        produto.setPreco(new BigDecimal("30.00"));
        produto.setQuantidade(10);

        // se o Update funcionou ira retornnar uma mensagem true ou false
        boolean atualizado = produtoService.atualizarProduto(produto);

        if (atualizado){
            System.out.println("ID: "+produto.getId()+ " Produto: "+produto.getNome()+ " Atualizado! "+" Preço R$: "+produto.getPreco()+"  Quantidade: "+produto.getQuantidade());
        }else {
            System.out.println("Produto Não Encontrado!");
        }

        boolean produtoDeletado = produtoService.deletarProduto(produto.getId());
        if (produtoDeletado) {
            System.out.println("Produto " + produto.getNome() + " Deletado!");
        }
        else{
            System.out.println("Produto Não Encotrado!");
        }

    }







}
