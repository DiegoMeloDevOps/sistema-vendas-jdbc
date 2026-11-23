package br.com.sistemavendas;


import br.com.sistemavendas.dao.ClienteDAO;
import br.com.sistemavendas.dao.ItemPedidoDAO;
import br.com.sistemavendas.dao.PedidoDAO;
import br.com.sistemavendas.dao.ProdutoDAO;
import br.com.sistemavendas.model.Cliente;
import br.com.sistemavendas.model.ItemPedido;
import br.com.sistemavendas.model.Pedido;
import br.com.sistemavendas.model.Produto;
import br.com.sistemavendas.service.ClienteService;
import br.com.sistemavendas.service.PedidoService;
import br.com.sistemavendas.service.ProdutoService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Main {
    static void main() throws SQLException {

        ClienteDAO clienteDAO = new ClienteDAO();
        ClienteService clienteService = new ClienteService(clienteDAO);


        //Cliente cliente =  clienteService.cadastrarCliente("Daniel","Daniel@gmail.com","1140028922");
        //System.out.println(cliente);

        Cliente cliente = clienteService.buscarClienteId(5L);
        System.out.println(cliente);

       // cliente = clienteService.buscarClienteId(999L);
       // System.out.println(cliente);

       // boolean verificaCliente = clienteService.existeCliente(5);
        //System.out.println(verificaCliente);
       // verificaCliente = clienteService.existeCliente(99999);
       // System.out.println(verificaCliente);

        boolean atualizaCliente = clienteService.atualizarCliente(5L,"Thiago" ,"tiago@gamil.com","4002-8922");
        System.out.println(atualizaCliente);
        boolean atualizaCliente2 = clienteService.atualizarCliente(99999,"Thiago" ,"tiago@gamil.com","4002-8922");
        System.out.println(atualizaCliente2);

        boolean deletaCliente = clienteService.deletarCliente(5L);
        System.out.println(deletaCliente);
        boolean deletaCliente2 = clienteService.deletarCliente(9999L);
        System.out.println(deletaCliente2);


    }
}
