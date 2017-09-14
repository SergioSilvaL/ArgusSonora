package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.adapter.Adapter_ViewPagerMain;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_REFERENCE_CLIENTE = "EXTRA_REFERENCE_CLIENTE";
    public static final String EXTRA_SUPERVISOR = "EXTRA_SUPERVISOR";
    public static final String EXTRA_GUARDIA_DISPONIBLE = "EXTRA_GUARDIA_DISPONIBLE";
    public static final String KEY_SUPERVISOR = "KEY_SUPERVISOR";
    public static final int REQUEST_GUARDIA_DISPONIBLE = 0;

    private Adapter_ViewPagerMain mAdapter_viewPagerMain;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Supervisor mSupervisor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mSupervisor = intent.getParcelableExtra(SignInActivity.EXTRA_SUPERVISOR);


        //Create costume TabLayour for our main view.
        setTabLayoutMain();

        //Create Costum Adapter for our View Pager in Main.
        setViewPagerMain(mSupervisor);

        Log.d("DEBUG", "Terminamos onCreate");
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

        switch (item.getItemId()){
            case R.id.action_bitacora:
                openBitacora();
                return true;

            case R.id.action_sign_out:
                signOut();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        //TODO if finish() is better practice replace
        startActivity(new Intent(this,SignInActivity.class));
        finish();
    }

    public void openBitacora(){


        Intent intent = new Intent(this, BitacoraRegistroActivity.class);

        intent.putExtra(EXTRA_SUPERVISOR, mSupervisor);
        startActivity(intent);
    }

    public void setTabLayoutMain(){

        // Instanciate a Tab Layout

        mTabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);


        // We also asign a centered gravity.
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Add "Fixed Mode" so  all the tabs are the same size.
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);


        //Added two tabs to tabLayout
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());


    }

    public void setViewPagerMain(Supervisor supervisor){

        //Instantiate a View Pager
        mViewPager = (ViewPager) findViewById(R.id.viewPagerMain);

        /**
         * Create Adapter
         * Pass the Fragment Gestor as an argument
         * add nº of tabs or Sections we have created
         * */

        mAdapter_viewPagerMain =
                new Adapter_ViewPagerMain(
                        getSupportFragmentManager(),
                        mTabLayout.getTabCount(),
                        supervisor);

        //Bind View Pager to the Adapter we just created.

        mViewPager.setAdapter(mAdapter_viewPagerMain);

        //? Enables Swiping
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GUARDIA_DISPONIBLE){
            if (requestCode == requestCode){
                mAdapter_viewPagerMain.mGuardiaDisponibleFragment.mAdapter.setGuardiaDisponibles();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_SUPERVISOR, mSupervisor);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mSupervisor = savedInstanceState.getParcelable(KEY_SUPERVISOR);
    }
}