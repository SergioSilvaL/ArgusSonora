package com.tecnologiasintech.argussonora.Asistio;

/**
 * Created by sergiosilva on 3/4/18.
 */

public interface AsistioPresenter {

    boolean validateSignature();

    boolean validateImageCapture();

    void onImageCapture(byte[] imageCapture);

    void onSignatureCapture(byte[] signatureCapture);

}
