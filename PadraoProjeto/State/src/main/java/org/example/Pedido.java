package org.example;

public class Pedido {
    private EstadoPedido estado;

    public Pedido() {
        this.estado = new Realizado(); 
    }

    public void setState(EstadoPedido estado) {
        this.estado = estado;
    }

    public EstadoPedido getState() {
        return estado;
    }

    public void preparar() {
        estado.preparar(this);
    }
    public void entregar() {
        estado.entregar(this);
    }
    public void finalizar() {
        estado.finalizar(this);
    }

}