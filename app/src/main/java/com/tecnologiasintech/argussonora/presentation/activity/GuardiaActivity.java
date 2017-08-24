package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.adapter.GuardiaAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GuardiaActivity extends AppCompatActivity implements ValueEventListener{

    /**
     * We use this key to reference the list of messages in Firebase.
     */
    public static final String Guardia_FIREBASE_KEY = "-KjPAAGyiRDt7xTdtLj2";
    private static final String TAG = GuardiaActivity.class.getSimpleName();

    /**
     * This is a reference to the root of our Firebase. With this object, we can access any child
     * information in the database.
     */
    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();
    /**
     * Using the key, "messages", we can access a reference to the list of messages. We will be
     * listening to changes to the children of this reference in this Activity.
     */
    private DatabaseReference GuardiaReference =
            firebase.getReference("Argus/guardias").child(Guardia_FIREBASE_KEY);

    public static final String EXTRA_GUARDIA = "EXTRA_GUARDIA";
    public static final String EXTRA_CLIENTE = "EXTRA_CLIENTE";

    private Guardia mGuardia;
    private Cliente mCliente;
    private GuardiaBitacora mBitacora;

    @InjectView(R.id.nameLabel) TextView mNameLabel;
    @InjectView(R.id.phoneValue) TextView mPhoneValue;
    @InjectView(R.id.domicilioLabel) TextView mDomicilioLabel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia);
        ButterKnife.inject(this);

        // Use Butterknife to set the views with fewer set of lines

        Intent intent = getIntent();

        if (intent.getParcelableExtra(ClienteActivity.EXTRA_CLIENTE) != null){
            mCliente = intent.getParcelableExtra(ClienteActivity.EXTRA_CLIENTE);
            Log.i(TAG, mCliente.toString());
        }

        if (intent.getParcelableExtra(GuardiaAdapter.EXTRA_GUARDIA_BITACORA) != null){
            mBitacora = intent.getParcelableExtra(GuardiaAdapter.EXTRA_GUARDIA_BITACORA);
            Log.i(TAG, mBitacora.toString());
        }

        // Load Firebase Date
        GuardiaReference.addValueEventListener(this);

    }

    @OnClick(R.id.btnCapturaAsistencia)
    public void capturarAsistencia(){
        Log.i(TAG, "Captura Asistencia");

        Intent intent = new Intent(this, AsistioActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mCliente);

        startActivity(intent);

    }

    @OnClick(R.id.btnCapturaInAsistencia)
    public void capturarInAsistencia(){
        Log.i(TAG, "Captura InAsistencia");
    }

    @OnClick(R.id.btnCapturaDobleTurno)
    public void capturarDobleTurno(){
        Log.i(TAG, "Captura Doble Turno");
    }

    @OnClick(R.id.btnCapturaCubreDescanso)
    public void capturarCubreDescanso(){
        Log.i(TAG, "Captura Cubre Descanso");
    }

    @OnClick(R.id.btnCapturaHorasExtra)
    public void capturarHorasExtra(){
        Log.i(TAG, "Captura Horas Extra");
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Guardia guardia = dataSnapshot.getValue(Guardia.class);
        Log.i(TAG, guardia.toString());

        mGuardia = guardia;

        // Update Views
        mNameLabel.setText(guardia.getUsuarioNombre());
        mPhoneValue.setText(guardia.getUsuarioTelefono() + "");
        mDomicilioLabel.setText(guardia.getUsuarioDomicilio());

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

        Toast.makeText(GuardiaActivity.this,
                "Oops. Error, intente de nuevo", Toast.LENGTH_LONG).show();

    }
}
