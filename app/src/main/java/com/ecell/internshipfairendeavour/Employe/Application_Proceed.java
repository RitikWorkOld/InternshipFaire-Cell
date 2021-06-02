package com.ecell.internshipfairendeavour.Employe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.R;


public class Application_Proceed extends AppCompatActivity {
private Button okay;
ImageView cross;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_application__proceed );
        okay=findViewById( R.id.okay_btn );
        cross =findViewById( R.id.cross_btn_rf );
        cross.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
        okay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        } );
    }
}
