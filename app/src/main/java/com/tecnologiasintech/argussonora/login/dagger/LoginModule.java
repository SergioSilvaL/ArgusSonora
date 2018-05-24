package com.tecnologiasintech.argussonora.login.dagger;


import com.tecnologiasintech.argussonora.data.IDataRepository;
import com.tecnologiasintech.argussonora.login.LoginPresenter;
import com.tecnologiasintech.argussonora.login.LoginViewPresenterContract;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    LoginViewPresenterContract.Presenter provideLoginPresenter(IDataRepository dataRepository) {
        return new LoginPresenter(dataRepository);
    }
}
