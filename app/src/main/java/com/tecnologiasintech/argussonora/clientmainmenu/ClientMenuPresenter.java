package com.tecnologiasintech.argussonora.clientmainmenu;


import com.tecnologiasintech.argussonora.data.DataRepositoryImpl;
import com.tecnologiasintech.argussonora.data.IDataRepository;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;

import java.util.Collections;
import java.util.Comparator;

public class ClientMenuPresenter implements ClientMenuViewPresenterContract.Presenter{

    IDataRepository dataRepository;

    public ClientMenuPresenter() {
        this.dataRepository = new DataRepositoryImpl();
    }

    @Override
    public void loadClients() {

    }

    // TODO: move to presenter
        Collections.sort(mClienteList, new Comparator<Cliente>() {
        @Override
        public int compare(Cliente o1, Cliente o2) {
            return o1.getClienteNombre().compareTo(o2.getClienteNombre());
        }
    });
}
