package com.example.rkrul.wieeebuddy;

import android.Manifest;
import android.app.Fragment;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import layout.addEventDate;
import layout.addEventDay;
import layout.addNewEvent;
import layout.contactInfo;
import layout.eventsList;
import layout.manageAccount;
import layout.openEvent;
import layout.projectsList;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, addNewEvent.OnFragmentInteractionListener,
        addEventDay.OnFragmentInteractionListener, addEventDate.OnFragmentInteractionListener,
        eventsList.OnFragmentInteractionListener, openEvent.OnFragmentInteractionListener,
        projectsList.OnFragmentInteractionListener{

    private User user;
    private String Uid;
    private TextView name;
    private TextView email;

    //location
    protected static final int SECONDS_IN_mS = 1000;
    protected static final int GPS_MIN_DIST_IN_METERS = 100;
    protected static final int GPS_MIN_TIME_IN_MILLISEC = 100*SECONDS_IN_mS;
    LocationManager locationManager;
    protected static final int GPS_FAIL_LONG_LAT = 360;
    double latitude;
    double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);

        user = (User)getIntent().getSerializableExtra("user");
        Uid = (String)getIntent().getSerializableExtra("authData");

        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.inflateHeaderView(R.layout.nav_header_main2);
        name = (TextView) header.findViewById(R.id.usersName);
        email = (TextView) header.findViewById(R.id.email);
        name.setText(user.getFullName());
        email.setText(user.getEmail());

        // Location stuff
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
                return;
            } else {

            }
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main2container, eventsList.newInstance())
                .addToBackStack(null)
                .commit();
    }

    /**prompts user for permission if the user has API 23 or higher*/
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED)
                    //configureButton()
                    ;
            case 22:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //configureButton()
                    ;

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
        }
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
        return;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Fragment curr = this.getFragmentManager().findFragmentById(R.id.main2container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(curr instanceof eventsList) {
            Intent newIntent = new Intent(this, MainActivity.class);
            startActivity(newIntent);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.createEvent) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main2container, addNewEvent.newInstance())
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        if (id == R.id.GPS) {
            //requests access from user only is API 23 or higher
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET}, 10);
                }
            }
            //make sure location is turned ON on your phone
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(Main2Activity.this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
            } else {
                showGPSDisabledAlertToUser();
            }
            //reads location and makes sure it isn't null
            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), true));
            if (location != null ) {
                //this means the party is private and location doesn't matter
                if (location == null) {
                    longitude = GPS_FAIL_LONG_LAT;
                    latitude = GPS_FAIL_LONG_LAT;
                    System.out.print(latitude);
                    System.out.print(longitude);
                    Toast.makeText(Main2Activity.this, "GPS coordinates not found", Toast.LENGTH_SHORT).show();
                }
                //this means we need to use the GPS data
                else {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    Toast.makeText(Main2Activity.this, "Latitude: "+latitude+"\nlongitude: "+longitude, Toast.LENGTH_SHORT).show();
                }


            } else {
                //this means location is null
                Toast.makeText(Main2Activity.this, "GPS location not ready yet; this may take a few seconds...", Toast.LENGTH_SHORT).show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_calender) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main2container, eventsList.newInstance())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_gallery) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main2container, projectsList.newInstance())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main2container, manageAccount.newInstance(user))
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.nav_logout) {
            Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
            ref.unauth();
            Intent newIntent = new Intent(this, MainActivity.class);
            startActivity(newIntent);
            
        } else if (id == R.id.nav_share) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main2container, contactInfo.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public double[] getLocation(){
        //requests access from user only is API 23 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET}, 10);
            }
        }
        //make sure location is turned ON on your phone
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(Main2Activity.this, "GPS is Enabled in your device", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }
        //reads location and makes sure it isn't null
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(new Criteria(), true));
        if (location != null ) {
            //this means the party is private and location doesn't matter
            if (location == null) {
                longitude = GPS_FAIL_LONG_LAT;
                latitude = GPS_FAIL_LONG_LAT;
                System.out.print(latitude);
                System.out.print(longitude);
                Toast.makeText(Main2Activity.this, "GPS coordinates not found", Toast.LENGTH_SHORT).show();
            }
            //this means we need to use the GPS data
            else {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(Main2Activity.this, "Latitude: "+latitude+"\nlongitude: "+longitude, Toast.LENGTH_SHORT).show();
            }


        } else {
            //this means location is null
            Toast.makeText(Main2Activity.this, "GPS location not ready yet; this may take a few seconds...", Toast.LENGTH_SHORT).show();
        }
        double[] llocation = {latitude,longitude};
        return llocation;
    }


    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }


}
