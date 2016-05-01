package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rkrul.wieeebuddy.Event;
import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

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

    private String mParam1;

    private TextView name;
    private TextView description;
    private TextView date;
    private TextView location;
    private Button gpsattend;
    private Button interestattend;

    private String eventName;
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
    public static openEvent newInstance(Event param1) {
        openEvent fragment = new openEvent();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            passedEvent = (Event)getArguments().getSerializable(ARG_PARAM1);
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
        gpsattend = (Button)view.findViewById(R.id.openGPSbutton);
        interestattend = (Button)view.findViewById(R.id.openinterstbutton);
        return view;
    }

    @Override
    public void onViewCreated(final View view,Bundle savedInstanceState){
        Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/events");
        Query queryRef = ref.orderByChild("name");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Event newevent = dataSnapshot.getValue(Event.class);
                if (newevent.getName().equals(passedEvent.getName())){
                    name.setText(passedEvent.getName());
                    description.setText("Description: "+passedEvent.getDescription());
                    location.setText("Location: "+passedEvent.getLocation());
                    date.setText("Time: "+passedEvent.getDate()+"   "+passedEvent.getStartTime()+" - "+passedEvent.getEndTime());
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
