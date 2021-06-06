package com.ecell.internshipfairendeavour;


import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.Admin.Login_Admin;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {

    private AppUpdateManager mAppUpdateManager;
    private static final int RC_APP_UPDATE = 100;

    private Button student_btn;
    private Button employe_btn;
    private Button admin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //---( TO REMOVE STATUS BAR )---//
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_main );
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener( new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && result.isUpdateTypeAllowed( AppUpdateType.IMMEDIATE ))
                {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult( result,AppUpdateType.IMMEDIATE,MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        } );


        student_btn=(Button) findViewById(R.id.stud_btn);
        employe_btn=(Button) findViewById(R.id.emp_btn);
        admin_btn = findViewById( R.id.admin );
        student_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==student_btn){
                    Intent intent=new Intent(getApplicationContext(), Login_Student.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                }
            }
        } );

        admin_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==admin_btn){
                    Intent intent=new Intent(getApplicationContext(), Login_Admin.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        } );
        employe_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==employe_btn){
                    Intent intent=new Intent(getApplicationContext(), Login_Employe.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                }
            }
        } );
     //   mAppUpdateManager.registerListener( installStateUpdatedListener );
    }

private InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
    @Override
    public void onStateUpdate(@NonNull InstallState state) {
        if(state.installStatus() == InstallStatus.DOWNLOADED){
            showCompletedUpdate();
        }
    }

    private void showCompletedUpdate() {

        Snackbar snackbar = Snackbar.make(findViewById( android.R.id.content ), "New app is ready!",Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction( "Install", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppUpdateManager.completeUpdate();
            }
        } );
        snackbar.show();
    }
};



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
if(requestCode == RC_APP_UPDATE && resultCode!= RESULT_FIRST_USER){
    Toast.makeText( this,"Cancel",Toast.LENGTH_SHORT ).show();
}


        super.onActivityResult( requestCode, resultCode, data );
    }

    @Override
    protected void onStop() {
     //   if(mAppUpdateManager!=null) mAppUpdateManager.unregisterListener( installStateUpdatedListener );
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener( new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if(result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                        && result.isUpdateTypeAllowed( AppUpdateType.IMMEDIATE ))
                {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult( result,AppUpdateType.IMMEDIATE,MainActivity.this, RC_APP_UPDATE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        } );

    }
}
