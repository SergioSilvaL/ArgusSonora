package com.tecnologiasintech.argussonora.presentation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sergiosilva on 9/8/17.
 */

public class Adapter_ViewPagerMain extends FragmentPagerAdapter {

    private int mNumberOfTabs;

    public Adapter_ViewPagerMain(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNumberOfTabs = numberOfTabs;
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

            case 0: // I'll always begin in 0.
                //return new ClienteFragment();

            case 1:
                //return new GuardiaFragment();

            // If the position we receive doesn't correspond to any section.
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
