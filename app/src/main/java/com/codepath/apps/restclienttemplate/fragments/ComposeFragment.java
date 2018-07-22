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
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwiiterApp;
import com.codepath.apps.restclienttemplate.TwiiterClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.HashMap;
import java.util.Objects;

import cz.msebera.android.httpclient.Header;

//todo show image profile and username
public class ComposeFragment extends DialogFragment {
    static EditText etTwiit;
    TwiiterClient client;

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
        client = TwiiterApp.getRestClient(getContext());

        assert getArguments() != null;
        String title = getArguments().getString("title", "Compose a twiit");
        getDialog().setTitle(title);

        //Send data back to fragment
        Button mBtnSubmitTwiit = view.findViewById(R.id.btnSubmit);
        mBtnSubmitTwiit.setOnClickListener(
                v -> {
                    //TODO upload twiit and dismiss Dialog
                    etTwiit = view.findViewById(R.id.etTwiit);
                    String status = etTwiit.getText().toString();
                    client.postStatus(new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode,
                                              Header[] headers,
                                              byte[] responseBody) {
                        }

                        @Override
                        public void onFailure(int statusCode,
                                              Header[] headers,
                                              byte[] responseBody,
                                              Throwable error) {
                        }
                    }, status);

                    dismiss();
                }
        );
        ImageButton btnCancel = view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> {
            //dismiss Dialog
            dismiss();
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        //Change FilterFragmentDialog size
        int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
        int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(width, height);
    }

}

