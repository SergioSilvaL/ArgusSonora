package com.tecnologiasintech.argussonora.Login;


import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

/**
 * Created by sergiosilva on 2/5/18.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    // Presenter BoilerPlate


    public LoginPresenterImpl(LoginView loginView, LoginInteractorImpl loginInteractor) {
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }

    @Override
    public void validateCredentials(String username, String password) {

        if(loginView != null){
            loginView.showProgress();
        }

        if(isValidEmail(username) && isValidPassword(password)){
            loginInteractor.login(username, password, this, loginView);
        }

    }

    @Override
    public void onDestroy() {
        loginView.hideProgress();
    }

    @Override
    public void getAuthListener() {
        loginInteractor.validateAuthentication(loginInteractor);
    }


    @Override
    public void onUsernameError() {

    }

    @Override
    public void onPasswordError() {

    }

    @Override
    public void onSuccess(Supervisor supervisor) {
        loginView.navigateToHome(supervisor);
    }

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
