package com.example.rkrul.wieeebuddy;

import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main2container, eventsList.newInstance())
                .addToBackStack(null)
                .commit();
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

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
