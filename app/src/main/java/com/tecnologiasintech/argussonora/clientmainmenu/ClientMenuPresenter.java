package com.tecnologiasintech.argussonora.clientmainmenu;


import com.tecnologiasintech.argussonora.data.IDataRepository;


public class ClientMenuPresenter implements ClientMenuViewPresenterContract.Presenter{

    private IDataRepository dataRepository;
    private ClientMenuViewPresenterContract.View view;


    public ClientMenuPresenter(IDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void loadClients() {
        dataRepository.getClientFromZone(dataRepository.getSupervisorZoneFromPreferences().toString())
                .subscribe(clientes -> view.onSuccessload(clientes),
                        error -> view.onErrorLoad());
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
