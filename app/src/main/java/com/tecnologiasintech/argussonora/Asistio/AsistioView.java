package com.tecnologiasintech.argussonora.Asistio;

import android.graphics.Bitmap;

import com.tecnologiasintech.argussonora.domain.ModelObjects.GuardiaBitacora;

import java.io.File;

/**
 * Created by sergiosilva on 3/4/18.
 */

public interface AsistioView {

    void showProgress();

    void hideProgress();

    void showError(String error);

    void disableViews();

    void clear();

    void Continue();

    void setGuardiaName(String name);

    void setClienteName(String name);

    byte[] getSignatureFromPad();

    byte[] getPictureFromCamera(Bitmap bitmap);

    void takePictureFromCamera();

    void resultIntentClient(GuardiaBitacora bitacora);

}
