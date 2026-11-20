package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.ItemPedido;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    public ItemPedido salvarItemPedido(Connection conexao, Integer id_pedido, Integer id_produto, int quantidade, BigDecimal preco_unitario) throws SQLException {

        String sql = "INSERT INTO itens_pedido (id_pedido,id_produto, quantidade,preco_unitario) VALUES (?,?,?,?) RETURNING id_item";

        try (PreparedStatement stmt = conexao.prepareStatement(sql);) {

            stmt.setInt(1, id_pedido);
            stmt.setInt(2, id_produto);
            stmt.setInt(3, quantidade);
            stmt.setBigDecimal(4, preco_unitario);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    int idGerado = resultado.getInt("id_item");
                    return new ItemPedido(idGerado, id_pedido, id_produto, quantidade, preco_unitario);
                }
            }


        }
        throw new SQLException("Não foi possível cadastrar o item do pedido.");
    }

    public List<ItemPedido> buscarItensPorPedido(Integer id) throws SQLException{
        String sql = "SELECT id_item,id_pedido,id_produto,quantidade,preco_unitario FROM itens_pedido WHERE id_pedido = ?";

        List<ItemPedido> itens = new ArrayList<>();

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);){

            stmt.setInt(1,id);



            try( ResultSet resultado = stmt.executeQuery()) {
                while (resultado.next()){
                    ItemPedido item = new ItemPedido(
                            resultado.getInt("id_item"),
                            resultado.getInt("id_pedido"),
                            resultado.getInt("id_produto"),
                            resultado.getInt("quantidade"),
                            resultado.getBigDecimal("preco_unitario"));
                    itens.add(item);
                }


            }

        }

        return itens ;
    }

public boolean atualizarItemPedido(int quantidade, BigDecimal preco_unitario,int id_item) throws SQLException{

    String sql = "UPDATE itens_pedido SET quantidade, preco_unitario WHERE id_item = ? ";

    try (Connection conexao = ConnectionFactory.getConnection();
         PreparedStatement stmt = conexao.prepareStatement(sql); ){

        stmt.setInt(1,id_item);

        int validaAtualizacaoItemPedido = stmt.executeUpdate();

        if (validaAtualizacaoItemPedido > 0){
            return true;
        }else {
            return false;
        }

    }

}

public boolean deletarItemPedido(int id_item) throws SQLException{

    String sql = "DELETE FROM itens_pedidos WHERE id_item= ?";

    try(Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement stmt = conexao.prepareStatement(sql);){

        stmt.setInt(1,id_item);
        // Criando uma variavel int pois o metodo retorna um valor inteiro das linhas afetadas (1 para true e 0 para não)
        int validaDelecao = stmt.executeUpdate();

        if (validaDelecao> 0){
            return true;
        }else {
            return false;
        }

    }


}

}




