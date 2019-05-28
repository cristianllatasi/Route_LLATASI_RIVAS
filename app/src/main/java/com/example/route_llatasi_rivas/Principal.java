package com.example.route_llatasi_rivas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
    ArrayList<Linea> lineas;
    Adapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);




        //Recycler View

        recyclerView = (RecyclerView) findViewById(R.id.myRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        reference = FirebaseDatabase.getInstance().getReference().child("Lineas");


        lineas = new ArrayList<Linea>();



        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
            //    lineas.removeAll(lineas);

             //   for (DataSnapshot snapshot:
                for( DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                       // dataSnapshot.getChildren()){
                    Linea p = dataSnapshot1.getValue(Linea.class);
                       // Linea linea = snapshot.getValue(Linea.class);
                        lineas.add(p);

                }


                    adapter = new Adapter(Principal.this,lineas);
                      recyclerView.setAdapter(adapter);

              //  adapter = new Adapter(Principal.this,lineas);
             //
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Toast.makeText(Principal.this, "Se produjo un error", Toast.LENGTH_SHORT).show();
            }
        });

        Button cerrarsesion=(Button) findViewById(R.id.CerrarSesion);
        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });


    }



}