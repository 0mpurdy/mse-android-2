package com.a0mpurdy.mse.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.a0mpurdy.mse.R;
import com.a0mpurdy.mse.data.hymn.HymnBook;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HymnBookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HymnBookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HymnBookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_HYMN_BOOK = "hymnBook";

    // TODO: Rename and change types of parameters
    private HymnBook mHymnBook;

    private EditText mHymnNumber;

    private OnFragmentInteractionListener mListener;

    public HymnBookFragment() {
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
    public static HymnBookFragment newInstance(HymnBook cache) {
        HymnBookFragment fragment = new HymnBookFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HYMN_BOOK, cache);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHymnBook = (HymnBook) getArguments().getSerializable(ARG_HYMN_BOOK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hymn_book, container, false);
//        EditText numberField = ;
//        numberField.setFocusable(true);
        mHymnNumber = v.findViewById(R.id.hymn_number);
        mHymnNumber.requestFocus();
        v.findViewById(R.id.go_to_hymn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                int hymnNumber;
                try {
                    hymnNumber = Integer.parseInt(mHymnNumber.getText().toString());
                } catch (NumberFormatException e) {
                    hymnNumber = 1;
                }
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_home, HymnFragment.newInstance(mHymnBook.getHymn(hymnNumber)))
                        .addToBackStack("hymn number " + hymnNumber)
                        .commit();
            }
        });

        // https://stackoverflow.com/questions/31779005/how-show-soft-keyboard-automatically-when-edittext-receives-focus
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
