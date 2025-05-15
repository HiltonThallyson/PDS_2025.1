package org.example;

public class Main {
    public static void main(String[] args) {
        Pedido pedido1 = new Pedido();
        Pedido pedido2 = new Pedido();

        // Vamos realizar o pedido 1 seguindo o fluxo normal
        System.out.println("Pedido 1 estado: " + pedido1.getState().getClass().getSimpleName());
        pedido1.preparar();
        pedido1.entregar();
        pedido1.finalizar();
        
        // Agora vamos tentar realizar o pedido 2, mas vamos simular um erro.
        try {
            System.out.println("Pedido 2 estado: " + pedido2.getState().getClass().getSimpleName());
            pedido2.preparar();
            pedido2.finalizar(); // Tentativa de finalizar sem entregar
        } catch (IllegalStateException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
        
    }
}
