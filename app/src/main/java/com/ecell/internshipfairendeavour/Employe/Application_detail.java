package com.ecell.internshipfairendeavour.Employe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ecell.internshipfairendeavour.R;
import com.google.android.material.tabs.TabLayout;


public class Application_detail extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    //This is our viewPager
    private ViewPager viewPager;
    String key,cmpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_application_detail );
        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById( R.id.sliding_tab );
        key = getIntent().getStringExtra("key");
        cmpid=getIntent().getStringExtra( "cmpid" );


        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        bundle.putString("cmpid",cmpid);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Applications_Fragment descFragment = new Applications_Fragment();
        descFragment.setArguments(bundle);

        Shortlisted_Fragment desc1Fragment = new Shortlisted_Fragment();
        desc1Fragment.setArguments(bundle);

        Hired_Fragment desc2Fragment = new Hired_Fragment();
        desc2Fragment.setArguments(bundle);

        Rejected_Fragment desc3Fragment = new Rejected_Fragment();
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
        SimpleFragmentPagerAdapterOne adapter = new SimpleFragmentPagerAdapterOne(getSupportFragmentManager(), tabLayout.getTabCount(),key,cmpid);

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
