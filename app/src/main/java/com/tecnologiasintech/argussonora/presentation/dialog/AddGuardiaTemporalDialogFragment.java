package com.tecnologiasintech.argussonora.presentation.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraRegistro;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Notificacion;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * Created by sergiosilva on 9/12/17.
 */

public class AddGuardiaTemporalDialogFragment extends DialogFragment {

    public static final String ACTION = "AG";
    public static final String TAG = AddGuardiaTemporalDialogFragment.class.getSimpleName();

    public static final String ARG_CLIENTE = "ARG_CLIENTE";
    public static final String ARG_SUPERVISOR = "ARG_SUPERVISOR";

    private Cliente mCliente;
    private Supervisor mSupervisor;

    private EditText mGuardiaDomicillioEditText;
    private EditText mGuardiaNombreEditText;
    private EditText mGuardiaTelefonoEditText;

    public AddGuardiaTemporalDialogFragment(){
    }

    public static AddGuardiaTemporalDialogFragment
        newInstance(Supervisor supervisor, Cliente cliente){

        AddGuardiaTemporalDialogFragment frag = new AddGuardiaTemporalDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_CLIENTE, cliente);
        args.putParcelable(ARG_SUPERVISOR, supervisor);
        frag.setArguments(args);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get arguments

        mCliente = getArguments().getParcelable(ARG_CLIENTE);
        mSupervisor = getArguments().getParcelable(ARG_SUPERVISOR);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Guardia Temporal");

        //Edited: Overrriding  onCreateView is not necessary in your case
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.fragment_add_guardia_temporal,null);

        // Get field from view
        mGuardiaDomicillioEditText = (EditText) view.findViewById(R.id.editTextGuardiaDomicillio);
        mGuardiaNombreEditText = (EditText) view.findViewById(R.id.editTextGuardiaNombre);
        mGuardiaTelefonoEditText = (EditText) view.findViewById(R.id.editTextGuardiaTelefono);

        builder.setView(view);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                push();
            }
        });

        builder.setNegativeButton("Cancelar", null);

        return builder.create();
    }

    private void push(){

        // Send to Notificacion Temporal
        pushNotificacionTemporal();
    }

    private DatabaseReference mBitacoraRegistroNRRef =
            FirebaseDatabase.getInstance().getReference().child("Argus/BitacoraRegistroNoResuelto");
                    ;// TODO:


    private void pushBitacoreRegistroNoResuelto(String key) {

        // TODO: Clean Code

        String bitacoraRegistroNRKey = new DatePost().getTimeCompletetKey();

        //Set Descripcion
        String descripcion = mSupervisor.getUsuarioNombre() + " agrego a un nuevo guardia";

        BitacoraRegistro bitacoraRegistro = new BitacoraRegistro(
                descripcion,
                3,
                mSupervisor.getUsuarioNombre(),
                mCliente.getClienteZonaAsignada(),
                new DatePost().get24HourFormat());

        bitacoraRegistro.setObservacionKey(bitacoraRegistroNRKey);
        bitacoraRegistro.setHora(new DatePost().get24HourFormat());
        bitacoraRegistro.setDateCreation(new DatePost().getDatePost());


        // Todo: Send info to Bitacora
        mBitacoraRegistroNRRef.child(mSupervisor.getUsuarioKey()).child(bitacoraRegistroNRKey).setValue(bitacoraRegistro);



        // TODO: Update Info to

        DatabaseReference notificacionTmpRef =
                FirebaseDatabase.getInstance().getReference("Argus/NotificacionTmp");

        notificacionTmpRef.child(key).child("bitacoraInformacion")
                .setValue(new BitacoraRegistro(new DatePost().getDateKey(),
                        mSupervisor.getUsuarioKey(),// TODO:
                        bitacoraRegistroNRKey));

        updateBitacoraRegistroNRSupervisorInfo(mSupervisor.getUsuarioNombre()); // TODO:

        Toast.makeText(getContext(),"Se envio una solicitud con exito",Toast.LENGTH_LONG).show();

    }

    private void updateBitacoraRegistroNRSupervisorInfo(String supervisor){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/supervisor", supervisor);
        mBitacoraRegistroNRRef.child(mSupervisor.getUsuarioKey()).updateChildren(childUpdates);
    }

    private void pushNotificacionTemporal() {

        // TODO: Clean Code
        //Get Current Date
        DatePost datePost = new DatePost();
        String fecha = datePost.getDatePost();

        //Set Descripcion
        String descripcion = mSupervisor.getUsuarioNombre() + " agrego a un nuevo guardia";


        // Get the current notificition
        Notificacion notificacion = new Notificacion(ACTION,descripcion,fecha);

        //Set the Guardia data model
        Guardia guardia = new Guardia();
        guardia.setUsuarioNombre(mGuardiaNombreEditText.getText().toString());

        // Get Domicillio Input

        String domicillio = mGuardiaDomicillioEditText.getText().toString();

        if (domicillio != null) {
            guardia.setUsuarioDomicilio(domicillio);
        }else{
            guardia.setUsuarioDomicilio("");
        }

        // Get Phone Number Input

        String telefono = mGuardiaTelefonoEditText.getText().toString();

        if (telefono != null){
            guardia.setUsuarioTelefono(Long.valueOf(telefono));
        }else {
            guardia.setUsuarioTelefono(0);
        }


        // TODO: Get Client
        guardia.setUsuarioClienteAsignado(mCliente.getClienteNombre());// TODO:


        DatabaseReference notificacionTmpRef =
                FirebaseDatabase.getInstance().getReference("Argus/NotificacionTmp");

        // Push notification with nested class
        String key = notificacionTmpRef.push().getKey();

        notificacionTmpRef.child(key).setValue(notificacion);
        notificacionTmpRef.child(key).child("informacion").setValue(guardia);

        pushBitacoreRegistroNoResuelto(key);
    }


}
