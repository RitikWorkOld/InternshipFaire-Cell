package com.ecell.internshipfairendeavour.Employe;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.ecell.internshipfairendeavour.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Dashboard_emp extends AppCompatActivity {
    BottomNavigationView navigationView;
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard_emp );
        navigationView=findViewById( R.id.bottom_nav );
        if(!haveNetworkConnection()){


            Toast.makeText(getApplicationContext(),"No Network Connection",Toast.LENGTH_LONG).show();
        }
        final FragmentOne fragmentOne=new FragmentOne();
        final Fragment_two fragment_two=new Fragment_two();

        final Fragment_three fragment_three=new Fragment_three();

        navigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.one){
                    setFragment( fragmentOne );
                    return true;
                }
                else if(id==R.id.two){
                    setFragment(fragment_two);
                    return true;

                } else if(id==R.id.three){
                    setFragment( fragment_three );
                    return true;
                }



                return false;
            }
        } );
        navigationView.setSelectedItemId( R.id.one );

    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            finish();
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.frame_emp,fragment );
        fragmentTransaction.commitNow();

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
