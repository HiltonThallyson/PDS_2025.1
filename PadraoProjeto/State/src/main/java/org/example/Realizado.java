package org.example;


public class Realizado implements EstadoPedido {

    @Override
    public void preparar(Pedido pedido) {
        System.out.println("Pedido está sendo preparado.");
        pedido.setState(new Preparando());
    }

    @Override
    public void entregar(Pedido pedido) {
        
        throw new IllegalStateException("Pedido não pode ser entregue antes de ser preparado");
    }

    @Override
    public void finalizar(Pedido pedido) {
        throw new IllegalStateException("Pedido não pode ser finalizado pois acabou de ser realizado");
    }
    
}
