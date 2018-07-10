package com.tecnologiasintech.argussonora.clientmainmenu.dagger;

import com.tecnologiasintech.argussonora.clientmainmenu.ClientSelectView;

import dagger.Subcomponent;

@Subcomponent(modules = ClientMenuModule.class)
public interface ClientMenuComponent {
    void inject(ClientSelectView target);
}
