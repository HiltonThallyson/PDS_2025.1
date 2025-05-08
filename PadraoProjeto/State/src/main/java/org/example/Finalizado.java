package org.example;

public class Finalizado implements EstadoPedido {

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido não pode ser preparado, pois já está finalizado");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido não pode ser entregue, pois já está finalizado");
    }

    @Override
    public void finalizar(Pedido pedido) {
        throw new IllegalStateException("Pedido já foi finalizado");
    }
    
}
