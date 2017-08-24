package com.tecnologiasintech.argussonora.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;
import com.tecnologiasintech.argussonora.presentation.activity.GuardiaActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 8/16/17.
 */
public class GuardiaAdapter extends RecyclerView.Adapter<GuardiaAdapter.GuardiaViewHolder> implements ChildEventListener{


    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String CLIENTE_FIREBASE_KEY = "Almacen Zapata";

    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    /**
     * Using the key, "messages", we can access a reference to the list of messages. We will be
     * listening to changes to the children of this reference in this Activity.
     */
    private DatabaseReference clienteReference =
            firebase.getReference("Argus/Clientes/Almacen Zapata/clienteGuardias");

    private Context mContext;
    private Cliente mCliente;
    private List<GuardiaBitacora> mGuardiaBitacoraList;
    public static final String TAG = GuardiaAdapter.class.getSimpleName();


    public GuardiaAdapter(Context context){
        mContext = context;

        mGuardiaBitacoraList = new ArrayList<>();


        Log.i(TAG, "Cliente Reference Child Event Listener Added");

        clienteReference.addChildEventListener(this);

    }

    @Override
    public GuardiaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_guardia, parent, false);
        return new GuardiaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuardiaViewHolder holder, int position) {
        GuardiaBitacora guardiaBitacora = mGuardiaBitacoraList.get(position);
        holder.bindGuardia(guardiaBitacora);
    }

    public void setCliente(Cliente cliente){
        mCliente = cliente;
    }

    @Override
    public int getItemCount() {
        return mGuardiaBitacoraList.size();
    }


    public class GuardiaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mGuardianameDisplay;
        private ImageView mAsistioIcon, mNoAsistioIcon, mDobleTurnoIcon, mDescansoElaboradoIcon,
                mHorasExtraIcon;

        public GuardiaViewHolder(View itemView) {
            super(itemView);

            mGuardianameDisplay = (TextView) itemView.findViewById(R.id.nameTxt);
            mAsistioIcon = (ImageView) itemView.findViewById(R.id.asistioIcon);
            mNoAsistioIcon = (ImageView) itemView.findViewById(R.id.inasistioIcon);
            mDobleTurnoIcon = (ImageView) itemView.findViewById(R.id.dobleTurnoIcon);
            mDescansoElaboradoIcon = (ImageView) itemView.findViewById(R.id.descansoElaboradoIcon);
            mHorasExtraIcon = (ImageView) itemView.findViewById(R.id.horasExtraIcon);

            itemView.setOnClickListener(this);
        }

        public void bindGuardia(GuardiaBitacora guardia){

            mGuardianameDisplay.setText(guardia.getUsuarioNombre());

            // Implement Binding Code
            // 1. Check if fecha is not null;

            // Implement Binding Code
            // TODO: set Current Date Key
            String fechaActual = "20170824";

            String fecha = guardia.getBitacoraSimple().getFecha();
            if (fecha.equals(fechaActual) ){
                // Bind

                // Asistio
                if (guardia.getBitacoraSimple().isAsistio()){
                    mAsistioIcon.setVisibility(View.VISIBLE);
                }

                // Cubre Descanso
                if (guardia.getBitacoraSimple().isCubredescanso()){
                    mDescansoElaboradoIcon.setVisibility(View.VISIBLE);
                }

                // Doble Turno
                if (guardia.getBitacoraSimple().isDobleturno()){
                    mDobleTurnoIcon.setVisibility(View.VISIBLE);
                }

                // Horas Extra
                if (guardia.getBitacoraSimple().getHorasextra() > 0){
                    mHorasExtraIcon.setVisibility(View.VISIBLE);
                }

                // No Asitio
                if (guardia.getBitacoraSimple().isNoasistio()){
                    mNoAsistioIcon.setVisibility(View.VISIBLE);
                }


            }else{
                // Update day and delete node
                // OverRide bitacora
                // TODO: If it's not the same date, decide how it will be handled, Handle in Signature Activity


            }

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, GuardiaActivity.class);
            intent.putExtra(ClienteActivity.EXTRA_CLIENTE, mCliente);
            intent.putExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA , mGuardiaBitacoraList.get(getAdapterPosition()));
            intent.putExtra(ClienteActivity.EXTRA_LIST_POSITION, getAdapterPosition());
            ((Activity)mContext).startActivityForResult(intent, ClienteActivity.REQUEST_FAVORITE);
        }
    }

    /**
     * Get Information from Firebase Node
     * */ 

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        // 4.
        GuardiaBitacora guardiaBitacora = dataSnapshot.getValue(GuardiaBitacora.class);

        // TODO: Deal with this line
        guardiaBitacora.setUsuarioKey(dataSnapshot.getKey());

        Log.i(TAG, guardiaBitacora.toString());

        boolean bandera = false;

        if (mGuardiaBitacoraList.size()>0){

            for (GuardiaBitacora currentGuardia : mGuardiaBitacoraList){

                if (currentGuardia.getUsuarioNombre().equals(guardiaBitacora.getUsuarioNombre())){
                    bandera = true;
                }
            }
        }

        if (bandera == false){
            mGuardiaBitacoraList.add(0,guardiaBitacora);
        }

        notifyDataSetChanged();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
