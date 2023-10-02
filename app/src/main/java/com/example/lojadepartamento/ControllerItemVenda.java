package com.example.lojadepartamento;

import com.example.lojadepartamento.modelo.Cliente;
import com.example.lojadepartamento.modelo.ItemVenda;

import java.util.ArrayList;

public class ControllerItemVenda {

    private static ControllerItemVenda instancia;
    private ArrayList<ItemVenda> listaItens;

    public static ControllerItemVenda getInstance() {
        if (instancia == null) {
            return instancia = new ControllerItemVenda();
        } else {
            return instancia;
        }
    }

    ControllerItemVenda() {
        listaItens = new ArrayList<>();
    }

    public void salvarItem(ItemVenda item) {
        listaItens.add(item);
    }

    public ArrayList<ItemVenda> retornarItens() {
        return listaItens;
    }
}
