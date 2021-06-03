package com.ecell.internshipfairendeavour;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.Admin.Login_Admin;


public class MainActivity extends AppCompatActivity {
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
    }
}
