package com.tecnologiasintech.argussonora.clientmainmenu;

import com.tecnologiasintech.argussonora.common.BaseViewPresenterContract;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Client;

import java.util.List;

import io.reactivex.Observable;

public interface ClientMenuViewPresenterContract {

    interface View extends BaseViewPresenterContract.View{
        void onSuccessLoad(List<Client> clients);

        void onErrorLoad();

        Observable<String> getItemClientObservable();

        void onItemSelected(String client);
    }

    interface Presenter extends BaseViewPresenterContract.Presenter{

        void loadClients();

        void loadClientSelected();

        void setView(ClientMenuViewPresenterContract.View view);

        void dropView();
    }
}
