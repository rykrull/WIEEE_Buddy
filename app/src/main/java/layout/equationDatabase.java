package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rkrul.wieeebuddy.R;
import com.firebase.client.Firebase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link equationDatabase.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link equationDatabase#newInstance} factory method to
 * create an instance of this fragment.
 */
public class equationDatabase extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private ListView eqList;
    private static ArrayList<String> classNameList;
    private ArrayAdapter<String> adapter;
    public equationDatabase() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment equation_database.
     */
    public static equationDatabase newInstance() {
        equationDatabase fragment = new equationDatabase();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        classNameList = new ArrayList<>();
        classNameList.add("ECE 230");
        classNameList.add("ECE 330");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Firebase.setAndroidContext(getActivity());
        View view = inflater.inflate(R.layout.fragment_equation_database, container, false);
        eqList = (ListView)view.findViewById(R.id.listView3);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_items,classNameList);
        eqList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
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
