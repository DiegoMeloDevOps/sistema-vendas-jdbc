package br.com.sistemavendas.model;

import java.math.BigDecimal;

public class ItemPedido {

    private Integer idItem;
    private Integer idPedido;
    private Integer idProduto;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPedido(Integer idItem, Integer idPedido, Integer idProduto, int quantidade, BigDecimal precoUnitario) {
        this.idItem = idItem;
        this.idPedido = idPedido;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public ItemPedido(Integer idProduto, int quantidade,
                      BigDecimal precoUnitario) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Integer getidPedido() {
        return idPedido;
    }

    public void setidPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getidProduto() {
        return idProduto;
    }

    public void setidProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public Integer getidItem() {
        return idItem;
    }

    public void setidItem(Integer idItem) {
        this.idItem = idItem;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getprecoUnitario() {
        return precoUnitario;
    }

    public void setprecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItem=" + idItem +
                ", idPedido=" + idPedido +
                ", idProduto=" + idProduto +
                ", quantidade=" + quantidade +
                ", precoUnitario=" + precoUnitario +
                '}';
    }
}
