package com.tecnologiasintech.argussonora.clientmainmenu;


import com.tecnologiasintech.argussonora.data.DataRepository;
import com.tecnologiasintech.argussonora.data.IDataRepository;


public class ClientMenuPresenter implements ClientMenuViewPresenterContract.Presenter{

    IDataRepository dataRepository;

    public ClientMenuPresenter() {
        this.dataRepository = new DataRepository(null, null);
    }

    @Override
    public void loadClients() {

    }

    // TODO: move to presenter
//        Collections.sort(mClienteList, new Comparator<Cliente>() {
//        @Override
//        public int compare(Cliente o1, Cliente o2) {
//            return o1.getClienteNombre().compareTo(o2.getClienteNombre());
//        }
//    });
}
