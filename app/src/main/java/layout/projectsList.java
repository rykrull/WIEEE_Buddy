package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rkrul.wieeebuddy.MyCustomAdapter;
import com.example.rkrul.wieeebuddy.Project;
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
 * {@link projectsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link projectsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class projectsList extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";

    private OnFragmentInteractionListener mListener;

    private ListView plist;
    private static ArrayList<String> prolist;
    private static ArrayList<Project> proProlist;
    private static ArrayAdapter<String> proAdapter;
    private static MyCustomAdapter adapter;

    private User user;

    public projectsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment projectsList.
     */
    public static projectsList newInstance(User param1) {
        projectsList fragment = new projectsList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User)getArguments().getSerializable(ARG_PARAM1);
        }
        prolist = new ArrayList<>();
        proProlist = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Firebase.setAndroidContext(getActivity());
        View view = inflater.inflate(R.layout.fragment_projects_list, container, false);
        plist = (ListView)view.findViewById(R.id.listView2);
        adapter = new MyCustomAdapter(prolist,proProlist,user,getActivity());
        proAdapter = new ArrayAdapter<>(getActivity(),R.layout.list_items,prolist);
        plist.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        plist.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
            }
        });
        Firebase ref = new Firebase("https://wieeebuddy.firebaseio.com/projects");
        Query queryRef = ref.orderByChild("date");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Project newproject = dataSnapshot.getValue(Project.class);
                proProlist.add(newproject);
                prolist.add(newproject.toString());
                adapter.notifyDataSetChanged();
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
