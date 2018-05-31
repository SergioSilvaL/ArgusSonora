package com.tecnologiasintech.argussonora.data;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

import rx.Observable;

public class DataRepositoryImpl implements IDataRepository{

    private static DatabaseReference databaseReference;

    static {
        databaseReference = FirebaseDatabase.getInstance().getReference("Argus");
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
    public void saveSuperviserIntoPreferences(Supervisor supervisor) {

    }

    @Override
    public Supervisor getSupervisorFromPreferences() {
        return null;
    }
}
