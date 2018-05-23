package com.tecnologiasintech.argussonora.data;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Single;

public class DataRepositoryImpl implements IDataRepository{

    private static DatabaseReference databaseReference;
    private Context mContext;
    private SharedPreferences mSharedPreferences = mContext.getSharedPreferences(PREFS_NAME, 0);

    static {
        databaseReference = FirebaseDatabase.getInstance().getReference("Argus");
        String PREFS_NAME = "com.technologiasintech.argussonora.settings";
        String SUPERVISOR_TAG = "Supervisor";
    }

    private DataRepositoryImpl(Context context){
        mContext = context;
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
    public void saveSupervisorIntoPreferences(Supervisor supervisor) {
    }

    @Override
    public Single<Supervisor> getSupervisorFromPreferences() {
        return Single.just(new Supervisor());
    }
}
