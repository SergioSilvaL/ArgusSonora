package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoveGuardiaActivity extends AppCompatActivity {

    private static final String TAG = MoveGuardiaActivity.class.getSimpleName();
    private Guardia mGuardia;
    private GuardiaBitacora mGuardiaBitacora;
    private Client mClient;
    ExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    @BindView(R.id.lvExp) ExpandableListView expListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_guardia);
        ButterKnife.bind(this);

        getData();

        // Get Data From intent

        Intent intent = getIntent();

        Log.i(TAG, "Getting Info from intent");

        // Get Guardia from Intent
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA) != null) {
            mGuardia = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA);
            //Log.i(TAG, mGuardia.toString());
        }

        // Get Guardia Bitacora Intent
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA) != null){
            mGuardiaBitacora = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA);
            //Log.i(TAG, mGuardiaBitacora.toString());
        }
        // Get Client from Intent
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE)!= null){
            mClient = intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE);
            //Log.i(TAG, mClient.toString());
        }


        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                String client = listDataChild.get(listDataHeader.get(groupPosition))
                        .get(childPosition);

                String zona = listDataHeader.get(groupPosition);

                Toast.makeText(
                        getApplicationContext(),
                        (mGuardia.getUsuarioNombre() + " a sido asignado a " + client),
                        Toast.LENGTH_SHORT)
                        .show();

                updateInformation(client, zona);


                finish();


                return false;
            }
        });

    }

    private void updateInformation(String client, String zona){

        // Change Guardia Status
        updateGuardiaClientStatus(client);
        // Errase from current Client
        DeleteFromCurrentClient();
        // Move to new Client
        addToNewClient(client);

    }

    private void addToNewClient(String client) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Argus/Clientes").child(client).child("clienteGuardias");

        reference.child(mGuardia.getUsuarioKey()).setValue(mGuardiaBitacora);

    }

    private void DeleteFromCurrentClient() {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Argus/Clientes").child(mClient.getClienteNombre()).child("clienteGuardias")
                .child(mGuardia.getUsuarioKey());

        reference.setValue(null);

    }

    private void updateGuardiaClientStatus(String client) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Argus/guardias")
                .child(mGuardia.getUsuarioKey())
                .child("usuarioClienteAsignado");

        reference.setValue(client);
    }

    public void getData(){


        DatabaseReference reference = FirebaseDatabase.getInstance().
                getReference("Argus/Zonas");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v(TAG, dataSnapshot.toString());

                listDataHeader = new ArrayList<String>();
                listDataChild = new HashMap<String, List<String>>();

                int pos = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.i(TAG, snapshot.toString());


                    listDataHeader.add(snapshot.getKey());
                    List<String> list = new ArrayList<String>();



                    for (DataSnapshot snapshot1 : snapshot.child("zonaClientes").getChildren()){
                        Log.i(TAG, "Client : " + snapshot1.getKey());
                        list.add(snapshot1.getKey());
                    }


                    listDataChild.put(listDataHeader.get(pos), list);
                    pos ++;

                }

                listAdapter = new ExpandableListAdapter(
                         MoveGuardiaActivity.this, listDataHeader, listDataChild);



                // setting list adapter
                expListView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}



