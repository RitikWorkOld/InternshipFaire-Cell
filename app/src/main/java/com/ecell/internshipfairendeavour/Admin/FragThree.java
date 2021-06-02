package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ecell.internshipfairendeavour.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragThree extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_frag_three);

        RadioButton radioButton_intern = (RadioButton) findViewById( R.id.radio_button_internship);
       // RadioButton radioButton_influe = (RadioButton) findViewById( R.id.radio_button_influencer );
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add( R.id.events_container, new Fragment_four_admin() );
        fragmentTransaction.commit();


        radioButton_intern.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                final FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace( R.id.events_container, new Fragment_four_admin() );
                fragmentTransaction1.commit();
            }
        } );
    }
}

// Step 01 :-
/*********************************************************************************************************************
 *                                     public class FragThree extends Fragment
 *                                                      |to|
 *                                public class FragThree extends AppCompatActivity
 *///********************************************************************************************************************


// Step 02 :-
/*********************************************************************************************************************
 *               public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
 *                                                       |to|
 *                             public void onCreate(@Nullable Bundle savedInstanceState)
 *///********************************************************************************************************************


// Step 03 :-
/*********************************************************************************************************************
 *                                Add :-  setContentView(R.layout.xml_name_here);
 *///********************************************************************************************************************


// Step 04 :-
/*********************************************************************************************************************
 *                 Remove :-  View view= inflater.inflate( R.layout.fragment_five_admin, container, false );
 *                                                       |And|
 *                                               Remove :-  return view;
 *///********************************************************************************************************************


// Step 05 :-
/*********************************************************************************************************************
 *                                              Solve the context errors :)
 *///********************************************************************************************************************