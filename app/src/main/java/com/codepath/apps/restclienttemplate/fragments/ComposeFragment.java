package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;

import java.util.HashMap;

public class ComposeFragment extends DialogFragment {
    static private HashMap<String, String> filter = new HashMap<>();
    private Button mBtnSubmitTwiit;
    static EditText etDate;
    static  String beginDate="";

    public ComposeFragment() {} //Empty Constructor is required for DialogFragment

    public static ComposeFragment newInstance(String title) {
        ComposeFragment frag = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString("Compose a twiit", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_compose, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        assert getArguments() != null;
        String title = getArguments().getString("title", "Compose a twiit");
        getDialog().setTitle(title);


        //Send data back to fragment
        mBtnSubmitTwiit = view.findViewById(R.id.btnSubmit);
        mBtnSubmitTwiit.setOnClickListener(
                v -> {
                    //TODO get DialogFragment data and put it in Hashmap filter
                }
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        //Change FilterFragmentDialog size
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        getDialog().getWindow().setLayout(width, height);
    }

    //Defines the listener interface with a method passing back data result.
    public interface ComposeListener{
        void onFinishFilterDialog(HashMap<String, String> filter);
    }





}

