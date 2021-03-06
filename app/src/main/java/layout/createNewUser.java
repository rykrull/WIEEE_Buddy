package layout;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.Main2Activity;
import com.example.rkrul.wieeebuddy.R;
import com.example.rkrul.wieeebuddy.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link createNewUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link createNewUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class createNewUser extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Button cnubutton;
    private EditText cnufirstname;
    private EditText cnuemailconfirm;
    private EditText cnuusername;
    private EditText cnupassword;
    private EditText cnupasswordconfirm;
    private EditText studentID;

    private OnFragmentInteractionListener mListener;

    public createNewUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment createNewUser.
     */
    public static createNewUser newInstance() {
        createNewUser fragment = new createNewUser();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_new_user, container, false);
        Firebase.setAndroidContext(getActivity());
        cnubutton = (Button)view.findViewById(R.id.cnubutton);
        cnufirstname = (EditText)view.findViewById(R.id.cnufirstname);
        cnuemailconfirm = (EditText)view.findViewById(R.id.cnuemailconfirm);
        cnuusername = (EditText)view.findViewById(R.id.cnuusername);
        cnupassword = (EditText)view.findViewById(R.id.cnupassword);
        cnupasswordconfirm = (EditText)view.findViewById(R.id.cnupasswordconfirm);
        studentID = (EditText)view.findViewById(R.id.studentID);

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //Login pressed
        cnubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cnupassword.getText().toString().equals(cnupasswordconfirm.getText().toString())){
                    if(cnuemailconfirm.getText().toString().equals(cnuusername.getText().toString())) {
                        Firebase myFirebaseRef = new Firebase("https://wieeebuddy.firebaseio.com/");
                        myFirebaseRef.createUser(cnuusername.getText().toString(),
                                cnupassword.getText().toString(),
                                new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> result) {
                                Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                                ref.authWithPassword(cnuusername.getText().toString(),
                                        cnupasswordconfirm.getText().toString(), new Firebase.AuthResultHandler() {
                                            @Override
                                            public void onAuthenticated(AuthData authData) {
                                                Firebase newUserRef = new Firebase("https://wieeebuddy.firebaseio.com/");
                                                Firebase userRef = newUserRef.child("users").child(authData.getUid());
                                                ArrayList<String> tmp = new ArrayList<String>();
                                                tmp.add(" ");
                                                User user = new User(authData.getUid() ,cnuemailconfirm.getText().toString(),
                                                        cnufirstname.getText().toString(),
                                                        studentID.getText().toString(),
                                                        tmp, 0);
                                                userRef.setValue(user);
                                                Intent newIntent = new Intent(getActivity(), Main2Activity.class);
                                                newIntent.putExtra("user", user);
                                                newIntent.putExtra("authData", authData.getUid());
                                                startActivity(newIntent);
                                            }
                                            @Override
                                            public void onAuthenticationError(FirebaseError firebaseError) {
                                                // there was an error
                                            }
                                        });
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(getActivity(), "Email is invalid or already has an account",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Emails do not match",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Passwords do not match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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
        void onFragmentInteraction(Uri uri);
    }
}
