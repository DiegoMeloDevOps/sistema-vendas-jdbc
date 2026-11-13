package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Pedido> listaTodosPedido() throws SQLException {

        String sql = "SELECT id_pedido, id_cliente, data_pedido, valor_total FROM pedidos ORDER BY id_pedido ASC";

        List<Pedido> pedidos = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                Pedido pedido = new Pedido(
                        resultado.getInt("id_pedido"), // Corrigido de 'id_pedidos' para 'id_pedido'
                        resultado.getInt("id_cliente"),
                        resultado.getTimestamp("data_pedido").toLocalDateTime(),
                        resultado.getBigDecimal("valor_total")
                );
                pedidos.add(pedido);
            }
            return pedidos;
        }
    }

    public Pedido buscaPedidoId(int id) throws SQLException {
        String sql = "SELECT id_pedido, id_cliente, data_pedido, valor_total FROM pedidos WHERE id_pedido = ?";

        try (Connection conexao = ConnectionFactory.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet resultado = stmt.executeQuery()) {
                if (resultado.next()) {
                    return new Pedido(
                            resultado.getInt("id_pedido"),
                            resultado.getInt("id_cliente"),
                            resultado.getTimestamp("data_pedido").toLocalDateTime(),
                            resultado.getBigDecimal("valor_total")
                    );
                }
            }

            return null;
        }
    }
}