package com.example.lojadepartamento;

import com.example.lojadepartamento.modelo.Cliente;
import com.example.lojadepartamento.modelo.Pedido;

import java.util.ArrayList;

public class ControllerPedido {
    private static ControllerPedido instancia;
    private ArrayList<Pedido> listaPedidos;

    public static ControllerPedido getInstance() {
        if (instancia == null) {
            return instancia = new ControllerPedido();
        } else {
            return instancia;
        }
    }

    ControllerPedido() {
        listaPedidos = new ArrayList<>();
    }

    public void salvarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    public ArrayList<Pedido> retornarPedidos() {
        return listaPedidos;
    }
}
