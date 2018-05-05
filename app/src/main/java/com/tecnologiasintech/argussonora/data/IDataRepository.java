package com.tecnologiasintech.argussonora.data;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import rx.Observable;
import rx.Single;

public interface IDataRepository {

    Observable<Supervisor> getSupervisorFromEmail(String email);

    void saveSuperviserIntoPreferences(Supervisor supervisor);

    Supervisor getSupervisorFromPreferences();
}
