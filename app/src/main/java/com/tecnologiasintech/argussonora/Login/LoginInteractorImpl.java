package com.tecnologiasintech.argussonora.Login;

import android.support.annotation.NonNull;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tecnologiasintech.argussonora.domain.FirebaseExceptionConstants;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;


public class LoginInteractorImpl implements LoginInteractor{



    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    public void validateAuthentication(LoginInteractor listener) {
        //getSupervisor(listener);
    }

    @Override
    public void login(String username, String password, final OnLoginFinishedListener listener, final LoginViewPresenterContract.View view) {
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            view.hideProgress();
                            getSupervisor(listener);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // TODO: Check Internet Connecion
                        if (e instanceof FirebaseException){
                            view.setError(FirebaseExceptionConstants
                                    .getFirebaseExceptionConstants(((FirebaseAuthException) e).getErrorCode()));
                        }
                    }
                });
    }

    private void getSupervisor(final OnLoginFinishedListener listener){

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Argus/supervisores");

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Supervisor supervisor = data.getValue(Supervisor.class);
                    if (firebaseUser.getEmail().equals(supervisor.getUsuarioEmail())){
                        listener.onSuccess(supervisor);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //listener.on(databaseError.toString());
            }
        });

    }

}
