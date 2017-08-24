package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Cliente;

public class MainActivity extends AppCompatActivity{

    public static final String TAG = MainActivity.class.getSimpleName();
    private Cliente mCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(MainActivity.this, ClienteActivity.class);
            startActivity(intent);
            }
        });



//        DatabaseReference clienteReference = FirebaseDatabase
//                .getInstance().getReference("Argus/Clientes/Almacen Zapata");
//
//        clienteReference.addValueEventListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    @Override
//    public void onDataChange(DataSnapshot dataSnapshot) {
//
//    }
//
//    @Override
//    public void onCancelled(DatabaseError databaseError) {
//
//    }
}
