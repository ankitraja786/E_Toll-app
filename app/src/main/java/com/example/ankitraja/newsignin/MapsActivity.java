package com.example.nitish235.newsignin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.util.List;

public class MapsActivity<location> extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    Button pay_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //mMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }
    LatLng MyLoc = new LatLng(23.812925399999997, 86.4427391);
    Double distance=100.00;

    List<Address> addressList = null;
    public void onSearch(View view)
    {
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();

        if(location != null || !location.equals(""))
        {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location , 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);
            LatLng latLng2 = new LatLng(address.getLatitude() , address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng2).title("Marker"));
            //
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng2));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng2,15));
            //

            //mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng2));
            distance = SphericalUtil.computeDistanceBetween(MyLoc, latLng2);
        }

    }
    public void onPress(View view)
    {
        Intent intent=new Intent(MapsActivity.this,Main4Activity.class);
        String var= String.valueOf(distance);
        intent.putExtra("Extra",var);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(MyLoc).title("My Location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLoc));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MyLoc,(float) 14.6));
        //mMap.animateCamera(CameraUpdateFactory.newLatLng(MyLoc));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //mMap.setMyLocationEnabled(true);
    }
}
/*
LatLng startLatLng = new LatLng(startLatitude, startLongitude);
LatLng endLatLng = new LatLng(endLatitude, endLongitude)
double distance = SphericalUtil.computeDistanceBetween(startLatLng, endLatLng);
 */