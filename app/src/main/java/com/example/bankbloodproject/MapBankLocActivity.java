package com.example.bankbloodproject;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bankbloodproject.Home.Patient.DonnerMapActivity;
import com.example.bankbloodproject.model.DonnerModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MapBankLocActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_bank_loc);
        reference = FirebaseDatabase.getInstance().getReference();
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
        LatLng bankloc = new LatLng(31.041787575339576,31.366258654743433);
        MarkerOptions options = new MarkerOptions();
        options.title("مستشفي السلام")
                .snippet("Blood Bank")
                // .draggable(true)
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_donner))
                // .snippet(childValue.getBloodGroup())
                .position(bankloc);
        mMap.addMarker(options);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(bankloc,16f);
        mMap.moveCamera(cameraUpdate);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latlng = marker.getPosition();
                double ln = latlng.longitude;
                double la = latlng.latitude;
                //show donner details.....
                AlertDialog.Builder builder = new AlertDialog.Builder(MapBankLocActivity.this);
                builder.setView(R.layout.donner_view);
                AlertDialog dialog = builder.show();
                ImageView img=dialog.findViewById(R.id.imageView);
                TextView name= dialog.findViewById(R.id.nametv);
                TextView phone= dialog.findViewById(R.id.phonetv);
                ImageView imvphone=dialog.findViewById(R.id.imvphone);
                //TextView city= dialog.findViewById(R.id.citytv);
                TextView blood= dialog.findViewById(R.id.bloodgrouptv);
                // TextView age= dialog.findViewById(R.id.agetv);
                img.setImageResource(R.drawable.splash_logo);
                imvphone.setImageResource(R.drawable.ic_phone_black_24dp);

                        name.setText("مستشفي السلام");
                        phone.setText("01094843253");
                imvphone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String number = ("tel: 01094843253");
                        Intent mIntent = new Intent(Intent.ACTION_CALL);
                        mIntent.setData(Uri.parse(number));
                         // Here, thisActivity is the current activity
                        if (ContextCompat.checkSelfPermission(MapBankLocActivity.this,
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(MapBankLocActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    1);

                            // MY_PERMISSIONS_REQUEST_CALL_PHONE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        } else {
                            //You already have permission
                            try {
                                MapBankLocActivity.this.startActivity(mIntent);
                            } catch(SecurityException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                        blood.setText("المنصوره بوابه الجلاء ");







                return false;
            }
        });


//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
