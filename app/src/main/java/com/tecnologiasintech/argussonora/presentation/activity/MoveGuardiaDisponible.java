package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.tecnologiasintech.argussonora.mainmenu.MainActivity;
import com.tecnologiasintech.argussonora.presentation.adapter.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sergiosilva on 9/14/17.
 */

public class MoveGuardiaDisponible extends AppCompatActivity {

    private static final String TAG = MoveGuardiaActivity.class.getSimpleName();
    private Guardia mGuardia;
    private GuardiaBitacora mGuardiaBitacora;
    private Client mClient;
    private ExpandableListAdapter listAdapter;
    private List <String> listDataHeader;
    private HashMap <String, List<String>> listDataChild;
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
        if (intent.getParcelableExtra(MainActivity.EXTRA_GUARDIA_DISPONIBLE) != null) {
            mGuardia = intent.getParcelableExtra(MainActivity.EXTRA_GUARDIA_DISPONIBLE);
            //Log.i(TAG, mGuardia.toString());
        }

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                String client = listDataChild.get(listDataHeader.get(groupPosition))
                        .get(childPosition);

                Toast.makeText(
                        getApplicationContext(),
                        (mGuardia.getUsuarioNombre() + " a sido asignado a " + client),
                        Toast.LENGTH_SHORT)
                        .show();

                updateInformation(client);

                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();

                return false;
            }
        });

    }

    private void updateInformation(String client) {

        // Change Guardia Status
        updateGuardiaClienteValues(client);
        // Move to new Client
        addToNewClient(client);

    }


    private void addToNewClient(String client) {

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Argus/Clientes").child(client).child("clienteGuardias");

        mGuardiaBitacora = new GuardiaBitacora();
        mGuardiaBitacora.setUsuarioKey(mGuardia.getUsuarioKey());
        mGuardiaBitacora.setUsuarioNombre(mGuardia.getUsuarioNombre());

        reference.child(mGuardia.getUsuarioKey()).setValue(mGuardiaBitacora);

    }

    private void updateGuardiaClienteValues(String client) {
        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("Argus/guardias")
                .child(mGuardia.getUsuarioKey());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/usuarioDisponible", false);
        childUpdates.put("usuarioClienteAsignado", client);
        reference.updateChildren(childUpdates);

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
                        MoveGuardiaDisponible.this, listDataHeader, listDataChild);



                // setting list adapter
                expListView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
