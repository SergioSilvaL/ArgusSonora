package com.tecnologiasintech.argussonora.presentation.fragment;

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
import android.widget.Toast;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaDisponibleAdapter;


import java.util.ArrayList;


/**
 * Created by sergiosilva on 9/12/17.
 */

public class GuardiaDisponibleFragment extends Fragment {

    public static final String TAG = GuardiaDisponibleFragment.class.getSimpleName();
    public static final String ARG_SUPERVISOR = "ARG_SUPERVISOR";

    public GuardiaDisponibleAdapter mAdapter;
    private Supervisor mSupervisor;

    public static GuardiaDisponibleFragment newInstance(Supervisor supervisor){
        Bundle args = new Bundle();
        args.putParcelable(ARG_SUPERVISOR, supervisor);
        GuardiaDisponibleFragment fragment = new GuardiaDisponibleFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_guardia_disponible, container, false);

        // Create the adapter
        mAdapter = new GuardiaDisponibleAdapter(getContext(),mSupervisor);

        //Capture the recyclerView
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(GuardiaDisponibleFragment.this.getContext()));

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem mSearchMenuItem = menu.findItem(R.id.action_search);
        MenuItem mRefreshMenuItem = menu.findItem(R.id.action_refresh);

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Perform final Search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                newText = newText.toLowerCase();
                ArrayList<Guardia> newList = new ArrayList<Guardia>();

                for (Guardia guardia: GuardiaDisponibleAdapter.filterGuardias){
                    String name = guardia.getUsuarioNombre().toLowerCase();

                    if (name.contains(newText)){
                        newList.add(guardia);
                    }
                }

                mAdapter.setFilter(newList);

                return false;
            }
        });

        mRefreshMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                mAdapter.setGuardiaDisponibles();
                Toast.makeText(getContext(),"Actualizado",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}
