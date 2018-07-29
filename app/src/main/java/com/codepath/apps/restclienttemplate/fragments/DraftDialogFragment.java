package com.codepath.apps.restclienttemplate.fragments;

/**
 * Created by drake on 7/29/18
 */


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class DraftDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the Builder class because this dialog has a simple UI
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        // Dialog will have "Make selection" as the title
        builder.setMessage("Save Draft ?")
                //An OK button thar does nothing
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happening here
                    }
                })

                //A "Cancel" button that does nothing
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing happening here either
                    }
                });

        //Create the object and return it
        return  builder.create();
    }// End onCreateDialog
}
