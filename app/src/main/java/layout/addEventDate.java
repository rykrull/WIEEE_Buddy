package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rkrul.wieeebuddy.Event;
import com.example.rkrul.wieeebuddy.R;
import com.example.rkrul.wieeebuddy.User;
import com.firebase.client.Firebase;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addEventDate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addEventDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addEventDate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    private static final String ARG_PARAM5 = "param5";
    private static final String ARG_PARAM6 = "param6";

    // TODO: Rename and change types of parameters
    private String name;
    private String description;
    private int day;
    private int month;
    private int year;
    private String location;

    private Button create;
    private TimePicker timePicker;
    private Button start;
    private Button end;
    private TextView startTime;
    private TextView endTime;


    private OnFragmentInteractionListener mListener;

    public addEventDate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param eventName Parameter 1.
     * @param eventDescription Parameter 2.
     * @return A new instance of fragment addEventDate.
     */
    // TODO: Rename and change types and number of parameters
    public static addEventDate newInstance(String eventName, String eventDescription, int day, int month, int year, String location) {
        addEventDate fragment = new addEventDate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, eventName);
        args.putString(ARG_PARAM2, eventDescription);
        args.putInt(ARG_PARAM3, day);
        args.putInt(ARG_PARAM4, month);
        args.putInt(ARG_PARAM5,year);
        args.putString(ARG_PARAM6, location);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            description = getArguments().getString(ARG_PARAM2);
            day = getArguments().getInt(ARG_PARAM3);
            month = getArguments().getInt(ARG_PARAM4);
            year = getArguments().getInt(ARG_PARAM5);
            location = getArguments().getString(ARG_PARAM6);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_event_date, container, false);
        start = (Button)view.findViewById(R.id.starttimebutton);
        end = (Button)view.findViewById(R.id.endtimebutton);
        create = (Button)view.findViewById(R.id.createbutton);
        timePicker = (TimePicker)view.findViewById(R.id.timePicker);
        startTime = (TextView)view.findViewById(R.id.starttime);
        endTime = (TextView)view.findViewById(R.id.endtime);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String am = "AM";
                if (hour>12){
                    am = "PM";
                    hour = hour - 12;
                }
                if(minute < 10){
                    startTime.setText(hour + ":0" + minute + " " + am);
                }
                else {
                    startTime.setText(hour + ":" + minute + " " + am);
                }
            }
        });;

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                String am = "AM";
                if (hour > 12){
                    am = "PM";
                    hour = hour - 12;
                }
                if(minute < 10){
                    endTime.setText(hour + ":0" + minute + " " + am);
                }
                else {
                    endTime.setText(hour + ":" + minute + " " + am);
                }
            }
        });;

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                Firebase eventRef = ref.child("events").child(name);
                Event event = new Event(name,startTime.getText().toString(), endTime.getText().toString(), month+"/"+day+"/"+year, description, location);
                eventRef.setValue(event);
            }
        });;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
