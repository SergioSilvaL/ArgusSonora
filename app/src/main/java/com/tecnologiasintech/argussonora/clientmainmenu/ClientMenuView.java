package com.tecnologiasintech.argussonora.clientmainmenu;

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


import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.clientmainmenu.adapter.ClientAdapter;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;


import java.util.ArrayList;
import java.util.List;

public class ClientMenuView extends Fragment implements ClientMenuViewPresenterContract.View{

    private ClientAdapter mAdapter;
    private ClientMenuViewPresenterContract.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ClientMenuPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        mAdapter = new ClientAdapter(getContext());

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
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
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Cliente> newList = new ArrayList<>();

                for(Cliente cliente : ClientAdapter.filterClientes){
                    String name = cliente.getClienteNombre().toLowerCase();

                    if (name.contains(newText)){
                        newList.add(cliente);
                    }
                }

                mAdapter.loadClientSearch(newList);
                return false;
            }
        });

        mRefreshMenuItem.setOnMenuItemClickListener(item -> {
            presenter.loadClients();
            return false;
        });

    }

    @Override
    public void onSuccessload(List<Cliente> clients) {
    }

    @Override
    public void onErrorLoad() {
        // TODO:
    }
}