package com.tecnologiasintech.argussonora.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.tecnologiasintech.argussonora.data.local.IUserPrefs;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Single;

public class DataRepository implements IDataRepository{

    private DatabaseReference databaseReference;
    private IUserPrefs sharedPreferences;

    public DataRepository(IUserPrefs sharedPreferences, DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Observable<List<Cliente>> getClientFromZone(String zone) {
        List<Cliente> clients = new ArrayList<>();

        return RxFirebaseDatabase.observeSingleValueEvent(
                databaseReference.child("Zonas").child(zone).child("zonaClientes"),
                dataSnapshot -> {
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        try {
                            Cliente client = data.getValue(Cliente.class);
                            clients.add(client);
                        } catch (NullPointerException npe) {}
                    }

                    // Order before Returning
                    Collections.sort(clients, (left, right) ->
                            left.getClienteNombre().compareTo(right.toString()));

                    return clients;

                }).doOnError(throwable -> {
                    // TODO: show Error Message
        });
    }

    @Override
    public Observable<Supervisor> getSupervisorFromEmail(final String email) {
        return RxFirebaseDatabase.observeSingleValueEvent(databaseReference.child("supervisores"),
                dataSnapshot -> {

                    Supervisor mSupervisor = null;

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Supervisor supervisor = data.getValue(Supervisor.class);
                        if (email.equals(supervisor.getEmail())) {
                            mSupervisor =  supervisor;
                        }
                    }
                    return mSupervisor;
                })
                .doOnError(throwable -> {throw new RuntimeException("Error de conexion");});
    }

    @Override
    public void saveSupervisorEmailIntoPreferences(String email) {
        sharedPreferences.setSupervisorEmail(email);
    }

    @Override
    public Single<String> getSupervisorEmailFromPreferences() {
        return Single.just(sharedPreferences.getSupervisorEmail());
    }

    @Override
    public void saveSupervisorZoneIntoPreferences(String zone) {
        sharedPreferences.setSupervisorZone(zone);
    }

    @Override
    public Single<String> getSupervisorZoneFromPreferences() {
        return Single.just(sharedPreferences.getSupervisorZone());
    }
}
