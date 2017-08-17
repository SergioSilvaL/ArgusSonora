package com.tecnologiasintech.argussonora.presentation.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.guardias;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;

public class ClienteGuardiaActivity extends AppCompatActivity
    implements ChildEventListener{

    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String CLIENTE_FIREBASE_KEY = "Almacen Zapata";

    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    /**
     * Using the key, "messages", we can access a reference to the list of messages. We will be
     * listening to changes to the children of this reference in this Activity.
     */
    private DatabaseReference clienteReference = firebase.getReference().child("Argus").child("Clientes")
            .child(CLIENTE_FIREBASE_KEY).child("clienteGuardias");

    private GuardiaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_guardia);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new GuardiaAdapter(this);
        recyclerView.setAdapter(mAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        clienteReference.addChildEventListener(this);

    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        guardias guardia = dataSnapshot.getValue(guardias.class);
        guardia.setUsuarioKey(dataSnapshot.getKey());

        mAdapter.addGuardia(guardia);
;
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        guardias guardiaRemoved = dataSnapshot.getValue(guardias.class);

        mAdapter.removeGuardia(guardiaRemoved);
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
