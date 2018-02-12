package com.tecnologiasintech.argussonora.Login;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

/**
 * Created by sergiosilva on 2/5/18.
 */

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setUsernameError(String error);

    void setPasswordError(String error);

    void setError(String error);

    void navigateToHome(Supervisor supervisor);
}
