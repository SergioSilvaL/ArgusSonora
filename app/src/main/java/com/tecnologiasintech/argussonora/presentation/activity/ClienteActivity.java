package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;
import com.tecnologiasintech.argussonora.presentation.dialog.AddGuardiaTemporalDialogFragment;
import com.tecnologiasintech.argussonora.presentation.dialog.TutorialViewDialogFragment;

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
    private Supervisor mSupervisor;
    private String mClienteName;
    private List<GuardiaBitacora> mGuardiaBitacora;

    private ActionBar mActionBar;
    @InjectView(R.id.recyclerView) RecyclerView mRecyclerView;

    public ClienteActivity(){
        setActivityName(ClienteActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_guardia);
        ButterKnife.inject(this);

        Intent intent = getIntent();

        mClienteName = intent.getStringExtra(MainActivity.EXTRA_REFERENCE_CLIENTE);
        mSupervisor = intent.getParcelableExtra(MainActivity.EXTRA_SUPERVISOR);


        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        getCliente();


        Log.i(TAG, "Main UI Code is running");
    }

    private void showGuardiaTemporalDialog() {
        FragmentManager fm = getSupportFragmentManager();
        AddGuardiaTemporalDialogFragment addGuardiaTemporalDialogFragment =
                AddGuardiaTemporalDialogFragment.newInstance(mSupervisor, mCliente);
        addGuardiaTemporalDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cliente, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                finish();
                return true;

            case R.id.action_add_guardia:
                showGuardiaTemporalDialog();
                return true;

            case R.id.action_consigna:
                openConsigna();
                return true;

            case R.id.action_tutorial:
                openTutorial();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void openTutorial() {
        TutorialViewDialogFragment df = new TutorialViewDialogFragment();

        df.show(getSupportFragmentManager(),"dialog_fragment_tutorial");
    }

    private void openConsigna(){

    }

    public void getCliente(){

        Log.i(TAG, "Fetching data client");

        FirebaseDatabase firebase = FirebaseDatabase.getInstance();


        DatabaseReference reference = firebase.getReference("Argus/Clientes")
                .child(mClienteName);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mCliente = dataSnapshot.getValue(Cliente.class);
                Log.i(TAG, mCliente.toString());

                // Set Titlebar
                if (mActionBar != null)
                mActionBar.setTitle(mCliente.getClienteNombre());

                // Get Guardia Bitacora
                // TODO: Create method and Test!

                mGuardiaBitacora = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.child("clienteGuardias").getChildren()){
                    try {
                        GuardiaBitacora guardiaBitacora = snapshot.getValue(GuardiaBitacora.class);

                        // Get the inner Simple Bitacora
                        for (DataSnapshot snapshot1: snapshot.child("BitacoraSimple").getChildren()){
                            if (snapshot1.getKey().equals(new DatePost().getDateKey())){
                                BitacoraSimple bitacoraSimple = snapshot1.getValue(BitacoraSimple.class);
                                guardiaBitacora.setBitacoraSimple(bitacoraSimple);
                            }
                        }
                        mGuardiaBitacora.add(guardiaBitacora);

                    }catch (DatabaseException dbe){
                        Log.e(TAG, dbe.getMessage());
                    }
                }

                updateAdapter(mCliente, mGuardiaBitacora);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Alert User
                Log.i(TAG, databaseError.getMessage());
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