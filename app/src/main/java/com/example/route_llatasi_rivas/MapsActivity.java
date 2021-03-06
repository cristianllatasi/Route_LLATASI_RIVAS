package com.example.route_llatasi_rivas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String ruta="";
    String desde;
    String hasta;
    //Double[] LtdLng = new Double[2];
    ProgressDialog progressDialog;
    int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 0;
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        ruta = getIntent().getStringExtra("variable_ruta").toString();



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        progressDialog = ProgressDialog.show(this, "Cargando",
                "Espere...", true);

        new CountDownTimer(12000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                progressDialog.dismiss();
            }
        }.start();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng lugar = new LatLng(-18.0038755, -70.225904);
        mMap.addMarker(new MarkerOptions().position(lugar).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        mMap.setMyLocationEnabled(true);

        String[] puntos=ruta.split("/");
        for(int i=0;i<puntos.length;i++){
            if(i==puntos.length-1){
                desde=puntos[i].toString();
                hasta=puntos[0].toString();
            }else{
                desde=puntos[i].toString();
                hasta=puntos[i+1].toString();
            }
            new RutaMapa(MapsActivity.this,mMap,desde,hasta).execute();
        }
    }
}
