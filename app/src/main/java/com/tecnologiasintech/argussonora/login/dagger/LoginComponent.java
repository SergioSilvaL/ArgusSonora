package com.tecnologiasintech.argussonora.login.dagger;

import com.tecnologiasintech.argussonora.login.LoginActivity;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Subcomponent(modules = {LoginModule.class})
public interface LoginComponent {
    void inject(LoginActivity target);
}
