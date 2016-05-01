package layout;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.R;
import com.example.rkrul.wieeebuddy.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.Serializable;

/**
 * Created by JohnB on 4/29/2016.
 */
public class manageAccount extends Fragment {

    private Button resetPassword;
    private Button resetEmail;
    private Button updateID;
    private Button updateName;

    private TextView erCurrentEmail;
    private TextView erPassword;
    private TextView erNewEmail;
    private TextView erNewEmailConfirm;

    private TextView prEmail;
    private TextView prOldPassword;
    private TextView prNewPassword;
    private TextView prConfirmNewPassword;

    private TextView idEmail;
    private TextView idPassword;
    private TextView newID;

    private TextView nEmail;
    private TextView nPassword;
    private TextView newName;

    private User user;

    private static final String ARG_PARAM1 = "param1";

    public manageAccount() {
        // Required empty public constructor
    }

    public static manageAccount newInstance(User param1) {
        manageAccount fragment = new manageAccount();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(getArguments() != null){
            user = (User)getArguments().getSerializable(ARG_PARAM1);
        }
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        resetPassword = (Button)view.findViewById(R.id.resetPassword);
        resetEmail = (Button)view.findViewById(R.id.resetEmail);
        updateID = (Button)view.findViewById(R.id.idUpdate);
        updateName = (Button)view.findViewById(R.id.updateName);

        erCurrentEmail = (TextView)view.findViewById(R.id.erCurrentEmail);
        erPassword = (TextView)view.findViewById(R.id.erPassword);
        erNewEmail = (TextView)view.findViewById(R.id.erNewEmail);
        erNewEmailConfirm = (TextView)view.findViewById(R.id.erNewEmailConfirm);

        prEmail = (TextView)view.findViewById(R.id.prEmail);
        prOldPassword = (TextView)view.findViewById(R.id.prOldPassword);
        prNewPassword = (TextView)view.findViewById(R.id.prNewPassword);
        prConfirmNewPassword = (TextView)view.findViewById(R.id.prConfirmNewPassword);

        nEmail = (TextView)view.findViewById(R.id.nEmail);
        nPassword = (TextView)view.findViewById(R.id.nPassword);
        newName = (TextView)view.findViewById(R.id.newName);

        idEmail = (TextView)view.findViewById(R.id.idEmail);
        idPassword = (TextView)view.findViewById(R.id.idPassword);
        newID = (TextView)view.findViewById(R.id.idNewID);



        return view;
    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prNewPassword.getText().toString().equals(prConfirmNewPassword.getText().toString())) {
                    Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                    ref.changePassword(prEmail.getText().toString(),
                            prOldPassword.getText().toString(),
                            prNewPassword.getText().toString(),
                            new Firebase.ResultHandler() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getActivity(), "Password Successfully Reset!",
                                            Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    Toast.makeText(getActivity(), "Username/Old Password incorrect",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                } else{
                    Toast.makeText(getActivity(), "New passwords don't match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (erNewEmail.getText().toString().equals(erNewEmailConfirm.getText().toString())) {
                    Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                    ref.changeEmail(erCurrentEmail.getText().toString(),
                            erPassword.getText().toString(),
                            erNewEmail.getText().toString(),
                            new Firebase.ResultHandler() {
                        @Override
                        public void onSuccess() {
                            Firebase userRef = new Firebase("https://wieeebuddy.firebaseio.com/")
                                    .child("users").child(user.getUserId()).child("email");
                            userRef.setValue(erNewEmail.getText().toString());
                            Toast.makeText(getActivity(), "Email Successfully Updated!",
                                    Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(FirebaseError firebaseError) {
                            Toast.makeText(getActivity(), "Old Username/Password incorrect",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    Toast.makeText(getActivity(), "New Emails don't match",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                ref.authWithPassword(nEmail.getText().toString(),
                        nPassword.getText().toString(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Firebase userRef = new Firebase("https://wieeebuddy.firebaseio.com/")
                                        .child("users").child(user.getUserId()).child("fullName");
                                userRef.setValue(newName.getText().toString());
                                Toast.makeText(getActivity(), "Name Updated!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(getActivity(), "Username/Password incorrect",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        updateID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/");
                ref.authWithPassword(idEmail.getText().toString(),
                        idPassword.getText().toString(),
                        new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {
                                Firebase userRef = new Firebase("https://wieeebuddy.firebaseio.com/")
                                        .child("users").child(user.getUserId()).child("uwId");
                                userRef.setValue(newID.getText().toString());
                                Toast.makeText(getActivity(), "UW ID Updated!",
                                        Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {
                                Toast.makeText(getActivity(), "Username/Password incorrect",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
