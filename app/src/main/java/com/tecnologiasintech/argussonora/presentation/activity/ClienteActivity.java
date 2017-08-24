package com.tecnologiasintech.argussonora.presentation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;

public class ClienteActivity extends AppCompatActivity implements ValueEventListener{

    public static final String TAG = ClienteActivity.class.getSimpleName();
    public static final String EXTRA_CLIENTE = "EXTRA_CLIENTE";
    private GuardiaAdapter mAdapter;
    private Cliente mCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference clienteReference = FirebaseDatabase
                .getInstance().getReference("Argus/Clientes/Almacen Zapata");

        clienteReference.addValueEventListener(this);

        setContentView(R.layout.activity_cliente_guardia);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);



        mAdapter = new GuardiaAdapter(this);
        recyclerView.setAdapter(mAdapter);



    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mCliente = dataSnapshot.getValue(Cliente.class);
        mAdapter.setCliente(mCliente);
        Log.i(TAG, mCliente.toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}