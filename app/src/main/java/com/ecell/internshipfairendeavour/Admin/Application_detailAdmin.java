package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import com.ecell.internshipfairendeavour.R;
import com.google.android.material.tabs.TabLayout;

public class Application_detailAdmin extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;
    String key;
    String cmpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_application_detail);
        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById( R.id.sliding_tab );
        key = getIntent().getStringExtra("id");
        cmpid=getIntent().getStringExtra( "cmpid" );
        Bundle bundle = new Bundle();
        bundle.putString("id",key);
        bundle.putString("cmpid",cmpid);
        FragmentManager fragmentManager1 = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager1.beginTransaction();

        Applications_FragmentAdmin desc0Fragment = new Applications_FragmentAdmin();
        desc0Fragment.setArguments(bundle);

        Shortlisted_FragmentAdmin desc1Fragment = new Shortlisted_FragmentAdmin();
        desc1Fragment.setArguments(bundle);

        Hired_FragmentAdmin desc2Fragment = new Hired_FragmentAdmin();
        desc2Fragment.setArguments(bundle);

        Rejected_FragmentAdmin desc3Fragment = new Rejected_FragmentAdmin();
        desc3Fragment.setArguments(bundle);
        fragmentTransaction.commit();

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Application"));
        tabLayout.addTab(tabLayout.newTab().setText("Shortlisted"));
        tabLayout.addTab(tabLayout.newTab().setText("Hired"));
        tabLayout.addTab(tabLayout.newTab().setText("Rejected"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Creating our pager adapter
        SimpleFragmentPagerAdapterOneAdmin adapter = new SimpleFragmentPagerAdapterOneAdmin(getSupportFragmentManager(), tabLayout.getTabCount(),key,cmpid);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
