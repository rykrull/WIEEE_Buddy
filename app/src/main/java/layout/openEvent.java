package layout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.Event;
import com.example.rkrul.wieeebuddy.Main2Activity;
import com.example.rkrul.wieeebuddy.R;
import com.example.rkrul.wieeebuddy.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link openEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link openEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class openEvent extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;

    private TextView name;
    private TextView description;
    private TextView date;
    private TextView location;
    private TextView time;
    private TextView interest;
    private Button gpsattend;
    private Button interestattend;
    private LocationManager locationManager;
    private LocationListener locationListener;
    //private Location locationGPS;

    private double latitude;
    private double longitude;
    private double[] latlong;
    private double event_latitude = 43.07;
    private double event_longitude = -89.41;
    private boolean checkedIn;

    private User user;
    private Event passedEvent;

    private OnFragmentInteractionListener mListener;


    public openEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment openEvent.
     */
    public static openEvent newInstance(Event param1, User param2) {
        openEvent fragment = new openEvent();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            passedEvent = (Event)getArguments().getSerializable(ARG_PARAM1);
            user = (User)getArguments().getSerializable(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open_event, container, false);
        name = (TextView)view.findViewById(R.id.openname);
        description = (TextView)view.findViewById(R.id.opendescription);
        location = (TextView)view.findViewById(R.id.openlocation);
        date = (TextView)view.findViewById(R.id.opendate);
        time = (TextView)view.findViewById(R.id.opentime);
        interest = (TextView)view.findViewById(R.id.openinterest);
        gpsattend = (Button)view.findViewById(R.id.openGPSbutton);
        interestattend = (Button)view.findViewById(R.id.openinterstbutton);
        if (passedEvent.getAttendees().contains(user.getFullName())){
            interestattend.setVisibility(View.GONE);
        }
        if(user.getEventsAttended().contains(passedEvent.getName())){
            gpsattend.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/events");
        Query queryRef = ref.orderByChild("name");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event newevent = dataSnapshot.getValue(Event.class);
                if (newevent.getName().equals(passedEvent.getName())) {
                    name.setText(passedEvent.getName());
                    description.setText("Description: "+passedEvent.getDescription());
                    location.setText("Location: "+passedEvent.getLocation());
                    date.setText("Date: "+passedEvent.getDate());
                    time.setText("Time: "+passedEvent.getStartTime()+" - "+passedEvent.getEndTime());
                    if(passedEvent.numAttendees() == 1){
                        interest.setText(passedEvent.numAttendees()+" person interested");
                    }
                    else {
                        interest.setText(passedEvent.numAttendees() + " people interested");
                    }
                }
            }

            @Override
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

        interestattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getFullName().equals("Freeloader")){
                    Toast.makeText(getActivity(), "Sign in or create account",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/events").child(passedEvent.getName());
                    passedEvent.addAttendees(user.getFullName());
                    ref.setValue(passedEvent);
                    interestattend.setVisibility(View.GONE);
                    if(passedEvent.numAttendees() == 1){
                        interest.setText(passedEvent.numAttendees()+" person interested");
                    }
                    else {
                        interest.setText(passedEvent.numAttendees() + " people interested");
                    }
                }

            }
        });



        /////////////////////////GPS///////////////////////////

        gpsattend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getFullName().equals("Freeloader")){
                    Toast.makeText(getActivity(), "Sign in or create account",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                latlong = ((Main2Activity)getActivity()).getLocation();
                latitude = latlong[0];
                longitude = latlong[1];

                if(latitude<passedEvent.getLatitude()+0.0005 && latitude> passedEvent.getLatitude()-0.0005){

                    if(longitude<passedEvent.getLongitude()+0.0005 && longitude> passedEvent.getLongitude()-0.0005){
                        checkedIn = true;
                        Firebase userRef = new Firebase("https://wieeebuddy.firebaseio.com/").child("users")
                                .child(user.getUserId()).child("eventsAttended");
                        ArrayList<String> tmp = user.getEventsAttended();
                        if(!tmp.contains(passedEvent.getName())){
                            tmp.add(passedEvent.getName());
                            user.setEventsAttended(tmp);
                            userRef.setValue(tmp);
                            Toast.makeText(getActivity(), "Attended Event!",
                                    Toast.LENGTH_SHORT).show();
                            gpsattend.setVisibility(View.GONE);
                        }
                        else{
                            checkedIn = false;
                            Toast.makeText(getActivity(), "Checked In Failed! Try Again",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        checkedIn = false;
                        Toast.makeText(getActivity(), "Checked In Failed! Try Again",
                                Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    checkedIn = false;
                    Toast.makeText(getActivity(), "Checked In Failed! Try Again",
                            Toast.LENGTH_SHORT).show();
                }

            }
            }
        });

        ///////////////////////////////////////////////////////


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
