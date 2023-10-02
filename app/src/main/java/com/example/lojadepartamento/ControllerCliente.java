package com.example.lojadepartamento;

import com.example.lojadepartamento.modelo.Cliente;

import java.util.ArrayList;

public class ControllerCliente {

    private static ControllerCliente instancia;
    private ArrayList<Cliente> listaClientes;

    public static ControllerCliente getInstance() {
        if (instancia == null) {
            return instancia = new ControllerCliente();
        } else {
            return instancia;
        }
    }

    ControllerCliente() {
        listaClientes = new ArrayList<>();
    }

    public void salvarCliente(Cliente cliente) {
        listaClientes.add(cliente);
    }

    public ArrayList<Cliente> retornarClientes() {
        return listaClientes;
    }
}
