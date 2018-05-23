package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
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
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;

/**
 * Created by sergiosilva on 8/16/17.
 */

public class SignInActivity extends AppCompatActivity{

    public static final String EXTRA_SUPERVISOR = "ARG_SUPERVISOR";

    private String email;
    private String password;
    private EditText inputEmail, inputPassword;
    private Button buttonLogin;
    private ProgressBar progressBar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    private String mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check to see if user is alreaedy loggedIn
        checkUserAuth();

        //CheckInternetAuthentication
        checkIntenetAuthentication();

        //Set view
        setView();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    private void checkIntenetAuthentication() {
        //Check if thier is an Internet Connection


        //User No Internet Authentication


        //Thier is Internet
        //No Probelem LogIn Regulary

        //Thier is no Internet

        // User is already logged In

        //--> User Logs in directly but with no Internet Status

        //User wasnt logged In

        //--> User won't be able to log In untill thier is an Internet Connection
    }
    private void setView(){
        //set the view
        setContentView(R.layout.activity_sign_in);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonLogin = (Button) findViewById(R.id.btn_login);
    }
    private boolean isValidEmail(String email){

        if (!isValidEmailFormat(email)){
            //inputEmail.setError(getString(R.string.invalid_format_email));
            mError = getString(R.string.invalid_format_email);
        }
        if (TextUtils.isEmpty(email)) {
            //inputEmail.setError(getString(R.string.empty_email));
            mError = getString(R.string.empty_email);
        }

        return  !TextUtils.isEmpty(email) && isValidEmailFormat(email);
    }
    private boolean isValidPassword(String password){

        if (TextUtils.isEmpty(password)) {
            //inputEmail.setError(getString(R.string.empty_email));
            mError = getString(R.string.empty_password);

        }

        if (!isValidPasswordLength(password)){
            //inputPassword.setError(getString(R.string.invalid_format_password));
            mError = getString(R.string.invalid_format_password);
        }
        return !TextUtils.isEmpty(password) && isValidPasswordLength(password);
    }
    private boolean isValidEmailFormat(String email){
        return email.contains("@");
    }
    private boolean isValidPasswordLength(String password){
        return password.length()>=6;
    }
    private void checkUserAuth(){
        // Log In User Directly if already Signed In

        if(auth.getCurrentUser() != null){
            getSupervisor();
            finish();
        }
    }
    private void authenticateUser(String email, String password){

        //authenticate user

        progressBar.setVisibility(View.VISIBLE);


        auth.signInWithEmailAndPassword(email, password)


                .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            getSupervisor();
                            finish();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);

                        //Todo Check Internet Connection before
                        if (!isConnectedToInternet()){
                            Toast.makeText(SignInActivity.this,
                                    getString(R.string.No_Connection_To_Internet),
                                    Toast.LENGTH_LONG).show();
                        }else

                        if (e instanceof FirebaseAuthException){

                            Toast.makeText(SignInActivity.this,
                                    FirebaseExceptionConstants.getFirebaseExceptionConstants(
                                            ((FirebaseAuthException) e).getErrorCode())
                                    ,Toast.LENGTH_LONG).show();

                        }



//                        String inputErrorField = FirebaseExceptionConstants.getFirebaseExceptionConstantsErrorInputField(
//                                (((FirebaseAuthException) e).getErrorCode()));
//
//
//
//                        if (inputErrorField.equals("EmailField")){
//                            inputEmail.setError(exception);
//                            return;
//                        }
//                        if (inputErrorField.equals("PasswordField")){
//                            inputPassword.setError(exception);
//                            return;
//                        }

                    }
                });
    }

    private void getSupervisor(){

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference("Argus/supervisores");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Supervisor supervisor = data.getValue(Supervisor.class);
                    if (firebaseUser.getEmail().equals(supervisor.getEmail())){
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.putExtra(EXTRA_SUPERVISOR, supervisor);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void signIn(){

        // Get the email from user input
        email = inputEmail.getText().toString().toLowerCase().trim();

        // Get the password from user input
        password = inputPassword.getText().toString();

        if(isValidEmail(email) && isValidPassword(password)){
            authenticateUser(email,password);
        }else{
            Toast.makeText(SignInActivity.this, mError , Toast.LENGTH_LONG).show();
        }
    }

    // Todo: Replace With BroadCast Receiver
    private boolean isConnectedToInternet() {


        ConnectivityManager conMgr = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            return false;
        }else {
            return true;
        }
    }

}
