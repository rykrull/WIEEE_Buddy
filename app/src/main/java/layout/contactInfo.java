package layout;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.rkrul.wieeebuddy.Officers;
import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;


/**
 * Created by JohnB on 5/1/2016.
 */
public class contactInfo extends Fragment {

    private ListView contact;
    private static ArrayList<Officers> contactlist;
    private static ArrayList<String> contactStringlist;
    private static ArrayAdapter<String> contactArrayAdapter;

    public contactInfo() {
        // Required empty public constructor
    }

    public static contactInfo newInstance() {
        contactInfo fragment = new contactInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        contactlist = new ArrayList<>();
        contactStringlist = new ArrayList<>();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_info, container, false);
        contact = (ListView) view.findViewById(R.id.contacts);
        Firebase.setAndroidContext(getActivity());
        contactArrayAdapter = new ArrayAdapter<>(getActivity(),R.layout.list_items,contactStringlist);
        contact.setAdapter(contactArrayAdapter);
        contactArrayAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/Officers");
        Query queryRef = ref.orderByChild("Name");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Officers newofficer = dataSnapshot.getValue(Officers.class);
                contactlist.add(newofficer);
                contactStringlist.add(newofficer.toString());
                contactArrayAdapter.notifyDataSetChanged();
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
        contact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long arg3) {
                view.setSelected(true);
                new AlertDialog.Builder(getActivity())
                        .setMessage("Would you like to send " + contactlist.get(position).getName()
                        + " an email?")
                        .setIcon(R.drawable.wieee_logo)
                        .setTitle("Contact Officer")
                        .setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.putExtra(Intent.EXTRA_EMAIL, new String[]{contactlist.get(position).getEmail()});
                                email.putExtra(Intent.EXTRA_SUBJECT, "[WIEEE BUDDY] Subject");
                                email.putExtra(Intent.EXTRA_TEXT, "body");
                                email.setType("message/rfc882");
                                startActivity(Intent.createChooser(email,"Choose email client."));
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
        });
    }

}
