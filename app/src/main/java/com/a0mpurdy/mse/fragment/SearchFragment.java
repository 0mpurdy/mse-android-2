package com.a0mpurdy.mse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.a0mpurdy.mse.hymn.AssetManagerWrapper;
import com.a0mpurdy.mse.hymn.HymnBookCache;

import com.a0mpurdy.mse.R;
import com.a0mpurdy.mse.hymn.ObjectStreamer;
import com.a0mpurdy.mse.search.HymnSearchThread;
import com.a0mpurdy.mse.search.TokenHelper;
import com.a0mpurdy.mse.search.criteria.SearchCriteria;
import com.a0mpurdy.mse.search.criteria.SearchScope;
import com.a0mpurdy.mse.search.criteria.SearchType;
import com.a0mpurdy.mse_core.data.author.AuthorIndex;

import java.io.IOException;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText searchBar;

    private OnFragmentInteractionListener mListener;

    HymnBookCache hymnBookCache;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        hymnBookCache = new HymnBookCache();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBar = view.findViewById(R.id.search_bar);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.search_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get search string from view
                String searchString = searchBar.getText().toString();

                // create search criteria
                SearchCriteria criteria = new SearchCriteria(SearchType.CONTAINS, SearchScope.PARAGRAPH, searchString);

                try {
                    ObjectStreamer oStreamer = new AssetManagerWrapper(getActivity().getAssets());
                    AuthorIndex index = hymnBookCache.getIndex(oStreamer);
                    HymnSearchThread sThread = new HymnSearchThread(criteria, index, hymnBookCache, oStreamer);
                    sThread.run();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // popup toast of search string
                Snackbar.make(view, TokenHelper.getTokensAsString(criteria.getTokens()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
