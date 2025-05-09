package org.example;

public interface EstadoPedido {
    void preparar(Pedido pedido);

    void entregar(Pedido pedido);
    

    void finalizar(Pedido pedido);
    
}