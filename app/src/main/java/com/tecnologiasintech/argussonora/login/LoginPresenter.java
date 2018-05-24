package com.tecnologiasintech.argussonora.login;


import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.tecnologiasintech.argussonora.data.IDataRepository;
import com.tecnologiasintech.argussonora.domain.FirebaseExceptionConstants;


public class LoginPresenter implements LoginViewPresenterContract.Presenter {

    private IDataRepository dataRepository;
    private LoginViewPresenterContract.View view = null;
    private FirebaseAuth auth;

    public LoginPresenter(IDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void launchFirebaseLogin(String username, String password) {
        view.showProgressBar();

        auth = FirebaseAuth.getInstance();
        
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener((task) ->{
                    if (task.isSuccessful()){
                        String supervisorEmail = task.getResult().getUser().getEmail();
                        submitSupervsior(supervisorEmail);
                    }
                    view.dismissProgressBar();
            }).addOnFailureListener((e) -> {
                    if (e instanceof FirebaseException){
                        view.setError(FirebaseExceptionConstants
                                .getFirebaseExceptionConstants(((FirebaseAuthException) e).getErrorCode()));
                    } else {
                        view.setError(e.getMessage());
                    }
            });
    }

    @Override
    public void setView(LoginViewPresenterContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }

    private void submitSupervsior(String supervisorEmail) {
        dataRepository.getSupervisorFromEmail(supervisorEmail)
                .subscribe(
                    supervisor ->
                    {
                        dataRepository.saveSupervisorEmailIntoPreferences(supervisor.email);
                        dataRepository.saveSupervisorZoneIntoPreferences(supervisor.zone);
                        view.navigateToHome(supervisor);
                    }
                , error -> Log.v("RxFirebase", error.getMessage()));
    }

    // TODO: Create email helper class
    private boolean isValidEmail(String email){

        if (!isValidEmailFormat(email)){
            view.setUsernameError("Formato de Correo Invalido");
        }
        if (email.length() == 0) {
            view.setUsernameError("Favor, de ingresar correo");
        }

        return  email.length() != 0 && isValidEmailFormat(email);
    }

    private boolean isValidPassword(String password){

        if (password.length() == 0) {
            view.setPasswordError("Favor de ingresar la contrasena");
        }


        return password.length() > 0;
    }

    private boolean isValidEmailFormat(String email){
        return email.contains("@");
    }
}
