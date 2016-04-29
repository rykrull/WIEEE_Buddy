package layout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.Main2Activity;
import com.example.rkrul.wieeebuddy.MainDirectory;
import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link signInUser.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link signInUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class signInUser extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button siResetButton;
    private Button sibutton;
    private EditText siusername;
    private EditText sipassword;

    private OnFragmentInteractionListener mListener;

    public signInUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment signInUser.
     */
    // TODO: Rename and change types and number of parameters
    public static signInUser newInstance() {
        signInUser fragment = new signInUser();
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
        View view = inflater.inflate(R.layout.fragment_sign_in_user, container, false);
        sibutton = (Button)view.findViewById(R.id.sibutton);
        siusername = (EditText)view.findViewById(R.id.siusername);
        sipassword = (EditText)view.findViewById(R.id.sipassword);
        siResetButton = (Button)view.findViewById(R.id.siResetButton);

        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //Login pressed
        sibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com");
                ref.authWithPassword(siusername.getText().toString()+"@firebase.com", sipassword.getText().toString(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        Intent newIntent = new Intent(getActivity(), Main2Activity.class);
                        startActivity(newIntent);
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(getActivity(), "Username/Password incorrect",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        siResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siusername.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please enter email in the email text field",
                            Toast.LENGTH_LONG).show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setMessage("By clicking yes, an email will " +
                                    "be sent to the current email with instructions on how to reset your password.")
                            .setIcon(R.drawable.wieee_logo)
                            .setTitle("RESET PASSWORD")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com");
                                    ref.resetPassword(siusername.getText().toString(), new Firebase.ResultHandler() {
                                        @Override
                                        public void onSuccess() {
                                            Toast.makeText(getActivity(), "Password email sent!",
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onError(FirebaseError firebaseError) {
                                            Toast.makeText(getActivity(), "Email is invalid or not connect to an account",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setCancelable(false)
                            .create()
                            .show();
                }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
