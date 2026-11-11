package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Produto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProdutoService {

    // O produtoService é a camada responsavel por toda a lógica de negocio, nela aplicaremos as regras
    // antes de chamarmos os metodos da Classe ProdutoDAO pois assim evitaremos alguns erros de validação

    // chamamos uma variavel que é um tipo obbjeto do ProdutoDAO
    private final ProdutoDAO produtoDAO;

    // criamos um construtor para o ProdutoService e ele "herdara" os metodos do ProdutoDAO
    public ProdutoService(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
    }
    // criamos uma metodo Cadastrar produto nele primeiramente aplicamos as regras de validação de negocios
    public Produto cadastrarProduto(String nome, BigDecimal preco, int quantidade) throws SQLException{
        // caso cair na validação ele ira emitir um "exception" avisando do erro referente a falta de preenchimento, ou
        // preenchumento errado dos campos
        if (nome == null || nome.isBlank()){
            throw new IllegalArgumentException("O nome do Produto é obrigatorio!");
        }

        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O preço deve ser maior que zero!");
        }

        if (quantidade < 0){
            throw new IllegalArgumentException("A quantidade não pode ser negativa!");
        }
        // caso passe em todos os testes é retornado o metodo de inserir o produto
        return produtoDAO.inserirProduto(nome, preco, quantidade);
    }

    // cria um lista do tipo produto e cria um metodo que retorna o metodo do produtoDAO que lista os produtos
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

    // criamos um metodo que valida os dados , se todos eles estao sendo preenchidos corretamente
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

    // aqui trataremos as transações SQL , para evitarmos insconsistência nos dados do banco
    public void atualizarEstoques(List<Produto> produtos) throws SQLException {

        // Validação inicial dos campos
        if (produtos == null || produtos.isEmpty()) {
            throw new IllegalArgumentException(
                    "A lista de produtos não pode estar vazia."
            );
        }

        // Valida os produtos antes de iniciar a transação
        for (Produto produto : produtos) {
            if (produto == null || produto.getId() < 1
                    || produto.getQuantidade() < 0) {
                throw new IllegalArgumentException(
                        "Produto ou quantidade inválida."
                );
            }
        }

        // Obtém uma única conexão para toda a operação
        // se criarmos uma conexao para cada pedido de forma que seja uma transação individual
        // poderemos ter insconsitência no banco de dados
        try (Connection conn = ConnectionFactory.getConnection()) {

            // Desativa o AutoCommit, assim impediremos que a transação
            // seja de forma automatica commitada para o banco caso apresente erros
            conn.setAutoCommit(false);

            try {

                // Percorre todos os produtos
                for (Produto produto : produtos) {

                    boolean atualizado = produtoDAO.atualizarEstoque(
                            conn,
                            produto.getId(),
                            produto.getQuantidade()
                    );

                    // Se nenhum produto for atualizado, interrompe a operação
                    if (!atualizado) {
                        throw new SQLException(
                                "Produto não encontrado: ID " + produto.getId()
                        );
                    }
                }

                //Se tudo der certo Confirma todas as atualizações
                conn.commit();

            } catch (SQLException | RuntimeException e) {

                // Desfaz todas as atualizações
                try {
                    // com o autoCommit desligado, ele ira realizar esta operação de rollback
                    // assim a transação sera cancelada caso haja inconsitência nos dados
                    // impedindo assim de que ocorra inconsitencia no banco
                    conn.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }

                throw e;
            }
        }
    }


}
