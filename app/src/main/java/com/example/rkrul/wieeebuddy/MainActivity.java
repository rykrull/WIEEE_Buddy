package com.example.rkrul.wieeebuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import layout.createNewUser;
import layout.login;
import layout.signInUser;

public class MainActivity extends AppCompatActivity implements login.OnFragmentInteractionListener,
        signInUser.OnFragmentInteractionListener, createNewUser.OnFragmentInteractionListener{

    private String Uid;
    private boolean reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            if(extras.getBoolean("message")){
                SaveSharedPreference.setUserName(MainActivity.this, "");
            }
        }
        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            // call Login Activity
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment_container, login.newInstance())
                    .addToBackStack(null)
                    .commit();
        }
        else
        {
            // Stay at the current activity.
            Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/users");
            Query queryRef = ref.orderByChild("email");
            Uid = SaveSharedPreference.getUserName(MainActivity.this);
            queryRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    User curr = dataSnapshot.getValue(User.class);
                    if(curr.getUserId().equals(Uid)){
                        Intent newIntent = new Intent(MainActivity.this, Main2Activity.class);
                        newIntent.putExtra("user", curr);
                        newIntent.putExtra("authData", Uid);
                        startActivity(newIntent);
                    }
                }
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });

        }

    }

    //TODO login

    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
