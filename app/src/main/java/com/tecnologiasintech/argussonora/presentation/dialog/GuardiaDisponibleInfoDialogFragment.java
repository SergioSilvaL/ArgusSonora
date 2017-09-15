package com.tecnologiasintech.argussonora.presentation.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.presentation.adapter.ConsignaDialogFragmentAdapter;
import com.tecnologiasintech.argussonora.presentation.fragment.GuardiaDisponibleFragment;

/**
 * Created by sergiosilva on 9/14/17.
 */

public class GuardiaDisponibleInfoDialogFragment extends android.support.v4.app.DialogFragment {

    public static final String ARG_GUARDIA = "ARG_GUARDIA";

    public GuardiaDisponibleInfoDialogFragment(){
        // Required empty constructor
    }

    public static GuardiaDisponibleInfoDialogFragment newInstance(Guardia guardia){
        GuardiaDisponibleInfoDialogFragment frag = new GuardiaDisponibleInfoDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_GUARDIA, guardia);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Informacion Basica");

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view  = inflater.inflate(R.layout.dialog_fragment_guardia_disponible_info, null);


        // Inflate Views
        TextView nombreTxtView = (TextView) view.findViewById(R.id.textViewGuardiaDisponibleNombre);
        TextView DomicilioTxtView = (TextView) view.findViewById(R.id.textViewGuardiaDisponibleDomicillio);
        TextView TelefonoTxtView = (TextView) view.findViewById(R.id.textViewGuardiaDisponibleTelefono);

        Guardia guardia = getArguments().getParcelable(ARG_GUARDIA);

        nombreTxtView.setText(guardia.getUsuarioNombre());
        DomicilioTxtView.setText(guardia.getUsuarioDomicilio());
        TelefonoTxtView.setText(guardia.getUsuarioTelefono() + "");

        builder.setView(view);

        builder.setPositiveButton("ok" ,null);

        return builder.create();
    }
}
