package br.com.sistemavendas.service;

import br.com.sistemavendas.dao.ItemPedidoDAO;
import br.com.sistemavendas.model.ItemPedido;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class ItemPedidoService {

    private final ItemPedidoDAO itemPedidoDAO;

    public ItemPedidoService(ItemPedidoDAO itemPedidoDAO){
        this.itemPedidoDAO = itemPedidoDAO;
    }

    // regra de negocio para salvar item pedido
    public ItemPedido salvarItemPedido(Integer id_pedido, Integer id_produto, int quantidade, BigDecimal preco_unitario) throws SQLException{

        if (id_pedido == null || id_produto == null || id_pedido < 1 || id_produto < 1){
            throw new IllegalArgumentException("Preencha os IDs Corretamente! ");
        }

        if( quantidade < 1  ){
            throw new IllegalArgumentException("Quantidade não pode ser menor que 1!");
        }


        if ( preco_unitario == null ||preco_unitario.compareTo(BigDecimal.ZERO) < 1){
            throw new IllegalArgumentException("O preço Unitario deve ser maior que zero!");
        }

        return itemPedidoDAO.salvarItemPedido(id_pedido,id_produto,quantidade,preco_unitario);
    }

    // regra de negocio para buscar por id
    public List<ItemPedido> buscarItensPorPedido(Integer id) throws SQLException{

        if (id == null || id < 1){
            throw new IllegalArgumentException("O id deve ser maior que 1!");
        }

        return itemPedidoDAO.buscarItensPorPedido(id);
    }

    // regra de negocio para atualizar pedido
    public boolean atualizarItemPedido(int quantidade, BigDecimal preco_unitario,int id_item) throws SQLException{

        if (id_item < 1){
            throw new IllegalArgumentException("O id deve ser maior que zero!");
        }

        if (quantidade < 1){
            throw new IllegalArgumentException("A quantidade deve ser maior que zero!");
        }

        if ( preco_unitario == null || preco_unitario.compareTo(BigDecimal.ZERO) < 1){
            throw new IllegalArgumentException("O preço tem que ser maior que zero!");
        }

        return itemPedidoDAO.atualizarItemPedido( quantidade, preco_unitario,id_item);
    }

    // regra de negocio para deletar itemPedido
    public boolean deletarItemPedido(int id_item) throws SQLException{

        if (id_item < 1){
            throw new IllegalArgumentException("O id tem que ser maior que zero!");
        }
        return itemPedidoDAO.deletarItemPedido(id_item);
    }

}
