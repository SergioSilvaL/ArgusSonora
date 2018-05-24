package com.tecnologiasintech.argussonora.dagger;

import com.tecnologiasintech.argussonora.clientmainmenu.dagger.ClientMenuComponent;
import com.tecnologiasintech.argussonora.dagger.applicationmodules.AppModule;
import com.tecnologiasintech.argussonora.dagger.applicationmodules.DataModule;
import com.tecnologiasintech.argussonora.dagger.applicationmodules.NetworkModule;
import com.tecnologiasintech.argussonora.login.dagger.LoginComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        DataModule.class
})
public interface AppComponent {
    LoginComponent provideLoginComponent();

    ClientMenuComponent provideClientMenuComponent();
}
