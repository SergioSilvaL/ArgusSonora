package com.tecnologiasintech.argussonora.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.activity.MainActivity;
import com.tecnologiasintech.argussonora.presentation.activity.MoveGuardiaDisponible;
import com.tecnologiasintech.argussonora.presentation.fragment.GuardiaDisponibleFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sergiosilva on 9/12/17.
 */

public class GuardiaDisponibleAdapter extends RecyclerView.Adapter<GuardiaDisponibleAdapter.ViewHolder>{

    private Context mContext;
    private List<Guardia> mGuardiaList;
    public static List<Guardia> filterGuardias;
    private Supervisor mSupervisor;

    public GuardiaDisponibleAdapter(Context context, Supervisor supervisor){
        mContext = context;
        mSupervisor = supervisor;
        setGuardiaDisponibles();
        filterGuardias = mGuardiaList;
    }

    public void setGuardiaDisponibles(){

        mGuardiaList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Argus/guardias");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        // Only get Guardias that are 'disponible'
                        Guardia guardia = snapshot.getValue(Guardia.class);
                        if (guardia.isUsuarioDisponible()){
                            mGuardiaList.add(guardia);
                            notifyDataSetChanged();
                        }
                    }catch (DatabaseException de){
                        Toast.makeText(mContext, de.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (NullPointerException npe){
                        Toast.makeText(mContext, npe.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        notifyDataSetChanged();
    }

    public void setFilter(ArrayList<Guardia> newList){
        mGuardiaList = new ArrayList<>();
        mGuardiaList.addAll(newList);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_guardia_disponible, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GuardiaDisponibleAdapter.ViewHolder holder, int position) {
        Collections.sort(mGuardiaList, new Comparator<Guardia>() {
            @Override
            public int compare(Guardia o1, Guardia o2) {
                return o1.getUsuarioNombre().compareTo(o2.getUsuarioNombre());
            }
        });

        holder.bindToView(mGuardiaList.get(position));
    }

    @Override
    public int getItemCount() {
        return mGuardiaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textviewGuardia;

        public ViewHolder(View itemView) {
            super(itemView);

            textviewGuardia = (TextView) itemView.findViewById(R.id.guardia_disponible_textView);

            itemView.setOnClickListener(this);
        }

        public void bindToView(Guardia guardia) {
            textviewGuardia.setText(guardia.getUsuarioNombre());
            Log.v(GuardiaDisponibleFragment.class.getSimpleName(), guardia.getUsuarioNombre());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, MoveGuardiaDisponible.class);
            intent.putExtra(MainActivity.EXTRA_GUARDIA_DISPONIBLE, mGuardiaList.get(getAdapterPosition()));
            ((Activity)mContext).startActivityForResult(intent, MainActivity.REQUEST_GUARDIA_DISPONIBLE);
        }
    }
}
