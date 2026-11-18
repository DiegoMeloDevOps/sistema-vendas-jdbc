package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.PedidoDAO;
import br.com.sistemavendas.model.Pedido;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO;

    // criando um construtor com injeção de dependencia
    // assim a logica de negocio fica separada da logica de conexao, se caso mexermos na logica
    // e formas de operação com o banco não quebraremos a logica de negocio
    public PedidoService(PedidoDAO pedidoDAO){
        this.pedidoDAO = pedidoDAO;
    }

    // regra de negocio para cadastrat pedidos
    public int cadastrarPedido(Pedido pedido) throws SQLException{

        if(pedido == null || pedido.getIdCliente() < 1){
            throw new IllegalArgumentException("O id do Cliente é obrigatorio!");
        }
        if(pedido.getvalorTotal() == null ||pedido.getvalorTotal().compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("O preço deve ser maior que zero!");
        }

        return pedidoDAO.salvarPedido(pedido);
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
