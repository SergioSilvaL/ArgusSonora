
package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.DatePost;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.costumSignaturePad;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.tecnologiasintech.argussonora.R.id.time;

public class AsistioActivity extends AppCompatActivity {

    public static final String TAG = AsistioActivity.class.getSimpleName();
    private Guardia mGuardia;

    SignaturePad mSignaturePad;
    //@InjectView(R.id.CloseBtn) ImageButton mCloseBtn;
    @InjectView(R.id.ContinuarBtn) Button mContinuarBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistio);
        ButterKnife.inject(this);
        mSignaturePad = (SignaturePad) findViewById(R.id.signaturePad);



        Intent intent = getIntent();

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA) != null){
            mGuardia = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA);
            Log.i(TAG, mGuardia.toString());
        }


    }

//    @OnClick(R.id.CloseBtn)
//    public void close(){
//        Log.i(TAG, "Close Button Clicked");
//        finish();
//    }

    @OnClick(R.id.ContinuarBtn)
    public void continuar(){
        uploadData();
    }

    public void uploadData(){

        UploadTask uploadTask = getStorageReference().putBytes(getDataFromSignaturePadAsBytes());


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Upload Data
                Log.i(TAG, taskSnapshot.getDownloadUrl().toString());
                // push Data
                Log.i(TAG, "push Data");
            }
        });

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

        // Create a child reference
        // imagesRef now points to "image"
        if (mGuardia.getUsuarioKey() == null){
            mGuardia.setUsuarioKey("asdf");
        }

        return  storageRef
                .child("Bitacora")
                .child(new DatePost().getDateKey())
                .child("123")
                .child("asistioFirma");
    }

}
