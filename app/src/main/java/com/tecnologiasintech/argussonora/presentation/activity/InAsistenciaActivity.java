package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InAsistenciaActivity extends LoggingActivity {

    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    public static final String TAG = AsistioActivity.class.getSimpleName();

    private Guardia mGuardia;
    private Cliente mCliente;
    private GuardiaBitacora mBitacora;

    private int listPosition;

    @InjectView(R.id.CancelarBtn) Button mCancelarButton;
    @InjectView(R.id.AceptarBtn)Button mAcpetarButton;
    @InjectView(R.id.editText) EditText mEditText;
    @InjectView(R.id.GuardiatextView) TextView mGuardiaTextView;
    @InjectView(R.id.ClientetextView) TextView mClienteTextView;

    public InAsistenciaActivity(){
        setActivityName(InAsistenciaActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_asistencia);
        ButterKnife.inject(this);

        Intent intent = getIntent();

        Log.i(TAG, "Getting Info from intent");

        // Get Guardia Intent
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA) != null) {
            mGuardia = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA);
            Log.i(TAG, mGuardia.toString());

            mGuardiaTextView.setText(mGuardia.getUsuarioNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE) != null){
            mCliente = intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE);
            Log.i(TAG, mCliente.toString());
            mClienteTextView.setText(mCliente.getClienteNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA) != null){
            mBitacora = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA);
        }

        listPosition = intent.getIntExtra(ClienteActivity.EXTRA_LIST_POSITION, 0);


        Log.i(TAG, "-------------------------");
    }

    @OnClick(R.id.AceptarBtn)
    public void aceptar(){
        uploadData();
    }
    @OnClick(R.id.CancelarBtn)
    public void cancelar(){
        finish();
    }

    private void uploadData() {

        // Upload to Bitacora
        pushBitacora(mEditText.getText().toString());

        // Upload to Cliente Bitacora
        pushBitacoraSimple();

        // Update List
        updateClientList();

        // finish
        finish();

    }

    private void updateClientList() {


        // TODO: Update List
        if (mBitacora.getBitacoraSimple() == null) {
            // Create new route
            BitacoraSimple bitacoraSimple = new BitacoraSimple();
            bitacoraSimple.setAsistio(true);
            bitacoraSimple.setFecha(new DatePost().getDateKey());
            mBitacora.setBitacoraSimple(bitacoraSimple);
        } else {
            mBitacora.getBitacoraSimple().setAsistio(true);
        }

        Intent resultIntent = new Intent();
        // Data you want to give back
        resultIntent.putExtra(ClienteActivity.EXTRA_LIST_POSITION, listPosition);
        resultIntent.putExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA, mBitacora);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private void pushBitacora(String observacion){

        DatabaseReference reference = firebase.getReference("Argus/Bitacora/")
                .child(new DatePost().getDateKey()) // Gets Current Date
                .child(mGuardia.getUsuarioKey());//Get Guardia Key

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/asistio",false);
        childUpdates.put("/cliente",mCliente.getClienteNombre());
        childUpdates.put("/cubredescanso",null);
        childUpdates.put("/dobleturno",null);
        childUpdates.put("/firmaAsistio",null);
        childUpdates.put("/firmaCubreDescanso",null);
        childUpdates.put("/firmaDobleTurno",null);
        childUpdates.put("/firmaHorasExtra",null);

        String currentDate = new DatePost().getDatePost();
        childUpdates.put("/fecha",currentDate);


        childUpdates.put("/observacion",observacion);


        childUpdates.put("/guardiaNombre",mGuardia.getUsuarioNombre());
        childUpdates.put("/horasextra",null);
        childUpdates.put("/turno",mGuardia.getUsuarioTurno());
        childUpdates.put("/zona",mCliente.getClienteZonaAsignada());
        reference.updateChildren(childUpdates);
    }

    private void pushBitacoraSimple(){
        // TODO: replace Clientes with Cliente Object
        DatabaseReference reference =
                firebase.getReference("Argus/Clientes/" +
                        mCliente.getClienteNombre() +
                        "/clienteGuardias").child(mGuardia.getUsuarioKey())
                        .child("BitacoraSimple");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/noasistio",true);
        childUpdates.put("/asistio",false);
        // TODO Set Values
        childUpdates.put("/cubredescanso",false);
        childUpdates.put("/dobleturno",false);
        childUpdates.put("/horasExtra",0);


        childUpdates.put("/fecha", new DatePost().getDateKey());
        // TODO: set Dates
        reference.updateChildren(childUpdates);
    }


}
