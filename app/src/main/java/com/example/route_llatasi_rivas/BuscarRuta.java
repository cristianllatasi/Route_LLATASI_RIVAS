package com.example.route_llatasi_rivas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class BuscarRuta extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mapa;
    Button btnmostrar;
    Button btnencontrar;
    EditText txtdesde;
    EditText txthasta;
    String hasta,desde;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_ruta);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnmostrar = (Button) findViewById(R.id.btnmostrarruta);

        btnmostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Principal.class);
                startActivity(intent);
            }
        });

        txtdesde=(EditText)findViewById(R.id.txtdesde);
        txthasta=(EditText)findViewById(R.id.txthasta);

        btnencontrar=(Button) findViewById(R.id.btnencontrar);
        btnencontrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(desde.equals("")&&hasta.equals("")){
                    Toast.makeText(getApplicationContext(),"Indique un punto de inicio y punto final",Toast.LENGTH_SHORT);
                }
                else {
                    Intent intent=new Intent(getApplicationContext(),EncontrarActivity.class);
                    intent.putExtra("desde",desde);
                    intent.putExtra("hasta",hasta);
                    startActivity(intent);

                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapa = googleMap;
        mapa.clear();

        LatLng lugar = new LatLng(-18.0038755, -70.225904);

        mapa.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.moveCamera(CameraUpdateFactory.zoomTo(14));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BuscarRuta.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        mapa.setMyLocationEnabled(true);




        mapa.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapa.addMarker(new MarkerOptions().position(latLng).title("Marker 1"));
                mapa.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                double latitud=latLng.latitude;
                double longitud=latLng.longitude;
                Geocoder geo=new Geocoder(BuscarRuta.this.getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses=geo.getFromLocation(latitud,longitud,1);
                    String[]calle=addresses.get(0).getAddressLine(0).split(",");
                    txtdesde.setText(calle[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                desde=latitud+","+longitud;


            }
        });
        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mapa.addMarker(new MarkerOptions().position(latLng).title("Marker 2"));
                mapa.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                double latitud=latLng.latitude;
                double longitud=latLng.longitude;
                Geocoder geo=new Geocoder(BuscarRuta.this.getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses=geo.getFromLocation(latitud,longitud,1);
                    String[]calle=addresses.get(0).getAddressLine(0).split(",");
                    txthasta.setText(calle[0]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                hasta=latitud+","+longitud;
            }
        });
    }
}
