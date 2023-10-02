package com.example.lojadepartamento;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lojadepartamento.modelo.Cliente;
import com.example.lojadepartamento.modelo.ItemVenda;

import java.util.ArrayList;

public class CadastroItemVendaActivity extends AppCompatActivity {

    private EditText edCodigoItem;
    private EditText edDescricaoItem;
    private EditText edValorItem;
    private Button btSalvarItem;
    private TextView tvListaItens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_item_venda);

        edCodigoItem = findViewById(R.id.edCodigoItem);
        edDescricaoItem = findViewById(R.id.edDescricaoItem);
        edValorItem = findViewById(R.id.edValorItem);
        btSalvarItem = findViewById(R.id.btSalvarItem);
        tvListaItens = findViewById(R.id.tvListaItens);

        btSalvarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarItem();
            }
        });

        atualizarListaItens();
    }

    private void salvarItem() {
        int codigo = 0;
        double valor = 0;

        if (edCodigoItem.getText().toString().isEmpty()) {
            edCodigoItem.setError("O Código do Item deve ser informado!");
            return;
        } else {
            try {
                codigo = Integer.parseInt(edCodigoItem.getText().toString());
            } catch (Exception ex) {
                edCodigoItem.setError("Informe um Código válido (somente números)");
            }
        }
        if (edDescricaoItem.getText().toString().isEmpty()) {
            edDescricaoItem.setError("A Descrição do Item deve ser informado!");
            return;
        }
        if (edValorItem.getText().toString().isEmpty()) {
            edValorItem.setError("O Valor do Item deve ser informado!");
            return;
        } else {
            try {
                valor = Double.parseDouble(edValorItem.getText().toString());
            } catch (Exception ex) {
                edValorItem.setError("Informe um Valor válido (somente números)");
            }
        }

        ItemVenda item = new ItemVenda();
        item.setCodigo(codigo);
        item.setDescricao(edDescricaoItem.getText().toString());
        item.setValor(valor);

        ControllerItemVenda.getInstance().salvarItem(item);

        Toast.makeText(CadastroItemVendaActivity.this, "Item de Venda Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();

        finish();
    }

    private void atualizarListaItens() {
        String texto = "";

        ArrayList<ItemVenda> lista = ControllerItemVenda.getInstance().retornarItens();
        for (ItemVenda item: lista) {
            texto += "Código: " + item.getCodigo() + "\nDescrição: " + item.getDescricao() + "\nValor: " + item.getValor() + "\n--------------------------------------------------------\n";
        }

        tvListaItens.setText(texto);
    }
}