package com.tecnologiasintech.argussonora.clientmainmenu.dagger;

import com.tecnologiasintech.argussonora.clientmainmenu.ClientMenuView;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Singleton
@Subcomponent(modules = ClientMenuModule.class)
public interface ClientMenuComponent {
    void inject(ClientMenuView target);
}
