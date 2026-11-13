package br.com.sistemavendas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    // Novo pedido, ainda não persistido
    public Pedido(Integer idCliente, int id_cliente, LocalDateTime dataPedido, BigDecimal valorTotal) {
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }

    // Quando formos consultar um produto que ja existe
    public Pedido(Integer idPedido, LocalDateTime dataPedido, BigDecimal valorTotal){
        this.idPedido = idPedido;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
    }


    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    private Integer idCliente;
    private Integer idPedido;
    private LocalDateTime dataPedido;
    private BigDecimal valorTotal;
    private List<ItemPedido> itens;


    public Integer getidPedido() {
        return idPedido;
    }

    public void setidPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public LocalDateTime getdataPedido() {
        return dataPedido;
    }

    public void setdataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getvalorTotal() {
        return valorTotal;
    }

    public void setvalorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String toString(){

        return "Pedido{"+ "Id Cliente" +getIdCliente() + "id Pedido :"+ getidPedido() + " Data Pedido: "+getdataPedido()+" valorTotal: "+getvalorTotal()+"}";
    }

}
