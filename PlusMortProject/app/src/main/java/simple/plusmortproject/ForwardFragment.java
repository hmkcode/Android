package simple.plusmortproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.plus.PlusOneButton;

public class ForwardFragment extends Fragment {

    private static final int PLUS_ONE_REQUEST_CODE = 0;

    private PlusOneButton mPlusOneButton;

    private OnFragmentInteractionListener mListener;


    public static ForwardFragment newInstance(String param1, String param2) {
        ForwardFragment fragment = new ForwardFragment();
        return fragment;
    }

    public ForwardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forward, container, false);

        //Find the +1 button
        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }

    @Override
    public void setTargetFragment(Fragment fragment, int requestCode) {
        super.setTargetFragment(fragment, requestCode);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return super.getLoaderManager();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startIntentSenderForResult(IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        super.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }
}
