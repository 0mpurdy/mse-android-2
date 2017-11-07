package com.a0mpurdy.mse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.a0mpurdy.mse.R;
import com.a0mpurdy.mse.hymn.HymnBookCache;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HymnBooksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HymnBooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HymnBooksFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CACHE = "cache";

    // TODO: Rename and change types of parameters
    private HymnBookCache mCache;

    private OnFragmentInteractionListener mListener;

    public HymnBooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cache Hymn book cache
     * @return A new instance of fragment HymnBooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HymnBooksFragment newInstance(HymnBookCache cache) {
        HymnBooksFragment fragment = new HymnBooksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CACHE, cache);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCache = (HymnBookCache) getArguments().getSerializable(ARG_CACHE);
        } else {
            mCache = new HymnBookCache();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hymn_books, container, false);
        LinearLayout booksLayout = v.findViewById(R.id.hymn_books);

        String[] years = new String[]{"1903", "1932", "1951", "1962", "1973"};

        for (String year : years) {
            Button yearButton = new Button(getActivity());
            yearButton.setText(year);
            final String serialName = "hymns" + year + ".ser";
            yearButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    mCache.getHymnBook(serialName, getActivity().getAssets());
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.content_home, HymnBookFragment.newInstance(mCache.getHymnBook(serialName, getActivity().getAssets())))
                            .addToBackStack(serialName)
                            .commit();
                }
            });
            booksLayout.addView(yearButton);
        }

        v.findViewById(R.id.hymn1962_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_home, HymnBookFragment.newInstance(mCache.getHymnBook("hymns1962.ser", getActivity().getAssets()))).commit();
            }
        });
        return v;
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
