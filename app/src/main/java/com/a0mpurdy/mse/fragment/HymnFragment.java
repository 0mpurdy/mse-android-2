package com.a0mpurdy.mse.fragment;

import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a0mpurdy.mse.Home;
import com.a0mpurdy.mse.R;
import com.a0mpurdy.mse.hymn.AssetManagerWrapper;
import com.a0mpurdy.mse.hymn.HymnBookCache;
import com.a0mpurdy.mse.search.source.Reference;
import com.a0mpurdy.mse_core.data.author.Author;
import com.a0mpurdy.mse_core.data.hymn.Hymn;
import com.a0mpurdy.mse_core.data.hymn.HymnBook;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HymnFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HymnFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HymnFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_REFERENCE = "reference";

    // TODO: Rename and change types of parameters
    private Hymn mHymn;
    private HymnBookCache cache;

    private OnFragmentInteractionListener mListener;

    public HymnFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param reference reference to the hymn this fragment displays
     * @return A new instance of fragment HymnFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HymnFragment newInstance(Reference reference) {
        HymnFragment fragment = new HymnFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_REFERENCE, reference);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Reference ref = (Reference) getArguments().getSerializable(ARG_REFERENCE);
            try {
                cache = ((Home) getActivity()).getHymnCache();
                mHymn = cache.getHymnBook(ref.volNum, new AssetManagerWrapper(getActivity().getAssets())).getHymn(ref.pageNum);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hymn, container, false);
        ((TextView) v.findViewById(R.id.number)).setText(mHymn.getNumber() + "");
        TextView verses = v.findViewById(R.id.verse1);
        String verseText = mHymn.getVerseText();
        verses.setText(verseText);

        v.findViewById(R.id.jump_back).setOnClickListener(new ChangeHymnListener(mHymn, -10, getFragmentManager()));
        v.findViewById(R.id.step_back).setOnClickListener(new ChangeHymnListener(mHymn, -1, getFragmentManager()));
        v.findViewById(R.id.step_forward).setOnClickListener(new ChangeHymnListener(mHymn, 1, getFragmentManager()));
        v.findViewById(R.id.jump_forward).setOnClickListener(new ChangeHymnListener(mHymn, 10, getFragmentManager()));

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

class ChangeHymnListener implements View.OnClickListener {

    Hymn mHymn;
    int mChange;
    FragmentManager fm;

    ChangeHymnListener(Hymn hymn, int change, FragmentManager fm) {
        mHymn = hymn;
        mChange = change;
        this.fm = fm;
    }

    @Override
    public void onClick(View v) {
        HymnBook parent = mHymn.getParentHymnBook();
        try {
            // check that hymn is accessible before switching fragments
            Hymn newHym = parent.getHymn(mHymn.getNumber() + mChange);
            Reference ref = new Reference(Author.HYMNS, parent.getId(), mHymn.getNumber() + mChange, 0, 0);
            fm.beginTransaction()
                    .replace(R.id.content_home, HymnFragment.newInstance(ref))
                    .commit();
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

}
