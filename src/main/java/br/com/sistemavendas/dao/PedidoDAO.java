package br.com.sistemavendas.dao;

import br.com.sistemavendas.factory.ConnectionFactory;
import br.com.sistemavendas.model.Pedido;
import br.com.sistemavendas.model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    // lista todos os pedidos
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
    // busca pedido por id
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

    public int salvarPedido( Pedido pedido) throws SQLException{
        // insere um registro com as informações do pedido , ele pega a hora atual para cadastrar o horario do pedido,
        // e como não sabemo o id ele que vai ser gerado ele retorna para gente como valor
        String sql = "INSERT INTO pedidos (id_cliente,data_pedido,valor_total) VALUES (?,CURRENT_TIMESTAMP,?) RETURNING id_pedido";

        try (Connection conexao = ConnectionFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
            // fazendo a conversão dos dados trazido do banco para o tipo java
            stmt.setInt(1,pedido.getidPedido());
            stmt.setBigDecimal(2,pedido.getvalorTotal());

            // pegando o id do pedido e convertendo para um tipo java
            try (ResultSet resultado = stmt.executeQuery()){
                if (resultado.next()){
                    return resultado.getInt("id_pedido");
                }
            }


        }
        throw new SQLException("Não Foi possivel cadastrar o pedido. ");
    }


}