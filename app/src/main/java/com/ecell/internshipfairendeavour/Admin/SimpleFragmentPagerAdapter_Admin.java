package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;



public class SimpleFragmentPagerAdapter_Admin extends FragmentStatePagerAdapter {


    int tabCount;
    String key;

    //Constructor to the class
    public SimpleFragmentPagerAdapter_Admin(FragmentManager fm, int tabCount, String key) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.key = key;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                DescFragment_admin tab1 = new DescFragment_admin();
                Bundle bundle1 = new Bundle();
                bundle1.putString("key",key);
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
              Desc1Fragment_admin tab2 = new Desc1Fragment_admin();
                Bundle bundle2 = new Bundle();
                bundle2.putString("key",key);
                tab2.setArguments(bundle2);
                return tab2;
            case 2:
                Desc2Fragment_admin tab3 = new Desc2Fragment_admin();
                Bundle bundle3 = new Bundle();
                bundle3.putString("key",key);
                tab3.setArguments(bundle3);
                return tab3;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }




}