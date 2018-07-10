package com.tecnologiasintech.argussonora.mainmenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.tecnologiasintech.argussonora.login.LoginActivity;
import com.tecnologiasintech.argussonora.R;
import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.activity.BitacoraRegistroActivity;
import com.tecnologiasintech.argussonora.mainmenu.adapter.Adapter_ViewPagerMain;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String LINK_PRIVACY_POLICY = "https://argusseguridad-41e35.firebaseapp.com/%23/Privacy&sa=D&usg=AFQjCNF1SdrM3ODzXfSrtH0KQ7Iul20Zyg";

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
        mSupervisor = intent.getParcelableExtra(LoginActivity.EXTRA_SUPERVISOR);

        setTabLayoutMain();
        setViewPagerMain(mSupervisor);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_bitacora:
                openBitacora();
                return true;

            case R.id.action_sign_out:
                signOut();
                return true;

            case R.id.action_privacy_Policy:
                openPrivacyPolicy();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPrivacyPolicy() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_PRIVACY_POLICY));
        startActivity(browserIntent);
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        //TODO if finish() is better practice replace
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }

    public void openBitacora(){
        Intent intent = new Intent(this, BitacoraRegistroActivity.class);
        intent.putExtra(EXTRA_SUPERVISOR, mSupervisor);
        startActivity(intent);
    }

    public void setTabLayoutMain(){
        mTabLayout = (TabLayout) findViewById(R.id.tabLayoutMain);

        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
    }

    public void setViewPagerMain(Supervisor supervisor){
        mViewPager = (ViewPager) findViewById(R.id.viewPagerMain);

        mAdapter_viewPagerMain =
                new Adapter_ViewPagerMain(
                        getSupportFragmentManager(),
                        mTabLayout.getTabCount(),
                        supervisor);

        mViewPager.setAdapter(mAdapter_viewPagerMain);
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