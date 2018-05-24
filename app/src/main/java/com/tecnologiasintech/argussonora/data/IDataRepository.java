package com.tecnologiasintech.argussonora.data;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import java.util.List;

import rx.Observable;
import rx.Single;

public interface IDataRepository {

    Observable<List<Cliente>> getClientFromZone(String zone);

    Observable<Supervisor> getSupervisorFromEmail(String email);

    void saveSupervisorEmailIntoPreferences(String email);

    Single<String> getSupervisorEmailFromPreferences();

    void saveSupervisorZoneIntoPreferences(String zone);

    Single<String> getSupervisorZoneFromPreferences();


}
