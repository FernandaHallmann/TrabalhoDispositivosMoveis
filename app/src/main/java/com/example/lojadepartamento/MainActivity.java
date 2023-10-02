package com.example.lojadepartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btCadastroCliente;
    private Button btCadastroItem;
    private Button btLancarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCadastroCliente = findViewById(R.id.btCadastroCliente);
        btCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroClienteActivity.class);
            }
        });

        btCadastroItem = findViewById(R.id.btCadastroItem);
        btCadastroItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(CadastroItemVendaActivity.class);
            }
        });

        btLancarPedido = findViewById(R.id.btLancarPedido);
        btLancarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirActivity(LancamentoPedidoActivity.class);
            }
        });
    }

    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }
}