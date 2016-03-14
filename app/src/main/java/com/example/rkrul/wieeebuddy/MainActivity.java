package com.example.rkrul.wieeebuddy;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import layout.createNewUser;
import layout.login;
import layout.signInUser;

public class MainActivity extends AppCompatActivity implements login.OnFragmentInteractionListener,
        signInUser.OnFragmentInteractionListener, createNewUser.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, login.newInstance())
                .addToBackStack(null)
                .commit();
    }

    //TODO login

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
