package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ItemPedidoDAO;
import br.com.sistemavendas.dao.PedidoDAO;
import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.ItemPedido;
import br.com.sistemavendas.model.Pedido;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ItemPedidoDAO itemPedidoDAO;
    private final ProdutoDAO produtoDAO;

    // criando um construtor com injeção de dependencia
    // assim a logica de negocio fica separada da logica de conexao, se caso mexermos na logica
    // e formas de operação com o banco não quebraremos a logica de negocio
    public PedidoService(
            PedidoDAO pedidoDAO,
            ItemPedidoDAO itemPedidoDAO,
            ProdutoDAO produtoDAO) {

        this.pedidoDAO = pedidoDAO;
        this.itemPedidoDAO = itemPedidoDAO;
        this.produtoDAO = produtoDAO;
    }

    // regra de negocio para cadastrar pedidos
    // Incluindo no metodo o controle de transação
    public int cadastrarPedido(Pedido pedido) throws SQLException {

        // 1. Validações iniciais
        if (pedido == null || pedido.getIdCliente() == null
                || pedido.getIdCliente() < 1) {
            throw new IllegalArgumentException("Cliente inválido!");
        }

        if (pedido.getvalorTotal() == null
                || pedido.getvalorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total inválido!");
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter itens!");
        }

        for (ItemPedido item : pedido.getItens()) {

            if (item == null
                    || item.getidProduto() == null
                    || item.getidProduto() < 1
                    || item.getQuantidade() <= 0
                    || item.getprecoUnitario() == null
                    || item.getprecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException("Item do pedido inválido!");
            }
        }

        // Uma única conexão para toda a transação
        try (Connection conn = ConnectionFactory.getConnection()) {
            // desligando o auto commit
            conn.setAutoCommit(false);

            try {

                // Salva o pedido e recupera o ID gerado
                int idPedido = pedidoDAO.salvarPedido(conn, pedido);

                //  Percorre todos os itens
                for (ItemPedido item : pedido.getItens()) {

                    // Salva o item relacionado ao pedido
                    itemPedidoDAO.salvarItemPedido(
                            conn,
                            idPedido,
                            item.getidProduto(),
                            item.getQuantidade(),
                            item.getprecoUnitario()
                    );

                    // Atualiza o estoque
                    boolean atualizado = produtoDAO.atualizarEstoque(
                            conn,
                            item.getidProduto(),
                            item.getQuantidade()
                    );

                    if (!atualizado) {
                        throw new SQLException(
                                "Produto inexistente ou estoque insuficiente: ID "
                                        + item.getidProduto()
                        );
                    }
                }

                // Confirma todas as operações
                conn.commit();

                return idPedido;

            } catch (SQLException | RuntimeException e) {

                // Desfaz todas as operações
                try {
                    conn.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }

                throw e;
            }
        }
    }


    // regra de negocio para listar pedidos ( n tem regra é so para não chamar diretamente o pedidoDAO )
    public List<Pedido> listarPedidos() throws SQLException{

        return pedidoDAO.listaTodosPedido();
    }

    // regra de negocio para buscar id
    public Pedido buscarPedidoId(int id) throws SQLException{
        if (id < 1){
            throw new IllegalArgumentException("O id deve ser maior que zero!");
        }
        return pedidoDAO.buscaPedidoId(id);
    }
    // regra de negocio para atualizar pedido
    public boolean atualizarPedido(Pedido pedido) throws SQLException{

        if (pedido == null){
            throw new IllegalArgumentException("O pedido não pode ser nulo!");
        }

        if (pedido.getIdCliente() == null || pedido.getIdCliente() <= 0){
            throw new IllegalArgumentException("O Id deve ser preenchido e não pode ser menor que zero!");
        }
        if (pedido.getvalorTotal() == null || pedido.getvalorTotal().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O valor deve ser preenchido e não pode ser zero!");
        }

        return pedidoDAO.atualizarPedido(pedido);
    }
    // regra de negocio para deletar pedido
    public boolean deletarPedido(Long id)throws SQLException{

        if (id < 0 || id == null){
            throw new IllegalArgumentException("O id deve ser preenchido, não pode ser zero! e tem que existir!");
        }

        return pedidoDAO.deletarPedido(id);
    }

}
