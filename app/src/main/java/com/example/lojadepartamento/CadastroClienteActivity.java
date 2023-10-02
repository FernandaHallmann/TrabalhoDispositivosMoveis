package com.example.lojadepartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojadepartamento.modelo.Cliente;

import java.util.ArrayList;

public class CadastroClienteActivity extends AppCompatActivity {

    private EditText edNomeCliente;
    private EditText edCpfCliente;
    private Button btSalvarCliente;
    private TextView tvListaClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCpfCliente = findViewById(R.id.edCpfCliente);
        btSalvarCliente = findViewById(R.id.btSalvarCliente);
        tvListaClientes = findViewById(R.id.tvListaClientes);

        btSalvarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCliente();
            }
        });
        
        atualizarListaClientes();
    }

    private void salvarCliente() {
        if (edNomeCliente.getText().toString().isEmpty()) {
            edNomeCliente.setError("O nome do Cliente deve ser informado!");
            return;
        }
        if (edCpfCliente.getText().toString().isEmpty()) {
            edCpfCliente.setError("O CPF do Cliente deve ser informado!");
            return;
        }

        Cliente cliente = new Cliente();
        cliente.setNome(edNomeCliente.getText().toString());
        cliente.setCpf(edCpfCliente.getText().toString());

        ControllerCliente.getInstance().salvarCliente(cliente);

        Toast.makeText(CadastroClienteActivity.this, "Cliente Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }

    private void atualizarListaClientes() {
        String texto = "";

        ArrayList<Cliente> lista = ControllerCliente.getInstance().retornarClientes();
        for (Cliente cliente: lista) {
            texto += "Nome: " + cliente.getNome() + "\nCPF: " + cliente.getCpf() + "\n--------------------------------------------------------\n";
        }

        tvListaClientes.setText(texto);
    }
}