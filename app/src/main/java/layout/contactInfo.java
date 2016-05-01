package layout;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.Firebase;


/**
 * Created by JohnB on 5/1/2016.
 */
public class contactInfo extends Fragment {

    private ListView contact;

    public contactInfo() {
        // Required empty public constructor
    }

    public static contactInfo newInstance() {
        contactInfo fragment = new contactInfo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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

        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {

    }

}
