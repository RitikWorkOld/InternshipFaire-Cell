package com.ecell.internshipfairendeavour;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;




public class MainActivity extends AppCompatActivity {
    private Button student_btn;
    private Button employe_btn;
private Button admin_btn;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
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
