package com.example.route_llatasi_rivas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MostrarRutas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrarruta);

        String ruta=getIntent().getStringExtra("variable_ruta");

        TextView txtruta=(TextView) findViewById(R.id.ruta);

        txtruta.setText(ruta);



    }

}
