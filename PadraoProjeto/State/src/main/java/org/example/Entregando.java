package org.example;

public class Entregando implements EstadoPedido {

    @Override
    public void preparar(Pedido pedido) {
        
        throw new IllegalStateException("Pedido não pode ser preparado, pois já está em entrega");
    }

    @Override
    public void entregar(Pedido pedido) {
        throw new IllegalStateException("Pedido já está em entrega");
    }

    @Override
    public void finalizar(Pedido pedido) {
        System.out.println("Pedido foi finalizado.");
        pedido.setState(new Finalizado());;
    }
   
}