package com.tecnologiasintech.argussonora.presentation.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.tecnologiasintech.argussonora.R;

/**
 * Created by sergiosilva on 9/8/17.
 */
public class TutorialViewDialogFragment extends DialogFragment{
    public TutorialViewDialogFragment(){}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Builds the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tutorial");

        // Inflate the view(s)
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View view = inflater.inflate(R.layout.view_asistencia_tutorial,null);
        // Set the view inside the dialog
        builder.setView(view);

        // data is uploaded when the 'anadir' button is clicked.
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Current Dialog gets removed when 'Cancelar' Button is clicked.
        builder.setNegativeButton(R.string.button_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        return builder.create();
    }
}
