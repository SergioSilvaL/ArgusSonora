package com.tecnologiasintech.argussonora.Login;

import com.google.firebase.auth.FirebaseAuth;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

/**
 * Created by sergiosilva on 2/5/18.
 */

public interface LoginInteractor {

    /**
     * The interface On login finished listener.
     */
    interface OnLoginFinishedListener {
        /**
         * On username error.
         */
        void onUsernameError();

        /**
         * On password error.
         */
        void onPasswordError();

        /**
         * On success.
         */
        void onSuccess(Supervisor supervisor);
    }


    void validateAuthentication(LoginInteractor listener);


    void login(String username, String password, OnLoginFinishedListener listener, LoginView view);



}
