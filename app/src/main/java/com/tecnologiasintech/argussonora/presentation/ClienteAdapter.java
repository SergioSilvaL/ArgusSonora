package com.tecnologiasintech.argussonora.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;
import com.tecnologiasintech.argussonora.presentation.activity.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by SERGIO on 20/02/2017.
 */

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolder> {

    private Context mContext;
    private List<Cliente> mClienteList;
    public static List<Cliente> filterClientes;


    public ClienteAdapter(Context context, Supervisor supervisor) {
        mContext = context;
        setClientList(supervisor.getUsuarioZona());
    }

    private void setClientList(String usuarioZona) {

        mClienteList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Argus/Zonas")
                .child(usuarioZona).child("zonaClientes");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    try{
                        Cliente cliente = data.getValue(Cliente.class);
                        mClienteList.add(cliente);
                        notifyDataSetChanged();
                    }catch (Exception e){

                    }
                }

                filterClientes = mClienteList;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(mContext, databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setFilter(ArrayList<Cliente> newList){
        mClienteList = new ArrayList<>();
        mClienteList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_cliente, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Collections.sort(mClienteList, new Comparator<Cliente>() {
            @Override
            public int compare(Cliente o1, Cliente o2) {
                return o1.getClienteNombre().compareTo(o2.getClienteNombre());
            }
        });

        final Cliente currentCliente = mClienteList.get(position);

        holder.bindToView(currentCliente, position + 1);

    }

    @Override
    public int getItemCount() {
        return mClienteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewCliente;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewCliente = (TextView) itemView.findViewById(R.id.TEXT_VIEW_CLIENTE_NAME);

            itemView.setOnClickListener(this);
        }

        public void bindToView(Cliente currentCliente, int position) {
            textViewCliente.setText(position + ". " + currentCliente.getClienteNombre());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ClienteActivity.class);
            intent.putExtra(MainActivity.EXTRA_REFERENCE_CLIENTE,mClienteList.get(getAdapterPosition()).getClienteNombre());
            ((Activity)mContext).startActivity(intent);
        }
    }

}
