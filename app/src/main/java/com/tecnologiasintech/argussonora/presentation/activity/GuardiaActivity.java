package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.asistio.AsistioActivity;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuardiaActivity extends LoggingActivity implements ValueEventListener{

    /**
     * We use this key to reference the list of messages in Firebase.
     */

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
            firebase.getReference("Argus/guardias");
    public static final String EXTRA_GUARDIA = "EXTRA_GUARDIA";
    public static final String EXTRA_CLIENTE = "EXTRA_CLIENTE";
    public static final String EXTRA_LIST_POSITION = "EXTRA_LIST_POSITION";
    public static final String EXTRA_GUARDIA_BITACORA = "EXTRA_GUARDIA_BITACORA";

    private Guardia mGuardia;
    private Client mClient;
    private GuardiaBitacora mBitacora;
    private int listPosition;

    @BindView(R.id.nameLabel) TextView mNameLabel;
    @BindView(R.id.phoneValue) TextView mPhoneValue;
    @BindView(R.id.domicilioLabel) TextView mDomicilioLabel;
    @BindView(R.id.turnoLabel) TextView mTurnoLabel;
    @BindView(R.id.btnCapturaAsistencia) LinearLayout mAsistenciaButton;
    @BindView(R.id.btnCapturaInAsistencia) LinearLayout mInAsistenciaButton;
    @BindView(R.id.btnCapturaCubreDescanso) LinearLayout mCubreDescansoButton;
    @BindView(R.id.btnCapturaDobleTurno) LinearLayout mDobleTurnoButton;
    @BindView(R.id.btnCapturaHorasExtra) LinearLayout mHorasExtraButton;
    @BindView(R.id.cdvCapturarAsistencia) CardView mAsistenciaCardView;
    @BindView(R.id.cdvCapturarInasistencia) CardView mInAsistenciaCardView;
    @BindView(R.id.cdvCapturarCubreDescanso) CardView mCubreDescansoCardView;



    public GuardiaActivity(){
        setActivityName(GuardiaActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardia);
        ButterKnife.bind(this);

        // Use Butterknife to set the views with fewer set of lines

        Intent intent = getIntent();

        if (intent.getParcelableExtra(ClienteActivity.EXTRA_CLIENTE) != null){
            mClient = intent.getParcelableExtra(ClienteActivity.EXTRA_CLIENTE);
            Log.i(TAG, mClient.toString());
        }

        if (intent.getParcelableExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA) != null){
            mBitacora = intent.getParcelableExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA);
        }

        listPosition = intent.getIntExtra(ClienteActivity.EXTRA_LIST_POSITION, 0);



        // Load Firebase Date
        GuardiaReference.child(mBitacora.getUsuarioKey()).addValueEventListener(this);

    }

    @OnClick(R.id.moveButton)
    public void moveGuardia(){
        Log.i(TAG, "Move Guardia");

        Intent intent = new Intent(this, MoveGuardiaActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        // Send Line position ?

        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnCapturaAsistencia)
    public void capturarAsistencia(){
        Log.i(TAG, "Captura Asistencia");

        Intent intent = new Intent(this, AsistioActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        intent.putExtra(EXTRA_LIST_POSITION, listPosition);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();

    }

    @OnClick(R.id.btnCapturaInAsistencia)
    public void capturarInAsistencia(){
        Log.i(TAG, "Captura InAsistencia");

        Intent intent = new Intent(this, InAsistenciaActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        intent.putExtra(EXTRA_LIST_POSITION, listPosition);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnCapturaDobleTurno)
    public void capturarDobleTurno(){
        Log.i(TAG, "Captura Doble Turno");

        Intent intent = new Intent(this, DobleTurnoActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        intent.putExtra(EXTRA_LIST_POSITION, listPosition);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnCapturaCubreDescanso)
    public void capturarCubreDescanso(){
        Log.i(TAG, "Captura Cubre Descanso");

        Intent intent = new Intent(this, CubreDescansoActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        intent.putExtra(EXTRA_LIST_POSITION, listPosition);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btnCapturaHorasExtra)
    public void capturarHorasExtra(){
        Log.i(TAG, "Captura Horas Extra");
        Intent intent = new Intent(this, HorasExtraActivity.class);
        intent.putExtra(EXTRA_GUARDIA, mGuardia);
        intent.putExtra(EXTRA_CLIENTE, mClient);
        intent.putExtra(EXTRA_GUARDIA_BITACORA, mBitacora);
        intent.putExtra(EXTRA_LIST_POSITION, listPosition);
        intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        startActivity(intent);
        finish();

    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        // TODO: Add Try Clause to handle Json Parsing
        mGuardia = dataSnapshot.getValue(Guardia.class);
        Log.i(TAG, mGuardia.toString());

        // Update Views
        mNameLabel.setText(mGuardia.getUsuarioNombre());
        mPhoneValue.setText(mGuardia.getUsuarioTelefono() + "");
        mDomicilioLabel.setText(mGuardia.getUsuarioDomicilio());
        if (mGuardia.getUsuarioTurno()!= null) {
            mTurnoLabel.setText(mGuardia.getUsuarioTurno());
        }

        if (mGuardia.isDiaDescanso(mGuardia.getDiaDescanso())){
            Log.v(TAG, "Hoy descansa " + mGuardia.getUsuarioNombre());
            // Disable Asistio y InAsistencia
            mAsistenciaButton.setVisibility(View.GONE);
            mAsistenciaCardView.setVisibility(View.GONE);
            mInAsistenciaButton.setVisibility(View.GONE);
            mInAsistenciaCardView.setVisibility(View.GONE);
        }else{
            Log.v(TAG, "Hoy NO descansa " + mGuardia.getUsuarioNombre());
            // Disable Cubre Descanso
            mCubreDescansoButton.setVisibility(View.GONE);
            mCubreDescansoCardView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

        Toast.makeText(GuardiaActivity.this,
                "Oops. Error, intente de nuevo", Toast.LENGTH_LONG).show();

    }
}
