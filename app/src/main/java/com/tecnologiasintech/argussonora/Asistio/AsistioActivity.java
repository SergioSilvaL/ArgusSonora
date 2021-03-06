
package com.tecnologiasintech.argussonora.Asistio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.BitacoraSimple;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;
import com.tecnologiasintech.argussonora.presentation.activity.GuardiaActivity;
import com.tecnologiasintech.argussonora.presentation.activity.LoggingActivity;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AsistioActivity extends LoggingActivity {

    private FirebaseDatabase firebase = FirebaseDatabase.getInstance();

    public static final String TAG = AsistioActivity.class.getSimpleName();

    public static final int REQUEST_TAKE_PICTURE = 0;
    public static final int PERMISSIONS_REQUEST_CODE = 11;

    private Guardia mGuardia;
    private Cliente mCliente;
    private GuardiaBitacora mBitacora;

    private int listPosition;


    @BindView(R.id.CloseBtn) ImageButton mCloseBtn;
    @BindView(R.id.ContinuarBtn) Button mContinuarBtn;
    @BindView(R.id.LimpiarBtn) Button mLimpiarBtn;
    @BindView(R.id.nameLabel) TextView mNameLabel;
    @BindView(R.id.clientLabel) TextView mClientLabel;
    @BindView(R.id.signaturePad) SignaturePad mSignaturePad;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    public AsistioActivity(){
        setActivityName(AsistioActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistio);
        ButterKnife.bind(this);

        // Get Data From intent

        Intent intent = getIntent();

        Log.i(TAG, "Getting Info from intent");

        // Get Guardia Intent
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA) != null) {
            mGuardia = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA);
            Log.i(TAG, mGuardia.toString());

            mNameLabel.setText(mGuardia.getUsuarioNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE) != null){
            mCliente = intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE);
            Log.i(TAG, mCliente.toString());
            mClientLabel.setText(mCliente.getClienteNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA) != null){
            mBitacora = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA);
        }

        listPosition = intent.getIntExtra(ClienteActivity.EXTRA_LIST_POSITION, 0);


        Log.i(TAG, "-------------------------");



    }


    /**
     * Called When the picture is taken
     * **/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_TAKE_PICTURE){
            if (data != null){

                // Upload Image(s) and data to Firebase Database
                disableViews();
                Uri imageUri = data.getData();
                uploadData(imageUri);
            }
        }
    }

    private void disableViews() {
        mSignaturePad.setEnabled(false);
        mCloseBtn.setEnabled(false);
        mContinuarBtn.setVisibility(View.GONE);
        mLimpiarBtn.setVisibility(View.GONE);
    }

    @OnClick(R.id.CloseBtn)
    public void close(){
        Log.i(TAG, "Close Button Clicked");
        finish();
    }

    @OnClick(R.id.ContinuarBtn)
    public void continuar(){


        if (mSignaturePad.isEmpty()){
            Toast.makeText(this, "Favor, de Firmar antes de continuar", Toast.LENGTH_LONG).show();
        }else {
            takePicture();
        }
    }

    private void takePicture(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CODE);
            }else {
                takePictureUsingIntent();
            }
        }else {
            takePictureUsingIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePictureUsingIntent();
            }
        }
    }

    private void takePictureUsingIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PICTURE);
    }

    @OnClick(R.id.LimpiarBtn)
    public void limpiar(){
        mSignaturePad.clear();
    }

    public void uploadData(Uri imageUri){

        final UploadTask uploadTask = getStorageReference().putBytes(getDataFromSignaturePadAsBytes());

        if (imageUri != null){

            UploadTask uploadSelfieTask = getStorageSelfieReference().putFile(imageUri);

            uploadSelfieTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pushSelfieUrl(taskSnapshot.getDownloadUrl().toString());
                    mProgressBar.setProgress(40);
                }
            });
        }

        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Upload Data

                mProgressBar.setProgress(70);

                Log.i(TAG, taskSnapshot.getDownloadUrl().toString());
                // push Data
                Log.i(TAG, "push Data");

                // 1.
                pushBitacora(taskSnapshot.getDownloadUrl().toString());
                mProgressBar.setProgress(80);

                // 2.
                pushBitacoraSimple();
                mProgressBar.setProgress(90);

                // 3.
                updateGuardiaArrayList();
                mProgressBar.setProgress(100);

                // update fecha
                updateFechaInfo();

                finish();

            }
        });

    }

    private void pushSelfieUrl(String s) {
        DatabaseReference reference = firebase.getReference("Argus/Bitacora/")
                .child(new DatePost().getDateKey()) // Gets Current Date
                .child(mGuardia.getUsuarioKey());//Get Guardia Key


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/capturaAsistio",s);
        reference.updateChildren(childUpdates);
    }

    private void pushBitacora(String urlPicture){

        DatabaseReference reference = firebase.getReference("Argus/Bitacora/")
                .child(new DatePost().getDateKey()) // Gets Current Date
                .child(mGuardia.getUsuarioKey());//Get Guardia Key


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/asistio",true);
        childUpdates.put("/cliente",mCliente.getClienteNombre());
        String currentDate = new DatePost().getDatePost();
        childUpdates.put("/fecha",currentDate);
        childUpdates.put("/firmaAsistio",urlPicture);
        childUpdates.put("/guardiaNombre",mGuardia.getUsuarioNombre());
        childUpdates.put("/turno",mGuardia.getUsuarioTurno());
        childUpdates.put("/zona",mCliente.getClienteZonaAsignada());
        reference.updateChildren(childUpdates);


    }

    private void pushBitacoraSimple(){
        DatabaseReference reference =
                firebase.getReference(
                        "Argus/Clientes/" +
                                mCliente.getClienteNombre() + "/clienteGuardias")
                        .child(mGuardia.getUsuarioKey())
                        .child("BitacoraSimple")
                        .child(new DatePost().getDateKey());

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/asistio",true);
        childUpdates.put("/fecha", new DatePost().getDateKey());
        // TODO: set Dates
        reference.updateChildren(childUpdates);
    }

    private void updateGuardiaArrayList(){

        // TODO: Update List
        if (mBitacora.getBitacoraSimple() == null){
            // Create new route
            BitacoraSimple bitacoraSimple = new BitacoraSimple();
            bitacoraSimple.setAsistio(true);
            bitacoraSimple.setFecha(new DatePost().getDateKey());
            mBitacora.setBitacoraSimple(bitacoraSimple);
        }else {
            mBitacora.getBitacoraSimple().setAsistio(true);
        }

        Intent resultIntent = new Intent();
        // Data you want to give back
        resultIntent.putExtra(ClienteActivity.EXTRA_LIST_POSITION, listPosition);
        resultIntent.putExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA, mBitacora);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private byte[] getDataFromSignaturePadAsBytes(){


        //Get the data from Signature Pad as bytes
        mSignaturePad.setDrawingCacheEnabled(true);
        mSignaturePad.buildDrawingCache();

        Bitmap signaturePadDrawingCache = mSignaturePad.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        signaturePadDrawingCache.compress(Bitmap.CompressFormat.JPEG,0,baos);

        return baos.toByteArray();
    }

    private StorageReference getStorageReference() {

        // Create a storage reference from our app
        StorageReference storageRef =  FirebaseStorage.getInstance().getReference();

        return  storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())
                .child(mGuardia.getUsuarioKey())
                .child("asistioFirma");
    }

    private StorageReference getStorageSelfieReference(){
        // Create a storage reference from our app
        StorageReference storageRef =  FirebaseStorage.getInstance().getReference();

        return  storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())
                .child(mGuardia.getUsuarioKey())
                .child("capturaAsistio");
    }

    private void updateFechaInfo(){

        DatabaseReference reference =firebase.getReference()
                .child("Argus")
                .child("Bitacora")
                .child(new DatePost().getDateKey());

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/fecha", Long.valueOf(new DatePost().getDateKey()));

        reference.updateChildren(childUpdates);
    }


}