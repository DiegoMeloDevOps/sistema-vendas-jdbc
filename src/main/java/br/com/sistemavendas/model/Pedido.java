package br.com.sistemavendas.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Pedido {

    // 1. Criar um pedido novo, ainda não persistido
    public Pedido(Integer idCliente, BigDecimal valorTotal) {
        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
    }

    // 2. Recuperar um pedido existente do banco
    public Pedido(Integer idPedido, Integer idCliente,
                  LocalDateTime dataPedido, BigDecimal valorTotal) {

        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
    }

    // 3. Criar um pedido novo acompanhado dos seus itens
    public Pedido(Integer idCliente, BigDecimal valorTotal,
                  List<ItemPedido> itens) {

        this.idCliente = idCliente;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }




    public List<ItemPedido> getItens() {
        return itens;
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
