package com.tecnologiasintech.argussonora.presentation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraRegistro;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Notificacion;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sergiosilva on 5/1/17.
 */

public class BitacoraRegistroAdapter extends RecyclerView.Adapter<BitacoraRegistroAdapter.ViewHolder>{


    private List<BitacoraRegistro> mRegistro;
    private Callback mCallback;
    private Context mContext;
    private Supervisor mSupervisor;
    private DatabaseReference mBitacoraRegistroRef;
    private DatabaseReference mBitacoraRegistroFechaRef;
    private DatabaseReference mBitacoraRegistroNRRef;

    public BitacoraRegistroAdapter(Callback callback, Context context, Supervisor supervisor){
        mCallback = callback;
        mContext = context;
        mSupervisor = supervisor;
        mRegistro = new ArrayList<>();
        mBitacoraRegistroFechaRef = FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistro").child(new DatePost().getDateKey());
        mBitacoraRegistroRef = FirebaseDatabase.getInstance().getReference().child("Argus").child("BitacoraRegistro").child(new DatePost().getDateKey())
                .child(mSupervisor.getUsuarioKey());
        mBitacoraRegistroRef.addChildEventListener(new bitacoraRegistroChildEventListener());
        mBitacoraRegistroNRRef = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("BitacoraRegistroNoResuelto")
                .child(mSupervisor.getUsuarioKey());
    }

    private class bitacoraRegistroChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            if (!dataSnapshot.getKey().equals("supervisor") && !dataSnapshot.getKey().equals("zona")) {
                BitacoraRegistro registro = dataSnapshot.getValue(BitacoraRegistro.class);
                if (registro.getDescripcion() != null) {
                    registro.setKey(dataSnapshot.getKey());
                    mRegistro.add(mRegistro.size(), registro);
                    notifyDataSetChanged();
                }
            }

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            BitacoraRegistro updatedBitacoraRegistro = dataSnapshot.getValue(BitacoraRegistro.class);
            for (BitacoraRegistro br: mRegistro){
                if (br.getKey().equals(key)){
                    br.setValues(updatedBitacoraRegistro);
                    notifyDataSetChanged();
                    return;
                }
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {
            String key = dataSnapshot.getKey();
            // Remove the item with the given key
            for (BitacoraRegistro br : mRegistro){
                if (br.getKey().equals(key)){
                    mRegistro.remove(br);
                    break;
                }
            }
            notifyDataSetChanged();
        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            // Empty
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Firebase Error
        }
    }
    @Override
    public BitacoraRegistroAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_bitacora_registro, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BitacoraRegistroAdapter.ViewHolder holder, final int position) {
        final BitacoraRegistro bitacoraRegistro = mRegistro.get(position);
        holder.mObservacionTextView.setText(bitacoraRegistro.getDescripcion());
        holder.mTextViewFecha.setText(bitacoraRegistro.getDateCreation());

        switch ((int) bitacoraRegistro.getSemaforo()){
            case 1:
                holder.mSemaforoView.setBackgroundColor(Color.parseColor("#4CAF50"));
                break;
            case 2:
                holder.mSemaforoView.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case 3:
                holder.mSemaforoView.setBackgroundColor(Color.parseColor("#f44336"));
                break;
        }

        if (bitacoraRegistro.getSupervisorResponsibility()) {
            holder.mRatingBarSupervisorResponsibilityIndicator.setRating(1);
        }

        // Si es la responsabilidad del Supervisor de Resolverlo, lo podra editar  y resolverlo
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitacoraRegistro.getSupervisorResponsibility()) {
                    mCallback.onAddEdit(bitacoraRegistro);
                }else{
                    Toast.makeText(mContext, "No le corresponde al Supervisor", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return mRegistro.size();
    }

    public void add(BitacoraRegistro bitacoraRegistro){

        bitacoraRegistro.setHora(new DatePost().get24HourFormat());

        updateSupervisorInfo(mSupervisor.getUsuarioNombre(),
                mSupervisor.getUsuarioZona());
        updateFechaInfo();
        addEditNotification(bitacoraRegistro.getSemaforo(), bitacoraRegistro.getDescripcion());

        // TODO: NEW

        String key = new DatePost().getTimeCompletetKey();

        bitacoraRegistro.setKey(key);
        bitacoraRegistro.setObservacionKey(key);

        if (bitacoraRegistro.getSemaforo()== 1) {
            mBitacoraRegistroRef.child(key).setValue(bitacoraRegistro);
        }else{
            bitacoraRegistro.setObservacionKey(key);
            mBitacoraRegistroNRRef.child(key).setValue(bitacoraRegistro);
            updateBitacoraRegistroNRSupervisorInfo(bitacoraRegistro.getSupervisor());
        }
    }

    private void addEditNotification(long semaforo, String observacion){

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance().getReference().child("Argus");

        if (semaforo != 1){
            Notificacion notificacion = new Notificacion("AI", observacion, new DatePost().getDatePost());
            databaseReference.child("NotificacionTmp").push().setValue(notificacion);
            databaseReference.child("Notificacion").push().setValue(notificacion);
        }
        // Delete notification
        else {
            // TODO: Delete notification or add notification which warns the system administrador the event has been handeled
        }
    }

    private void updateFechaInfo(){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/fecha", new DatePost().getDateKey());
        mBitacoraRegistroFechaRef.updateChildren(childUpdates);
    }

    private void updateSupervisorInfo(String supervisor, String zona) {

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/supervisor", supervisor);
        childUpdates.put("/zona", zona);
        mBitacoraRegistroRef.updateChildren(childUpdates);
    }

    public void update(BitacoraRegistro bitacoraRegistro, String newObservacion, long newSemaforoStatus){
        addEditNotification(newSemaforoStatus, newObservacion);


        bitacoraRegistro.setDescripcion(newObservacion);
        bitacoraRegistro.setSemaforo(newSemaforoStatus);
        if (newSemaforoStatus==1) {

            mBitacoraRegistroRef.child(bitacoraRegistro.getObservacionKey()).setValue(bitacoraRegistro);
        }else{
            // Delete from current List and add to new List;
            // add
            mBitacoraRegistroNRRef.child(bitacoraRegistro.getObservacionKey()).setValue(bitacoraRegistro);
            // Updates Bitacora Registro No Resuelto Supervisor Reference
            updateBitacoraRegistroNRSupervisorInfo(bitacoraRegistro.getSupervisor());

            // Delete
            remove(bitacoraRegistro);
        }
    }

    private void updateBitacoraRegistroNRSupervisorInfo(String supervisor){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Argus")
                .child("BitacoraRegistroNoResuelto")
                .child(mSupervisor.getUsuarioKey());

        Map<String, Object> childUpdate = new HashMap<>();
        childUpdate.put("/supervisor", supervisor);
        reference.updateChildren(childUpdate);
    }

    public void remove(BitacoraRegistro bitacoraRegistro){
        mBitacoraRegistroRef.child(bitacoraRegistro.getKey()).removeValue();
    }

    public interface Callback {
        public void onAddEdit(BitacoraRegistro bitacoraRegistro);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mObservacionTextView;
        private TextView mTextViewFecha;
        private LinearLayout mSemaforoView;
        private RatingBar mRatingBarSupervisorResponsibilityIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            mObservacionTextView = (TextView) itemView.findViewById(R.id.textViewBitacoraRegistroObservacion);
            mSemaforoView = (LinearLayout) itemView.findViewById(R.id.LinearLayoutSemaforoRepresentation);
            mTextViewFecha = (TextView) itemView.findViewById(R.id.textViewFecha);
            mRatingBarSupervisorResponsibilityIndicator = (RatingBar) itemView.findViewById(R.id.ratingBarSupervisorResponsibilityIndicator);
        }
    }


}

