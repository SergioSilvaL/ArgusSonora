package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraRegistro;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.adapter.BitacoraRegistroAdapter;
import com.tecnologiasintech.argussonora.presentation.adapter.BitacoraRegistroNoResueltoAdapter;

public class BitacoraRegistroActivity extends AppCompatActivity implements BitacoraRegistroAdapter.Callback, BitacoraRegistroNoResueltoAdapter.Callback {

    BitacoraRegistroAdapter mBitacoraRegistroAdapter;
    BitacoraRegistroNoResueltoAdapter mBitacoraRegistroNoResueltoAdapter;
    private static final String TAG = BitacoraRegistroActivity.class.getSimpleName();

    private Supervisor mSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mSupervisor = intent.getParcelableExtra(MainActivity.EXTRA_SUPERVISOR);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddEditDialog(null,true);
            }
        });

        mBitacoraRegistroAdapter = new BitacoraRegistroAdapter(this, this, mSupervisor);
        RecyclerView recyclerviewBitacoraRegistro = (RecyclerView) findViewById(R.id.recyclerViewBitacoraRegistro);
        recyclerviewBitacoraRegistro.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewBitacoraRegistro.setHasFixedSize(true);
        recyclerviewBitacoraRegistro.setAdapter(mBitacoraRegistroAdapter);

        mBitacoraRegistroNoResueltoAdapter = new BitacoraRegistroNoResueltoAdapter(this, this, mSupervisor);
        RecyclerView recyclerviewBitacoraRegistroNoResuelto = (RecyclerView) findViewById(R.id.recyclerViewBitacoraRegistroNoResuelto);
        recyclerviewBitacoraRegistroNoResuelto.setLayoutManager(new LinearLayoutManager(this));
        recyclerviewBitacoraRegistroNoResuelto.setHasFixedSize(true);
        recyclerviewBitacoraRegistroNoResuelto.setAdapter(mBitacoraRegistroNoResueltoAdapter);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int semaforoStatus = 1;

    public void onRadioButtonClickedBitacoraRegistro(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was created
        switch (view.getId()) {
            case R.id.radioButtonBitacoraRegistroStatusBAJA:
                if (checked)
                    semaforoStatus = 1;
                break;

            case R.id.radioButtonBitacoraRegistroStatusMEDIA:
                if (checked) semaforoStatus = 2;
                break;

            case R.id.radioButtonBitacoraRegistroStatusALTA:
                semaforoStatus = 3;
                break;
        }
        Log.v(TAG, String.valueOf(semaforoStatus));
    }

    private void showAddEditDialog(final BitacoraRegistro bitacoraRegistro, final Boolean isBitacoraRegistro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(bitacoraRegistro == null ? R.string.dialog_bitacora_registro_add_title : R.string.dialog_bitacora_registro_edit_title);
        //Todo: Rename dialog
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        builder.setView(view);
        final EditText observacionEditText = (EditText) view.findViewById(R.id.dialog_add_observacion_text);
        semaforoStatus = 1;// Sets default semaforo Value


        if (bitacoraRegistro != null) {
            // pre-populate
            observacionEditText.setText(bitacoraRegistro.getDescripcion());
        }

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (bitacoraRegistro == null) {
                    String observacion = observacionEditText.getText().toString();
                    mBitacoraRegistroAdapter.add(new BitacoraRegistro(observacion, semaforoStatus,
                            mSupervisor.getFullName(),
                            mSupervisor.getZone(),
                            new DatePost().getDatePost()));
                } else {
                    if (isBitacoraRegistro) {
                        mBitacoraRegistroAdapter.update(bitacoraRegistro, observacionEditText.getText().toString(), semaforoStatus);
                    }else {
                        mBitacoraRegistroNoResueltoAdapter.update(bitacoraRegistro, observacionEditText.getText().toString(), semaforoStatus);
                    }
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);

        builder.create().show();
    }


    @Override
    public void onAddEdit(final BitacoraRegistro bitacoraRegistro) {
        showAddEditDialog(bitacoraRegistro, true);
    }

    public void onEdit(final BitacoraRegistro bitacoraRegistro){
        showAddEditDialog(bitacoraRegistro, false);
    }

}





