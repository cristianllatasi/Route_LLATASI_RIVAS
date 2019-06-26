package com.example.route_llatasi_rivas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EncontrarActivity extends AppCompatActivity implements OnMapReadyCallback {

    String desde,hasta;
    String desderuta,hastaruta;
    TextView textView;
    TextView txthora;
    float distanciainicio=999999999;
    float distanciafinal=999999999;
    int combi;
    private GoogleMap mapa;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    String puntotomar,puntobajar;
    String[] puntosmascercanos;
    float[] sumadistanciaporruta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encontrar);




        textView=(TextView)findViewById(R.id.txtcombi);
        txthora=(TextView)findViewById(R.id.txthorario);

        desde = getIntent().getStringExtra("desde").toString();
        String[]desdeltdlng=desde.split(",");
        Location locationA=new Location("punto A");
        locationA.setLatitude(Double.parseDouble(desdeltdlng[0]));
        locationA.setLongitude(Double.parseDouble(desdeltdlng[1]));

        hasta = getIntent().getStringExtra("hasta").toString();
        String[]hastaltdlng=hasta.split(",");
        Location locationC=new Location("punto C");
        locationC.setLatitude(Double.parseDouble(hastaltdlng[0]));
        locationC.setLongitude(Double.parseDouble(hastaltdlng[1]));

        String[] rutas=Principal.ruta.split("¡");

        sumadistanciaporruta=new float[rutas.length];
        puntosmascercanos=new String[rutas.length];

        Location locationB=new Location("punto B");
        for(int i=0;i<rutas.length;i++){
            float suma=0;
            distanciainicio=999999999;
            distanciafinal=999999999;
            String [] puntos=rutas[i].split("/");
            for(int j=0;j<puntos.length;j++){
                String[] latlng=puntos[j].split(",");
                locationB.setLatitude(Double.parseDouble(latlng[0]));
                locationB.setLongitude(Double.parseDouble(latlng[1]));
                float distanceinicio=locationA.distanceTo(locationB);
                if(distanceinicio<distanciainicio){
                    distanciainicio=distanceinicio;
                    //puntotomar=latlng[0]+","+latlng[1];
                }
                float distancefinal=locationC.distanceTo(locationB);
                if(distancefinal<distanciafinal){
                    distanciafinal=distancefinal;
                    //puntobajar=latlng[0]+","+latlng[1];
                }

            }

            suma=distanciainicio+distanciafinal;
            sumadistanciaporruta[i]=suma;

            puntosmascercanos[i]=puntotomar+"/"+puntobajar;



        }
        float maximo=99999999;
        int combi=0;
        for(int i=0;i<sumadistanciaporruta.length;i++){
            if(sumadistanciaporruta[i]<maximo){
                maximo=sumadistanciaporruta[i];
                combi=i;
            }
        }
        float inicio2=99999999;
        float final2=99999999;
        String [] puntos2=rutas[combi].split("/");
        for(int i=0;i<puntos2.length;i++){
            String[] latlng2=puntos2[i].split(",");
            locationB.setLatitude(Double.parseDouble(latlng2[0]));
            locationB.setLongitude(Double.parseDouble(latlng2[1]));
            float distanceinicio=locationA.distanceTo(locationB);
            if(distanceinicio<inicio2){
                inicio2=distanceinicio;
                puntotomar=latlng2[0]+","+latlng2[1];
            }
            float distancefinal=locationC.distanceTo(locationB);
            if(distancefinal<final2){
                final2=distancefinal;
                puntobajar=latlng2[0]+","+latlng2[1];
            }
        }


        String[]nombres=Principal.nombre.split("/");
        textView.setText(nombres[combi]);
        String[]horario=Principal.horario.split("/");
        txthora.setText(horario[combi]);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng lugar = new LatLng(-18.0038755, -70.225904);
        mapa.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        mapa.moveCamera(CameraUpdateFactory.zoomTo(14));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EncontrarActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        mapa.setMyLocationEnabled(true);
        float maximo=99999999;
        int combi=0;
        for(int i=0;i<sumadistanciaporruta.length;i++){
            if(sumadistanciaporruta[i]<maximo){
                maximo=sumadistanciaporruta[i];
                combi=i;
            }
        }

        String[] rutas=Principal.ruta.split("¡");

        String[] puntos=rutas[combi].split("/");
        for(int i=0;i<puntos.length;i++){
            if(i==puntos.length-1){
                desderuta=puntos[i].toString();
                hastaruta=puntos[0].toString();
            }else{
                desderuta=puntos[i].toString();
                hastaruta=puntos[i+1].toString();
            }
            new RutaMapa(EncontrarActivity.this,mapa,desderuta,hastaruta).execute();
        }

        //String[] puntoscercanos=puntosmascercanos[combi].split("/");


            //desderuta=puntoscercanos[0];
            //hastaruta=puntoscercanos[1];
            new RutaMapaIndicar(EncontrarActivity.this,mapa,desde,puntotomar).execute();
        new RutaMapaIndicar(EncontrarActivity.this,mapa,hasta,puntobajar).execute();


    }
}
