package com.tecnologiasintech.argussonora.presentation;

import android.content.Context;
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
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.supervisores;
import com.tecnologiasintech.argussonora.presentation.activity.SignInActivity;

import java.util.ArrayList;


/**
 * Created by Legible on 2/17/2017.
 */

public class ClienteFragment extends Fragment {

    private ClienteRecyclerAdapter mAdapter;
    private String zonaSupervisorRef, zonaRef;
    private RecyclerView recyclerView;
    private View view;
    private Context context;
    //Test it out on here
    private static final String TAG = ClienteFragment.class.getSimpleName();


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Get Firebase Reference
    private DatabaseReference mDatabaseReference =
            FirebaseDatabase.getInstance().getReference()
                    .child("Argus")
                    .child("supervisores");

    public ClienteFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        //Inflates the layout for this fragment

        view = inflater.inflate(R.layout.fragment_cliente, container, false);

//        //Capture the recyclerView
//
//        recyclerView = (RecyclerView)
//                view.findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//        recyclerView.setHasFixedSize(true);


        mDatabaseReference.addListenerForSingleValueEvent(new ZonaReferenceEventListener());


        return view;

    }

    class ZonaReferenceEventListener implements ValueEventListener{

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            boolean isSupervisor = false;

            for(DataSnapshot data: dataSnapshot.getChildren()){

                supervisores supervisor = data.getValue(supervisores.class);

                if(user.getEmail().equals(supervisor.getUsuarioEmail())){

                    isSupervisor = true;
                    zonaSupervisorRef = supervisor.getUsuarioNombre();
                    zonaRef = supervisor.getUsuarioZona();
                    ClienteRecyclerAdapter.mySupervisorKey = data.getKey();
                    //Create the Adapter
                    mAdapter = new ClienteRecyclerAdapter(ClienteFragment.this.getContext(), zonaRef, zonaSupervisorRef);
                    //Binding

                    //Capture the recyclerView

                    recyclerView = (RecyclerView)
                            view.findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);

                    recyclerView.setAdapter(mAdapter);

                    return;

                }
            }

            if (!isSupervisor){
                signOut();
            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    public void signOut () {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(),SignInActivity.class);

        //Todo: String recource
        Toast.makeText(getContext(),"Error, Solo los Supervisores tiene acceso al Sistema Movil",Toast.LENGTH_LONG).show();
        startActivity(intent);
        getActivity().finish();

        getActivity().finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

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

                for(Cliente cliente : ClienteRecyclerAdapter.filterClientes){
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