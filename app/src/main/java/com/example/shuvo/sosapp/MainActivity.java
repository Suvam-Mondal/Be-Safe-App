package com.example.shuvo.sosapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {

    Button setup;
    ImageButton sos;
    Intent sendIntent;
    LocationManager locationManager;
    Location loc;
    double longitude, latitude;
    boolean checkGPS = false;
    boolean checkNetworkProvider = false;
    List<Address> address;
    String postalcode="",locality="",thouroghfare="",admin="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup = (Button) findViewById(R.id.button);
        sos = (ImageButton) findViewById(R.id.imageButton);
        setup.setOnClickListener(this);
        sos.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (setup.equals(v)) {
            startActivity((new Intent(MainActivity.this, Setup.class)));
        }
        if (sos.equals(v)) {



            locationManager = (LocationManager) MainActivity.this.getSystemService(LOCATION_SERVICE);

            checkGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            checkNetworkProvider = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


            if (!checkGPS) {
                Toast.makeText(MainActivity.this, "GPS not enabled", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));


            } else {
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
                LocationListener ll = new MainActivity();
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 35000, 0, ll);

                if (locationManager != null) {
                    loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                }
                if (loc!=null) {
                longitude = loc.getLongitude();
                latitude = loc.getLatitude();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    address = geocoder.getFromLocation(latitude,longitude,1);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (address!=null && address.size()!=0)
                {

                    postalcode = address.get(0).getPostalCode();
                    locality = address.get(0).getLocality();    //Howrah
                    thouroghfare = address.get(0).getThoroughfare();    //  Kalikundu Lane
                    admin = address.get(0).getAdminArea();      //WestBengal
                    /*Toast.makeText(MainActivity.this, "Postal Code: "+postalcode
                            +"\nThoroughfare: "+thouroghfare
                            +"\nLocality: "+locality
                            +"\nAdmin: "+admin
                            +"\nLongitude: "+longitude
                            +"\nLatitude: "+latitude, Toast.LENGTH_SHORT).show();*/

                    Intent sendac = new Intent(MainActivity.this,Send.class);
                    sendac.putExtra("postal",postalcode);
                    sendac.putExtra("thoroughfare",thouroghfare);
                    sendac.putExtra("locality",locality);
                    sendac.putExtra("admin",admin);
                    sendac.putExtra("longitude",longitude);
                    sendac.putExtra("latitude",latitude);
                    startActivity(sendac);
                }
            }
        }



        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
