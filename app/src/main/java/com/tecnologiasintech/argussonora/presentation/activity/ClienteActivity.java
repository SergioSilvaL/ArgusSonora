package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
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
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;

public class ClienteActivity extends AppCompatActivity implements ValueEventListener{

    public static final String TAG = ClienteActivity.class.getSimpleName();
    public static final String EXTRA_CLIENTE = "EXTRA_CLIENTE";
    public static final String EXTRA_GUARDIA_BITACORA = "EXTRA_GUARDIA_BITACORA";
    public static final String EXTRA_LIST_POSITION = "EXTRA_LIST_POSITION";
    public static final int REQUEST_FAVORITE = 0;


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

        // 1
        mCliente = dataSnapshot.getValue(Cliente.class);
        // 2
        mAdapter.setCliente(mCliente);
        Log.i(TAG, mCliente.toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FAVORITE){
            if (resultCode == RESULT_OK){


                GuardiaBitacora guardiaBitacora = data.getParcelableExtra(EXTRA_GUARDIA_BITACORA);
                int position = data.getIntExtra(EXTRA_LIST_POSITION, 0);


                Log.i(TAG, guardiaBitacora.toString());
                Log.i(TAG, position + "");
                guardiaBitacora.getBitacoraSimple();
                mAdapter.updateBitacoraSimple(guardiaBitacora.getBitacoraSimple(), position);

            }
        }
    }
}