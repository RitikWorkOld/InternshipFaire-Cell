package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.ecell.internshipfairendeavour.R;


public class AdminWorkArrow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_work_arrow );
        RadioButton radioButton_intern = (RadioButton)findViewById( R.id.radio_button_internship);

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( R.id.events_container1, new Internship_Admin() );
        fragmentTransaction.commit();


        radioButton_intern.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                final FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace( R.id.events_container1, new Internship_Admin() );
                fragmentTransaction1.commit();
            }
        } );





    }
}
