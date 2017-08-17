package com.tecnologiasintech.argussonora.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.guardias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergiosilva on 8/16/17.
 */
public class GuardiaAdapter extends RecyclerView.Adapter<GuardiaViewHolder> implements ChildEventListener{


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
    private DatabaseReference clienteReference = firebase.getReference().child("Argus").child("Clientes")
            .child(CLIENTE_FIREBASE_KEY).child("clienteGuardias");


    private Context mContext;
    private List<guardias> mGuardias;

    public GuardiaAdapter(Context context){
        mContext = context;
        mGuardias = new ArrayList<>();

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
        guardias guardia = mGuardias.get(position);
        holder.bindGuardia(guardia);
    }

    @Override
    public int getItemCount() {
        return mGuardias.size();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        guardias guardia = dataSnapshot.getValue(guardias.class);
        guardia.setUsuarioKey(dataSnapshot.getKey());


        boolean bandera = false;

        if (mGuardias.size()>0){

            for (guardias currentGuardia : mGuardias){

                if (currentGuardia.getUsuarioNombre().equals(guardia.getUsuarioNombre())){
                    bandera = true;
                }
            }
        }

        if (bandera == false){
            mGuardias.add(0,guardia);
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
