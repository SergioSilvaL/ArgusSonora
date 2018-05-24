package com.tecnologiasintech.argussonora.clientmainmenu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;
import com.tecnologiasintech.argussonora.mainmenu.MainActivity;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;

import java.util.ArrayList;
import java.util.List;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private Context mContext;
    private List<Client> clients;


    public ClientAdapter(Context context) {
        mContext = context;
    }

    public void loadClients(List<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients);
        notifyDataSetChanged();
    }

    public void loadClientSearch(ArrayList<Client> newList){
        clients.clear();
        clients.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cliente, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Client currentClient = clients.get(position);
        holder.bindToView(currentClient, position );
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewCliente;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewCliente = itemView.findViewById(R.id.TEXT_VIEW_CLIENTE_NAME);
            itemView.setOnClickListener(this);
        }

        public void bindToView(Client client, int position) {
            textViewCliente.setText((position + 1) + ". " + client.getClienteNombre());
        }

        // TODO:  Move to fragment
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, ClienteActivity.class);
            intent.putExtra(MainActivity.EXTRA_REFERENCE_CLIENTE, clients.get(getAdapterPosition()).getClienteNombre());
            ((Activity)mContext).startActivity(intent);
        }
    }

}
