package com.example.route_llatasi_rivas;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import model.Linea;

public class Adapter extends RecyclerView.Adapter <Adapter.LineasViewHolder>{

  Context context;
  ArrayList<Linea> lineas;

  public Adapter(Context c , ArrayList<Linea> p){

      context= c;
      lineas=p;
  }



    @Override
    public LineasViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
      return new LineasViewHolder(LayoutInflater.from(context).inflate(R.layout.row_recycler, parent, false));

    }

    @Override
    public void onBindViewHolder(LineasViewHolder holder, int position) {
      holder.nombre.setText(lineas.get(position).getNombre());
      holder.horario.setText(lineas.get(position).getHorario());


    }

    @Override
    public int getItemCount() {
        return lineas.size();
    }


    class LineasViewHolder extends RecyclerView.ViewHolder{


        TextView nombre,horario;

        public LineasViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.txtnombre);
            horario = (TextView) itemView.findViewById(R.id.txthorario);

}




    }
}
