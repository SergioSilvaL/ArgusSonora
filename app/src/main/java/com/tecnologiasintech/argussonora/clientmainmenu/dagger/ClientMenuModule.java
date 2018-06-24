package com.tecnologiasintech.argussonora.clientmainmenu.dagger;

import com.tecnologiasintech.argussonora.clientmainmenu.ClientMenuPresenter;
import com.tecnologiasintech.argussonora.clientmainmenu.ClientMenuViewPresenterContract;
import com.tecnologiasintech.argussonora.data.IDataRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class ClientMenuModule {

    @Provides
    ClientMenuViewPresenterContract.Presenter provideClientMenuView (IDataRepository dataRepository) {
        return new ClientMenuPresenter(dataRepository);
    }

}
