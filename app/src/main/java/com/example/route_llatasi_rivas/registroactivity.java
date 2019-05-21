package com.example.route_llatasi_rivas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class registroactivity extends AppCompatActivity implements View.OnClickListener{

    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextNombre;
    private Button btnregistrar;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);
        setupActionBar();

        firebaseAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        TextEmail=(EditText)findViewById(R.id.txtemail);
        TextPassword=(EditText)findViewById(R.id.txtcontrasena);
        TextNombre=(EditText)findViewById(R.id.txtnombre);
        btnregistrar=(Button)findViewById(R.id.btnregistrar);
        progressDialog=new ProgressDialog(this);

        btnregistrar.setOnClickListener(this);


    }

    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null){

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Crear cuenta");
        }
    }
    private void registrarUsuario(){
        final String email=TextEmail.getText().toString().trim();
        final String password=TextPassword.getText().toString().trim();
        final String nombre=TextNombre.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getApplicationContext(),"Se debe ingresar un email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(getApplicationContext(),"falta ingresar la contraseña",Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(getApplicationContext(),"El password debe tener más de 6 caracteres",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(nombre)){
            Toast.makeText(getApplicationContext(),"Ingrese su nombre completo",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Map<String,Object> map=new HashMap<>();
                    map.put("name",nombre);
                    map.put("email",email);
                    map.put("password",password);

                    String id=firebaseAuth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map);
                    Toast.makeText(getApplicationContext(),"Se ha registrado el email",Toast.LENGTH_SHORT).show();

                    Intent intencion = new Intent(getApplication(), MainActivity.class);
                    startActivity(intencion);

                }else{
                    Toast.makeText(getApplicationContext(),"No se pudo registrar el usuario",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view){
        registrarUsuario();

    }
}

