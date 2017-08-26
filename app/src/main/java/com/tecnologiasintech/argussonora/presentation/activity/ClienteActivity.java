package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ClienteActivity extends LoggingActivity {


    public static final String TAG = ClienteActivity.class.getSimpleName();

    public static final String EXTRA_CLIENTE = "EXTRA_CLIENTE";
    public static final String EXTRA_GUARDIA_BITACORA = "EXTRA_GUARDIA_BITACORA";
    public static final String EXTRA_LIST_POSITION = "EXTRA_LIST_POSITION";
    public static final int REQUEST_FAVORITE = 0;

    private GuardiaAdapter mAdapter;
    private Cliente mCliente;
    private List<GuardiaBitacora> mGuardiaBitacora;

    @InjectView(R.id.recyclerView) RecyclerView mRecyclerView;

    public ClienteActivity(){
        setActivityName(ClienteActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_guardia);
        ButterKnife.inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        getCliente();


        Log.i(TAG, "Main UI Code is running");
    }

    public void getCliente(){

        Log.i(TAG, "Fetching data client");

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();

        DatabaseReference reference = firebase.getReference("Argus/Clientes/All");

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mCliente = dataSnapshot.getValue(Cliente.class);
                Log.i(TAG, mCliente.toString());


                // Get Guardia Bitacora

                mGuardiaBitacora = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.child("clienteGuardias").getChildren()){
                    GuardiaBitacora guardiaBitacora = snapshot.getValue(GuardiaBitacora.class);
                    //Log.i(TAG, guardiaBitacora.toString());
                    mGuardiaBitacora.add(guardiaBitacora);
                }

                updateAdapter(mCliente, mGuardiaBitacora);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Alert User
            }
        });

    }

    private void updateAdapter(Cliente cliente, List<GuardiaBitacora> guardiaBitacora) {
        mAdapter = new GuardiaAdapter(this, cliente, guardiaBitacora);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FAVORITE){
            if (resultCode == RESULT_OK){
                getCliente();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}