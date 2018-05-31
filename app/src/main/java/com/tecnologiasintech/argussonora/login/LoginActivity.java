package com.tecnologiasintech.argussonora.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.activity.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginViewPresenterContract.View, View.OnClickListener{

    public static final String EXTRA_SUPERVISOR = "EXTRA_SUPERVISOR";

    private ProgressBar progressBar;
    private EditText inputEmail;
    private EditText inputPassword;
    private Button buttonLogin;

    private LoginViewPresenterContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // TODO: Use Butterknife
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        buttonLogin = (Button) findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(this);

        presenter = new LoginPresenter(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
     public void setUsernameError(String error) {
        inputEmail.setError(error);
    }

    @Override
    public void setPasswordError(String error) {
        inputPassword.setError(error);
    }

    @Override
    public void setError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToHome(Supervisor supervisor) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_SUPERVISOR, supervisor);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        presenter.launchFirebaseLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

}
