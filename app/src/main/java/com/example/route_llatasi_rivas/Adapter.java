package com.example.route_llatasi_rivas;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import model.Linea;

public class Adapter extends RecyclerView.Adapter <Adapter.LineasViewHolder>{

  Context context;
  List<Linea> lineas;
  String ruta="";

  public Adapter(Context c , List<Linea> p){

      context= c;
      lineas=p;
  }



    @Override
    public LineasViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
      View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler,parent,false);
      LineasViewHolder holder=new LineasViewHolder(v);
      return holder;

    }

    @Override
    public void onBindViewHolder(LineasViewHolder holder, int position) {
      holder.nombre.setText(lineas.get(position).getNombre());
      holder.horario.setText(lineas.get(position).getHorario());
      holder.ruta=(lineas.get(position).getRuta());

      ruta=(lineas.get(position).getRuta()).toString();


    }

    @Override
    public int getItemCount() {
        return lineas.size();
    }


    class LineasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        TextView nombre,horario;
        String ruta;

        public LineasViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            nombre = (TextView) itemView.findViewById(R.id.txtnombre);
            horario = (TextView) itemView.findViewById(R.id.txthorario);

}
        @Override
        public void onClick(View v) {
            //Toast.makeText(v.getContext(),ruta.toString(),Toast.LENGTH_SHORT).show();

            openActivity();

        }
        public void openActivity(){
            Intent intent = new Intent(context.getApplicationContext(), MapsActivity.class);
            intent.putExtra("variable_ruta", ruta);

            context.startActivity(intent);
        }
    }
}
