package com.example.route_llatasi_rivas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import model.Linea;


// video referencia https://www.youtube.com/watch?v=vpObpZ5MYSE


public class Principal extends AppCompatActivity {

    DatabaseReference reference;
    RecyclerView recyclerView;
    List<Linea> lineas;
    Adapter adapter;


    Button btnbuscar;
    public static String ruta="";
    public static String nombre="";
    public static String horario="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        btnbuscar=(Button)findViewById(R.id.btnbuscartura) ;


        //Recycler View

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        lineas = new ArrayList<Linea>();





        reference = FirebaseDatabase.getInstance().getReference().child("Lineas");


        adapter=new Adapter(Principal.this,lineas);
        recyclerView.setAdapter(adapter);


        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                ruta="";
                lineas.removeAll(lineas);
            //    lineas.removeAll(lineas);

             //   for (DataSnapshot snapshot:
                for( DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                       // dataSnapshot.getChildren()){
                    Linea p = dataSnapshot1.getValue(Linea.class);
                       // Linea linea = snapshot.getValue(Linea.class);
                        lineas.add(p);

                }
                for(int i=0;i<lineas.size();i++){
                    if(i==lineas.size()-1){
                        nombre=nombre+lineas.get(i).getNombre().toString();
                        ruta=ruta+lineas.get(i).getRuta().toString();
                        horario=horario+lineas.get(i).getHorario().toString();
                    }else{
                        nombre=nombre+lineas.get(i).getNombre().toString()+"/";
                        ruta=ruta+lineas.get(i).getRuta().toString()+"¡";
                        horario=horario+lineas.get(i).getHorario().toString()+"/";
                    }

                }

                adapter.notifyDataSetChanged();




              //  adapter = new Adapter(Principal.this,lineas);
             //
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Toast.makeText(Principal.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),BuscarRuta.class);
                startActivity(intent);
            }
        });






    }



}