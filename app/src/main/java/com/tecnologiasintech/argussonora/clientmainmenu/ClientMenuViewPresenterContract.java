package com.tecnologiasintech.argussonora.clientmainmenu;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;

import java.util.List;

public interface ClientMenuViewPresenterContract {

    interface View {
        void onSuccessload(List<Cliente> clients);

        void onErrorLoad();
    }

    interface Presenter {
        void loadClients();
    }
}
