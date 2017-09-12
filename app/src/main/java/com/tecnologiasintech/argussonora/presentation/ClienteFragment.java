package com.tecnologiasintech.argussonora.presentation;

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
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;


import java.util.ArrayList;


/**
 * Created by Legible on 2/17/2017. // Edited by Sergio on 09/11/2017.
 */

public class ClienteFragment extends Fragment {

    public static final String ARG_SUPERVISOR = "ARG_SUPERVISOR";

    private ClienteAdapter mAdapter;
    private Supervisor mSupervisor;

    public static ClienteFragment newInstance(Supervisor supervisor){
        Bundle args = new Bundle();
        args.putParcelable(ARG_SUPERVISOR, supervisor);
        ClienteFragment fragment = new ClienteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSupervisor = getArguments().getParcelable(ARG_SUPERVISOR);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        //Create the Adapter
        mAdapter = new ClienteAdapter(ClienteFragment.this.getContext(), mSupervisor);

        //Capture the recyclerView
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ClienteFragment.this.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform final Search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<Cliente> newList = new ArrayList<>();

                for(Cliente cliente : ClienteAdapter.filterClientes){
                    String name = cliente.getClienteNombre().toLowerCase();

                    if (name.contains(newText)){
                        newList.add(cliente);
                    }
                }

                mAdapter.setFilter(newList);
                return false;
            }
        });
    }
}