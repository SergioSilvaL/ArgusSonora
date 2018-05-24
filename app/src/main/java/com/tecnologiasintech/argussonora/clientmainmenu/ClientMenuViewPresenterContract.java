package com.tecnologiasintech.argussonora.clientmainmenu;

import com.tecnologiasintech.argussonora.common.BaseViewPresenterContract;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;

import java.util.List;

public interface ClientMenuViewPresenterContract {

    interface View extends BaseViewPresenterContract.View{
        void onSuccessload(List<Client> clients);

        void onErrorLoad();
    }

    interface Presenter {
        void loadClients();

        void setView(ClientMenuViewPresenterContract.View view);

        void dropView();
    }
}
