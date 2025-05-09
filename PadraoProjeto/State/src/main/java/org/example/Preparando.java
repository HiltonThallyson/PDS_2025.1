package org.example;

public class Preparando implements EstadoPedido {

    @Override
    public void preparar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está em preparação");
    }


    @Override
    public void entregar(Pedido pedido) {
        System.out.println("Pedido está sendo entregue.");
        pedido.setState(new Entregando());
    }

    @Override
    public void finalizar(Pedido pedido) {
        throw new IllegalStateException("Pedido não pode ser finalizado pois está sendo preparado");
    }
    
}