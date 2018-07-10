package com.tecnologiasintech.argussonora.mainmenu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.clientmainmenu.ClientSelectView;
import com.tecnologiasintech.argussonora.availableguardmainmenu.GuardiaDisponibleFragment;

public class Adapter_ViewPagerMain extends FragmentPagerAdapter {

    private int mNumberOfTabs;
    private Supervisor mSupervisor;
    public GuardiaDisponibleFragment mGuardiaDisponibleFragment;


    public Adapter_ViewPagerMain(FragmentManager fm, int numberOfTabs, Supervisor supervisor) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
        mSupervisor = supervisor;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
            return new ClientSelectView();

            case 1:
                mGuardiaDisponibleFragment = mGuardiaDisponibleFragment.newInstance(mSupervisor);
                return mGuardiaDisponibleFragment;

            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Servicios";//TODO: mContext.getString(R.string.Client);
            case 1:
                return "Guardias";//TODO: mContext.getString(R.string.guardias);

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
