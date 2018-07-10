package com.tecnologiasintech.argussonora.clientmainmenu.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder> {

    private List<Client> clients;
    private Subject<String> clickSubject = PublishSubject.<String>create().toSerialized();

    public void loadClients(List<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients);
        this.clients = clients;
        notifyDataSetChanged();
    }

    public Observable<String> getItemCickSubject() {
        return clickSubject;
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
        holder.bindToView(currentClient);
    }

    @Override
    public int getItemCount() {
        return (clients == null) ? 0 : clients.size();
    }

    public List<Client> getClients() {
        return clients;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textViewClient;

        private ViewHolder(View itemView) {
            super(itemView);
            textViewClient = itemView.findViewById(R.id.TEXT_VIEW_CLIENTE_NAME);
            itemView.setOnClickListener(this);
        }

        private void bindToView(Client client) {
            int pos = getAdapterPosition() + 1;
            textViewClient.setText(String.format("%d. + %s", pos, client.getClienteNombre()));
        }

        @Override
        public void onClick(View v) {
            clickSubject.onNext(clients.get(getAdapterPosition()).getClienteNombre());
        }
    }

}
