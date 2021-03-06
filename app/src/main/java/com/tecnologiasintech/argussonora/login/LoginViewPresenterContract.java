package com.tecnologiasintech.argussonora.login;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

public interface LoginViewPresenterContract {

    interface View {

        void showProgress();

        void hideProgress();

        void setUsernameError(String error);

        void setPasswordError(String error);

        void setError(String error);

        void navigateToHome(Supervisor supervisor);
    }

    interface Presenter {

        void launchFirebaseLogin(String username, String password);

    }

}
