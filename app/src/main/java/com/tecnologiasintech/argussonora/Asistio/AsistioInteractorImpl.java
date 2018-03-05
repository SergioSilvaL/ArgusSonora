package com.tecnologiasintech.argussonora.Asistio;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AsistioInteractorImpl implements AsistioInteractor {

    StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();

    private static final String STORAGE_REFERENCE_ROOT = "Bitacora";
    private static final String STORAGE_REFERENCE_SIGNATURE_CAPTURE = "asistioFirma";
    private static final String STORAGE_REFERENCE_IMAGE_CAPTURE = "capturaAsistio";


    @Override
    public String setSignatureCapture(byte[] imageSignature) {

        // TODO: Upload Image and return link
        final UploadTask task = getStorageReferenceSignatureCapture().putBytes(imageSignature);

        return task.getSnapshot().getDownloadUrl().toString();

    }

    @Override
    public String setImageCapture(byte[] imageCapture) {
        // TODO: Upload Image and return Link
        final UploadTask task = getStorageReferenceImageCapture().putBytes(imageCapture);

        return task.getSnapshot().getDownloadUrl().toString();
    }


    private StorageReference getStorageReferenceSignatureCapture() {
        return  mStorageReference
                .child(STORAGE_REFERENCE_ROOT)
//                .child(new DatePost().getDateKey())
//                .child(mGuardia.getUsuarioKey())
                .child(STORAGE_REFERENCE_SIGNATURE_CAPTURE);
    }

    private StorageReference getStorageReferenceImageCapture(){
        return  mStorageReference
                .child(STORAGE_REFERENCE_ROOT)
//                .child(new DatePost().getDateKey())
//                .child(mGuardia.getUsuarioKey())
                .child(STORAGE_REFERENCE_IMAGE_CAPTURE);
    }

}
