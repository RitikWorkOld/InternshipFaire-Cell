package com.ecell.internshipfairendeavour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ecell.internshipfairendeavour.Internship.InternFragment;
import com.ecell.internshipfairendeavour.MyDashboard.AppliedIntern;
import com.ecell.internshipfairendeavour.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard_Student extends AppCompatActivity {
    private Toast backToast;
    BottomNavigationView navigationView;
    private long backPressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //---( TO REMOVE STATUS BAR )---//
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_dashboard_student );

        navigationView=findViewById( R.id.bottom_nav );
        if(!haveNetworkConnection()){
            Toast.makeText(Dashboard_Student.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }
        final InternFragment intern=new InternFragment();
        final ProfileFragment profile=new ProfileFragment();
        final AppliedIntern appliedIntern = new AppliedIntern();

        navigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.intern){

                    setFragment( intern );
                    return true;
                }
                else if(id==R.id.profile){
                    setFragment(profile);
                    return true;

                } else if(id==R.id.chat_box){
                    setFragment(  appliedIntern);
                    return true;

                }
                return false;
            }
        } );
        navigationView.setSelectedItemId( R.id.intern );

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
    private synchronized void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( R.id.frame,fragment );
        fragmentTransaction.commitNow();

    }
}