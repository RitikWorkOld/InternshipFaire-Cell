package com.ecell.internshipfairendeavour.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.ecell.internshipfairendeavour.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard_Admin extends AppCompatActivity {
    BottomNavigationView navigationView;
    private Toast backToast;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard_admin );
        navigationView = findViewById( R.id.bottom_nav );
        if(!haveNetworkConnection()){
            Toast.makeText(Dashboard_Admin.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }

        final FragmentOneAdmin fragmentOne = new FragmentOneAdmin();

    final Fragment_three_admin_main fragmentThree = new Fragment_three_admin_main();

      //  final Fragment_five_admin fragmentFive = new Fragment_five_admin();
        navigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.one){
                    setFragment( fragmentOne );
                    return true;
                }
                if(id==R.id.three){
                    setFragment( fragmentThree );
                    return true;
                }
                if(id==R.id.five){
                  //  setFragment( fragmentFive );
                    return true;
                }


                return false;
            }
        } );
        navigationView.setSelectedItemId( R.id.one );


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
        fragmentTransaction.commit();

    }
}