package com.tecnologiasintech.argussonora.login;


import android.util.Log;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.tecnologiasintech.argussonora.data.DataRepositoryImpl;
import com.tecnologiasintech.argussonora.domain.FirebaseExceptionConstants;


public class LoginPresenter implements LoginViewPresenterContract.Presenter {

    private LoginViewPresenterContract.View loginView;

    private static FirebaseAuth authentication;
    private static DataRepositoryImpl dataRepository;

    static {
        // TODO: Inject
        authentication = FirebaseAuth.getInstance();
        dataRepository = new DataRepositoryImpl();
    }


    public LoginPresenter(LoginViewPresenterContract.View loginView) {
        this.loginView = loginView;
    }

    @Override
    public void launchFirebaseLogin(String username, String password) {
        loginView.showProgress();

        authentication.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener((task) ->{
                    if (task.isSuccessful()){
                        String supervisorEmail = task.getResult().getUser().getEmail();
                        submitSupervsior(supervisorEmail);
                    }
                    loginView.hideProgress();
            }).addOnFailureListener((e) -> {
                    if (e instanceof FirebaseException){
                        loginView.setError(FirebaseExceptionConstants
                                .getFirebaseExceptionConstants(((FirebaseAuthException) e).getErrorCode()));
                    }
            });
    }

    private void submitSupervsior(String supervisorEmail) {
        dataRepository.getSupervisorFromEmail(supervisorEmail)
                .subscribe(supervisor -> loginView.navigateToHome(supervisor)
                , error -> Log.v("RxFirebase", error.getMessage()));
    }

    // TODO: Create email helper class
    private boolean isValidEmail(String email){

        if (!isValidEmailFormat(email)){
            loginView.setUsernameError("Formato de Correo Invalido");
        }
        if (email.length() == 0) {
            loginView.setUsernameError("Favor, de ingresar correo");
        }

        return  email.length() != 0 && isValidEmailFormat(email);
    }

    private boolean isValidPassword(String password){

        if (password.length() == 0) {
            loginView.setPasswordError("Favor de ingresar la contrasena");
        }


        return password.length() > 0;
    }

    private boolean isValidEmailFormat(String email){
        return email.contains("@");
    }
}
