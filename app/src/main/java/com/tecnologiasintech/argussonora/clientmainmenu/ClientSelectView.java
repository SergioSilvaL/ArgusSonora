package com.tecnologiasintech.argussonora.clientmainmenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.tecnologiasintech.argussonora.ArgusSonoraApp;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.clientmainmenu.adapter.ClientAdapter;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;
import com.tecnologiasintech.argussonora.mainmenu.MainActivity;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ClientSelectView extends Fragment implements ClientMenuViewPresenterContract.View{

    private ClientAdapter mAdapter;
    @Inject
    ClientMenuViewPresenterContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        doDagger();
    }

    private void doDagger() {
        ArgusSonoraApp.component
                .provideClientMenuComponent()
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mAdapter = new ClientAdapter();

        View view = inflater.inflate(R.layout.fragment_cliente, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.setView(this);
        setItemClickedSubject();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.dropView();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        MenuItem mRefreshMenuItem = menu.findItem(R.id.action_refresh);

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                List<Client> newList=  new ArrayList<>();
                query = query.toLowerCase();
                String clientName;

                for (Client client: mAdapter.getClients()) {
                    clientName = client.getClienteNombre().toLowerCase();
                    if (clientName.contains(query)) {
                        newList.add(client);
                    }
                }

                mAdapter.loadClients(newList);

                return false;
            }
        });

        mRefreshMenuItem.setOnMenuItemClickListener(___-> {
            presenter.loadClients();
            return false;
        });
    }

    @Override
    public void onSuccessLoad(List<Client> clients) {
        mAdapter.loadClients(clients);
    }

    @Override
    public void onErrorLoad() {
        // TODO:
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void dismissProgressBar() {

    }

    private void setItemClickedSubject() {
        presenter.loadClientSelected();
    }

    @Override
    public Observable<String> getItemClientObservable() {
        return mAdapter.getItemCickSubject();
    }

    @Override
    public void onItemSelected(String client) {
        Intent intent = new Intent(getActivity(), ClienteActivity.class);
        intent.putExtra(MainActivity.EXTRA_REFERENCE_CLIENTE, client);
        startActivity(intent);
    }
}