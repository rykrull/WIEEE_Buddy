package com.example.rkrul.wieeebuddy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebChromeClient;

import com.firebase.client.Firebase;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link pdfViewer.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link pdfViewer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pdfViewer extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ArrayList<String> urls;
    private static int classPosition;

    public pdfViewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment pdfViewer.
     */
    public static pdfViewer newInstance(int position) {
        pdfViewer fragment = new pdfViewer();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        classPosition = position;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

        urls = new ArrayList<String>();

        //Will be ECE 230 url
        urls.add("https://drive.google.com/file/d/0ByrsqOz3sOuDdGlCT0lmNUtBS3M/view?usp=sharing");
        // ECE 330 url
        urls.add("https://drive.google.com/file/d/0ByrsqOz3sOuDdGlCT0lmNUtBS3M/view?usp=sharing");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Firebase.setAndroidContext(getActivity());
        View view = inflater.inflate(R.layout.fragment_pdf_viewer, container, false);

        WebView mWebView = (WebView)view.findViewById(R.id.webViews);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        String url = urls.get(classPosition);
        mWebView.loadUrl(url);

        return view;
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
