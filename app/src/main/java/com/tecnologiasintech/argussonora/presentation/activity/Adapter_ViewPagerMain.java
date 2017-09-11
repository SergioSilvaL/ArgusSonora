package com.tecnologiasintech.argussonora.presentation.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tecnologiasintech.argussonora.domain.ModelObjects.Supervisor;
import com.tecnologiasintech.argussonora.presentation.ClienteFragment;


/**
 * Created by Legible on 17/02/2017.
 */

public class Adapter_ViewPagerMain extends FragmentPagerAdapter {

    int mNumberOfTabs;
    private Context mContext;
    private Supervisor mSupervisor;


    public Adapter_ViewPagerMain(FragmentManager fm, int numberOfTabs, Context mContext, Supervisor supervisor) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
        this.mContext = mContext;
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
                return new GuardiaFragment();

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
