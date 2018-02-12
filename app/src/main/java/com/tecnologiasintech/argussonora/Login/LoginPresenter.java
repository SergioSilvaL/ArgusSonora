package com.tecnologiasintech.argussonora.Login;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by sergiosilva on 2/5/18.
 */

public interface LoginPresenter {

    void validateCredentials(String username, String password);

    void onDestroy();

    void getAuthListener();

}
