package com.example.lojadepartamento;

import static java.lang.String.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojadepartamento.modelo.Cliente;
import com.example.lojadepartamento.modelo.ItemVenda;
import com.example.lojadepartamento.modelo.Pedido;

import java.util.ArrayList;

public class LancamentoPedidoActivity extends AppCompatActivity {

    private Button btSalvarPedido;
    private EditText edQuantidadeItemPedido;
    private EditText edValorItemPedido;
    private EditText edQuantidadeParcelasPedido;
    private ImageButton btAddItem;
    private ImageButton btConfirmarParcelas;
    private RadioButton rbAVista;
    private RadioButton rbAPrazo;
    private RadioGroup rgCondicaoPagamentoPedido;
    private Spinner spClientePedido;
    private Spinner spItemPedido;
    private TextView tvErroClientePedido;
    private TextView tvErroItemPedido;
    private TextView tvListaItensPedido;
    private TextView tvQuantidadeItensPedido;
    private TextView tvValorTotalItensPedido;
    private TextView tvQuantidadeParcelasPedido;
    private TextView tvValorParcelasPedido;
    private TextView tvValorTotalDoPedido;
    private ArrayList<Cliente> clientes;
    private ArrayList<ItemVenda> itensVenda;
    private int posicaoSelecionadaCliente = 0;
    private int posicaoSelecionadaItem = 0;
    private int quantidadeItens = 0;
    private double valorTotal = 0;
    private String listaItensPedido = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancamento_pedido);

        edQuantidadeItemPedido = findViewById(R.id.edQuantidadeItemPedido);
        edValorItemPedido = findViewById(R.id.edValorItemPedido);
        edQuantidadeParcelasPedido = findViewById(R.id.edQuantidadeParcelasPedido);
        rbAVista = findViewById(R.id.rbAVista);
        rbAPrazo = findViewById(R.id.rbAPrazo);
        rgCondicaoPagamentoPedido = findViewById(R.id.rgCondicaoPagamentoPedido);
        spClientePedido = findViewById(R.id.spClientePedido);
        spItemPedido = findViewById(R.id.spItemPedido);
        tvErroClientePedido = findViewById(R.id.tvErroClientePedido);
        tvErroItemPedido = findViewById(R.id.tvErroItemPedido);
        tvListaItensPedido = findViewById(R.id.tvListaItensPedido);
        tvQuantidadeItensPedido = findViewById(R.id.tvQuantidadeItensPedido);
        tvValorTotalItensPedido = findViewById(R.id.tvValorTotalPedido);
        tvQuantidadeParcelasPedido = findViewById(R.id.tvQuantidadeParcelasPedido);
        tvValorParcelasPedido = findViewById(R.id.tvValorParcelasPedido);
        tvValorTotalDoPedido = findViewById(R.id.tvValorTotalDoPedido);
        btAddItem = findViewById(R.id.btAddItem);
        btConfirmarParcelas = findViewById(R.id.btConfirmarParcelas);
        btSalvarPedido = findViewById(R.id.btSalvarPedido);


        btAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarItemPedido();
            }
        });


        // Pega a posição selecionada no Spinner
        spClientePedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoSelecionadaCliente = posicao;
                    tvErroClientePedido.setVisibility(View.GONE);
                } else {
                    tvErroClientePedido.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Adiciona os clientes no Spinner
        popularListaClientes();


        spItemPedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                if (posicao > 0) {
                    posicaoSelecionadaItem = posicao;
                    tvErroItemPedido.setVisibility(View.GONE);
                    edValorItemPedido.setText(valueOf(itensVenda.get(posicao - 1).getValor()));
                } else {
                    tvErroItemPedido.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        popularListaItens();


        rgCondicaoPagamentoPedido.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                realizarPagamento();
            }
        });


        btConfirmarParcelas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarValorParcelas();
            }
        });


        btSalvarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarPedido();
            }
        });
    }


    private void salvarPedido() {
        Pedido pedido = new Pedido();
        pedido.setCliente(clientes.get(posicaoSelecionadaCliente - 1));
        pedido.setValorTotal(valorTotal);

        ControllerPedido.getInstance().salvarPedido(pedido);

        Toast.makeText(LancamentoPedidoActivity.this, "Pedido Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }


    private void mostrarValorParcelas() {
        int quantidadeParcelas;

        if (edQuantidadeParcelasPedido.getText().toString().isEmpty()) {
            edQuantidadeParcelasPedido.setError("A Quantidade deve ser informada!");
            edQuantidadeParcelasPedido.requestFocus();
            return;
        }

        try {
            quantidadeParcelas = Integer.parseInt(edQuantidadeParcelasPedido.getText().toString());
            if (quantidadeParcelas <= 0) {
                edQuantidadeParcelasPedido.setError("Informe uma quantidade maior que zero!");
                edQuantidadeParcelasPedido.requestFocus();
                return;
            }

            double valorParcelas = (valorTotal * 1.05) / Integer.parseInt(String.valueOf(edQuantidadeParcelasPedido.getText()));
            tvValorParcelasPedido.setText("Valor de cada parcela: R$" + valorParcelas);
            tvValorTotalDoPedido.setText("Valor Total: R$" + valorTotal * 1.05);
        } catch (NumberFormatException e) {
            edQuantidadeParcelasPedido.setError("Informe uma quantidade válida!");
            edQuantidadeParcelasPedido.requestFocus();
        }
    }


    private void realizarPagamento() {
        if (rbAVista.isChecked()) {
            tvValorTotalDoPedido.setText("Valor Total: R$" + valorTotal * 0.95);
            tvQuantidadeParcelasPedido.setVisibility(View.GONE);
            edQuantidadeParcelasPedido.setVisibility(View.GONE);
            tvValorParcelasPedido.setVisibility(View.GONE);
            btConfirmarParcelas.setVisibility(View.GONE);
        }
        if (rbAPrazo.isChecked()) {
            tvQuantidadeParcelasPedido.setVisibility(View.VISIBLE);
            edQuantidadeParcelasPedido.setVisibility(View.VISIBLE);
            tvValorParcelasPedido.setVisibility(View.VISIBLE);
            btConfirmarParcelas.setVisibility(View.VISIBLE);
        }
    }


    private void adicionarItemPedido() {
        int quantidade;

        if (edQuantidadeItemPedido.getText().toString().isEmpty()) {
            edQuantidadeItemPedido.setError("A Quantidade deve ser informada!");
            edQuantidadeItemPedido.requestFocus();
            return;
        } else {
            quantidade = Integer.parseInt(edQuantidadeItemPedido.getText().toString());
            if (quantidade <= 0) {
                edQuantidadeItemPedido.setError("Informe uma quantidade maior que zero!");
                edQuantidadeItemPedido.requestFocus();
                return;
            }
        }

        ItemVenda item = itensVenda.get(posicaoSelecionadaItem - 1);

        quantidadeItens += quantidade;
        valorTotal += item.getValor() * quantidade;

        Toast.makeText(LancamentoPedidoActivity.this, "Item adicionado ao Pedido!", Toast.LENGTH_LONG).show();

        listaItensPedido += "Código: " + item.getCodigo() + "\nDescrição: " + item.getDescricao() + "\nQuantidade: " + quantidade + "\nValor unitário: " + item.getValor() + "\nValor Total: " + item.getValor() * quantidade + "\n\n";

        tvListaItensPedido.setText(listaItensPedido);
        tvQuantidadeItensPedido.setText(valueOf(quantidadeItens));
        tvValorTotalItensPedido.setText(valueOf(valorTotal));
    }


    private void popularListaItens() {
        itensVenda = ControllerItemVenda.getInstance().retornarItens();
        String[] vetorItens = new String[itensVenda.size()+1];
        vetorItens[0] = "";
        for (int i = 1; i < vetorItens.length; i++){
            ItemVenda item = itensVenda.get(i - 1);
            vetorItens[i] = item.getDescricao() + " - " + item.getCodigo();
        }

        ArrayAdapter adapter = new ArrayAdapter(LancamentoPedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetorItens);

        spItemPedido.setAdapter(adapter);
    }


    private void popularListaClientes() {
        clientes = ControllerCliente.getInstance().retornarClientes();
        String[] vetorClientes = new String[clientes.size()+1];
        vetorClientes[0] = "";
        for (int i = 1; i < vetorClientes.length; i++){
            Cliente cliente = clientes.get(i - 1);
            vetorClientes[i] = cliente.getNome() + " - " + cliente.getCpf();
        }

        ArrayAdapter adapter = new ArrayAdapter(LancamentoPedidoActivity.this, android.R.layout.simple_dropdown_item_1line, vetorClientes);

        spClientePedido.setAdapter(adapter);
    }
}