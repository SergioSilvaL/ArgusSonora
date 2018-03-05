
package com.tecnologiasintech.argussonora.Asistio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Guardia;
import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;
import com.tecnologiasintech.argussonora.presentation.activity.ClienteActivity;
import com.tecnologiasintech.argussonora.presentation.activity.GuardiaActivity;
import com.tecnologiasintech.argussonora.presentation.activity.LoggingActivity;

import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AsistioActivity extends LoggingActivity implements AsistioView {

    private static final String TAG = AsistioActivity.class.getSimpleName();

    private static final int REQUEST_IMAGE_CAPTURE = 0;

    private Guardia mGuardia;
    private Cliente mCliente;
    private GuardiaBitacora mBitacora;

    private int listPosition;

    private AsistioPresenterImpl mPresenter;

    @InjectView(R.id.CloseBtn) ImageButton mCloseBtn;
    @InjectView(R.id.ContinuarBtn) Button mContinuarBtn;
    @InjectView(R.id.LimpiarBtn) Button mLimpiarBtn;
    @InjectView(R.id.nameLabel) TextView mNameLabel;
    @InjectView(R.id.clientLabel) TextView mClientLabel;
    @InjectView(R.id.signaturePad) SignaturePad mSignaturePad;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;

    public AsistioActivity(){
        setActivityName(AsistioActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistio);
        ButterKnife.inject(this);

        getDataFromIntent();

        mPresenter = new AsistioPresenterImpl(this, new AsistioInteractorImpl());
    }

    private void getDataFromIntent(){
        // Get Data From intent
        Intent intent = getIntent();

        // TODO: Move to RepoImpl
        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA) != null) {
            mGuardia = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA);
            Log.i(TAG, mGuardia.toString());
            setGuardiaName(mGuardia.getUsuarioNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE) != null){
            mCliente = intent.getParcelableExtra(GuardiaActivity.EXTRA_CLIENTE);
            Log.i(TAG, mCliente.toString());
            setClienteName(mCliente.getClienteNombre());
        }

        if (intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA) != null){
            mBitacora = intent.getParcelableExtra(GuardiaActivity.EXTRA_GUARDIA_BITACORA);
        }

        listPosition = intent.getIntExtra(ClienteActivity.EXTRA_LIST_POSITION, 0);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.LimpiarBtn)
    @Override
    public void clear() {
        mSignaturePad.clear();
    }

    @OnClick(R.id.ContinuarBtn)
    @Override
    public void Continue() {
        mPresenter.onSignatureCapture(getSignatureFromPad());
    }

    @Override
    public void setGuardiaName(String name) {
        mNameLabel.setText(name);
    }

    @Override
    public void setClienteName(String name) {
        mClientLabel.setText(name);
    }

    @Override
    public void disableViews() {
        mSignaturePad.setEnabled(false);
        mCloseBtn.setEnabled(false);
        mContinuarBtn.setEnabled(false);
        mLimpiarBtn.setEnabled(false);
    }

    @Override
    public byte[] getSignatureFromPad() {
        mSignaturePad.setDrawingCacheEnabled(true);
        mSignaturePad.buildDrawingCache();

        Bitmap signaturePadDrawingCache = mSignaturePad.getDrawingCache();

        return convertBitmapToByteArray(signaturePadDrawingCache);
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,0,baos);

        return baos.toByteArray();
    }

    @Override
    public byte[] getPictureFromCamera(Bitmap bitmap) {
        return  convertBitmapToByteArray(bitmap);
    }

    @Override
    public void takePictureFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }else {
            showError("Error, en Abrir la camara, Favor de Instalar una applicacion de camara");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ){
            if (requestCode == REQUEST_IMAGE_CAPTURE){
                if (data != null){
                    disableViews();
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    mPresenter.onImageCapture(getPictureFromCamera(imageBitmap));
                }
            }
        }
    }

    @Override
    public void resultIntentClient(GuardiaBitacora bitacora) {
        Intent resultIntent = new Intent();

        // Send data back
        resultIntent.putExtra(ClienteActivity.EXTRA_LIST_POSITION, listPosition);
        resultIntent.putExtra(ClienteActivity.EXTRA_GUARDIA_BITACORA, mBitacora);
        setResult(RESULT_OK, resultIntent);

        finish();
    }

    @OnClick(R.id.CloseBtn)
    public void close(){
        finish();
        Log.v(TAG, "Finished Called");
    }
}
