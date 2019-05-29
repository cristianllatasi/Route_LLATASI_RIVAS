package com.example.route_llatasi_rivas;

import android.Manifest;
import android.content.pm.PackageManager;
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
    String[]ruta;
    Double[]LtdLng=new Double[2];
    int MY_PERMISSION_REQUEST_ACCESS_FINE_LOCATION=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        String []ruta=getIntent().getStringExtra("variable_ruta").split(",");
        LtdLng[0]=Double.parseDouble(ruta[0]);
        LtdLng[1]=Double.parseDouble(ruta[1]);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

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
        LatLng lugar = new LatLng(LtdLng[0], LtdLng[1]);
        mMap.addMarker(new MarkerOptions().position(lugar).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lugar));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));
    }
}
