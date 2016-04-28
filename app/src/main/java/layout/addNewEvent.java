package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link addNewEvent.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link addNewEvent#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addNewEvent extends Fragment {
    private EditText name;
    private EditText description;
    private Button next;

    private OnFragmentInteractionListener mListener;

    public addNewEvent() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment addNewEvent.
     */
    // TODO: Rename and change types and number of parameters
    public static addNewEvent newInstance() {
        addNewEvent fragment = new addNewEvent();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_new_event, container, false);
        name = (EditText)view.findViewById(R.id.eventName);
        description = (EditText)view.findViewById(R.id.eventDescription);
        next = (Button)view.findViewById(R.id.neweventbutton);
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Please enter event name",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main2container, addEventDay.newInstance(
                                    name.getText().toString(),description.getText().toString()))
                            .addToBackStack(null)
                            .commit();
                }
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
