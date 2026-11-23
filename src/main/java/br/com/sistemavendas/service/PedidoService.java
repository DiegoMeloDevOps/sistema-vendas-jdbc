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
    private final ClienteService clienteService;

    // criando um construtor com injeção de dependencia
    // assim a logica de negocio fica separada da logica de conexao, se caso mexermos na logica
    // e formas de operação com o banco não quebraremos a logica de negocio
    public PedidoService(
            PedidoDAO pedidoDAO,
            ItemPedidoDAO itemPedidoDAO,
            ProdutoDAO produtoDAO,
            ClienteService clienteService) {

        this.pedidoDAO = pedidoDAO;
        this.itemPedidoDAO = itemPedidoDAO;
        this.produtoDAO = produtoDAO;
        this.clienteService = clienteService;
    }

    // regra de negocio para cadastrar pedidos
    // Incluindo no metodo o controle de transação
    public int cadastrarPedido(Pedido pedido) throws SQLException {

        // 1. Validações iniciais
        if (pedido == null || pedido.getIdCliente() == null
                || pedido.getIdCliente() < 1) {
            throw new IllegalArgumentException("Cliente inválido!");
        }

        // verifica se o cliente existe, chamando o metodo da classe clienteService
        clienteService.existeCliente(pedido.getIdCliente());


        if (pedido.getvalorTotal() == null
                || pedido.getvalorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor total inválido!");
        }

        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter itens!");
        }
        //variável responsável por acumular o valor dos itens.
        BigDecimal valorCalculado = BigDecimal.ZERO;

        for (ItemPedido item : pedido.getItens()) {

            if (item == null
                    || item.getidProduto() == null
                    || item.getidProduto() < 1
                    || item.getQuantidade() <= 0
                    || item.getprecoUnitario() == null
                    || item.getprecoUnitario().compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException("Item do pedido inválido!");
            }
            //calcula o subtotal de cada item.
            BigDecimal subtotal = item.getprecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade()));
            //acumula os subtotais.
            valorCalculado = valorCalculado.add(subtotal);


        }

        //compara o valor calculado com o valor informado no pedido.
        if (valorCalculado.compareTo(pedido.getvalorTotal()) != 0) {
            throw new IllegalArgumentException(
                    "O valor total do pedido não corresponde à soma dos itens!"
            );
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

        if (pedido.getidPedido() == null || pedido.getidPedido() <= 0){
            throw new IllegalArgumentException("Id do pedido invalido!");
        }

        clienteService.existeCliente(pedido.getIdCliente());

        return pedidoDAO.atualizarPedido(pedido);
    }
    // regra de negocio para deletar pedido
    public boolean deletarPedido(Long id) throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID do pedido inválido!");
        }

        try (Connection conn = ConnectionFactory.getConnection()) {

            conn.setAutoCommit(false);

            try {

                // 1. Busca todos os itens antes de excluí-los.
                List<ItemPedido> itens =
                        itemPedidoDAO.buscarItensPorPedido(conn, id.intValue());

                // 2. Devolve cada produto ao estoque.
                for (ItemPedido item : itens) {

                    boolean devolvido = produtoDAO.devolverEstoque(
                            conn,
                            item.getidProduto(),
                            item.getQuantidade()
                    );

                    if (!devolvido) {
                        throw new SQLException(
                                "Não foi possível devolver o produto ID: "
                                        + item.getidProduto()
                        );
                    }
                }

                // 3. Exclui os itens vinculados ao pedido.
                itemPedidoDAO.deletarItemPedido(conn, id);

                // 4. Exclui o pedido.
                boolean excluido = pedidoDAO.deletarPedido(conn, id);

                if (!excluido) {
                    throw new SQLException("Pedido não encontrado!");
                }

                // 5. Confirma todas as operações.
                conn.commit();

                return true;

            } catch (SQLException | RuntimeException e) {

                // Se algo falhar, desfaz todas as operações.
                try {
                    conn.rollback();
                } catch (SQLException erroRollback) {
                    e.addSuppressed(erroRollback);
                }

                throw e;
            }
        }
    }

}
