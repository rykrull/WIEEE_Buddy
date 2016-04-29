package layout;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by JohnB on 4/29/2016.
 */
public class manageAccount extends Fragment {

    private Button resetPassword;
    private Button resetEmail;

    private TextView erCurrentEmail;
    private TextView erPassword;
    private TextView erNewEmail;
    private TextView erNewEmailConfirm;

    private TextView prEmail;
    private TextView prOldPassword;
    private TextView prNewPassword;
    private TextView prConfirmNewPassword;

    public manageAccount() {
        // Required empty public constructor
    }

    public static manageAccount newInstance() {
        manageAccount fragment = new manageAccount();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);
        resetPassword = (Button)view.findViewById(R.id.resetPassword);
        resetEmail = (Button)view.findViewById(R.id.resetEmail);

        erCurrentEmail = (TextView)view.findViewById(R.id.erCurrentEmail);
        erPassword = (TextView)view.findViewById(R.id.erPassword);
        erNewEmail = (TextView)view.findViewById(R.id.erNewEmail);
        erNewEmailConfirm = (TextView)view.findViewById(R.id.erNewEmailConfirm);

        prEmail = (TextView)view.findViewById(R.id.prEmail);
        prOldPassword = (TextView)view.findViewById(R.id.prOldPassword);
        prNewPassword = (TextView)view.findViewById(R.id.prNewPassword);
        prConfirmNewPassword = (TextView)view.findViewById(R.id.prConfirmNewPassword);

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
    }
}
