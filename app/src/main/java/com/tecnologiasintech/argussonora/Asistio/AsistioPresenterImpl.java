package com.tecnologiasintech.argussonora.Asistio;

public class AsistioPresenterImpl implements AsistioPresenter{

    private AsistioInteractor mInteractor;
    private AsistioView mAsistioView;


    public AsistioPresenterImpl(AsistioActivity asistioActivity, AsistioInteractorImpl asistioInteractor) {
        mAsistioView = asistioActivity;
        mInteractor = asistioInteractor;
    }


    @Override
    public boolean validateSignature() {
        return false;
    }

    @Override
    public boolean validateImageCapture() {
        return false;
    }

    @Override
    public void onImageCapture(byte[] imageCapture) {

        // Validate and upload Image

        // get Image storage URL
        mInteractor.setImageCapture(imageCapture);

        //  upload data to storage
    }

    @Override
    public void onSignatureCapture(byte[] signatureCapture) {

        // Validate and upload Image

        // get Image storage URL
        mInteractor.setSignatureCapture(signatureCapture);

        //  take picture from camera
    }
}

