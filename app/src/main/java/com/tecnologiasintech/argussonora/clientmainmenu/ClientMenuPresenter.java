package com.tecnologiasintech.argussonora.clientmainmenu;


import com.tecnologiasintech.argussonora.common.RxBasePresenter;
import com.tecnologiasintech.argussonora.data.IDataRepository;

import io.reactivex.disposables.Disposable;


public class ClientMenuPresenter extends RxBasePresenter implements ClientMenuViewPresenterContract.Presenter{

    private IDataRepository dataRepository;
    private ClientMenuViewPresenterContract.View view;


    public ClientMenuPresenter(IDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void loadClients() {
        dataRepository.getClientFromZone(dataRepository.getSupervisorZoneFromPreferences().toString())
                .subscribe(clients -> view.onSuccessLoad(clients),
                        error -> view.onErrorLoad());
    }

    @Override
    public void loadClientSelected() {

        Disposable subscription = view.getItemClientObservable()
                .subscribe(clientName -> view.onItemSelected(clientName));

        subscribe(subscription);
    }

    @Override
    public void setView(ClientMenuViewPresenterContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        view = null;
    }
}
