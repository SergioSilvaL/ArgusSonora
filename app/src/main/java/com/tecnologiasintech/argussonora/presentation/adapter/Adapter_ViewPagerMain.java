package com.tecnologiasintech.argussonora.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.fragment.ClienteFragment;
import com.tecnologiasintech.argussonora.presentation.fragment.GuardiaDisponibleFragment;


/**
 * Created by Legible on 17/02/2017.
 */

public class Adapter_ViewPagerMain extends FragmentPagerAdapter {

    int mNumberOfTabs;
    private Supervisor mSupervisor;
    public GuardiaDisponibleFragment mGuardiaDisponibleFragment;


    public Adapter_ViewPagerMain(FragmentManager fm, int numberOfTabs, Supervisor supervisor) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
        mSupervisor = supervisor;
    }
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {

        /**
         * Get the position from the parameter
         * thanks to the position, we can send the correspondent Fragment to the Section.
         * */
        switch (position) {

            case 0:
            return ClienteFragment.newInstance(mSupervisor);


            case 1:
                mGuardiaDisponibleFragment = mGuardiaDisponibleFragment.newInstance(mSupervisor);
                return mGuardiaDisponibleFragment;

            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        /**
         * Get the position from the parameter
         * thanks to the position, we can send the correspondent tab title
         * */
        switch (position) {

            case 0: // The first tab will always initialize at 0.
                return "Servicios";//mContext.getString(R.string.Cliente);
            case 1:
                return "Guardias";//mContext.getString(R.string.guardias);

            // If the position we receive doesn't correspond to any section.
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
