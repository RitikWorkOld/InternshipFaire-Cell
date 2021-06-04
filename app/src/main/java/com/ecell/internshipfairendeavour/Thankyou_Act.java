package com.ecell.internshipfairendeavour;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Thankyou_Act extends AppCompatActivity {
Button okay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_thankyou );

        okay = findViewById( R.id.okay_btn_ );

        okay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent( Thankyou_Act.this, Login_Student.class );
                finish();
                startActivity( intent );

            }
        } );
    }
}