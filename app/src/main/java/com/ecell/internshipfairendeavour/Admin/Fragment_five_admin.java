package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ecell.internshipfairendeavour.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_five_admin extends Fragment {
ImageView arrow;
    public Fragment_five_admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate( R.layout.fragment_five_admin, container, false );
        arrow=view.findViewById( R.id.arrow );
        RadioButton radioButton_student = (RadioButton) view.findViewById( R.id.radio_button_internship);
        RadioButton radioButton_employe = (RadioButton) view.findViewById( R.id.radio_button_influencer );
        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add( R.id.events_container, new Student_Fragment() );
        fragmentTransaction.commit();
arrow.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent= new Intent( getActivity(), AdminWorkArrow.class );
        startActivity( intent );
    }
} );
        radioButton_student.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                final FragmentTransaction fragmentTransaction1 = getFragmentManager().beginTransaction();
                fragmentTransaction1.replace( R.id.events_container, new Student_Fragment() );
                fragmentTransaction1.commit();
            }
        } );

        radioButton_employe.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction fragmentTransaction2 = getFragmentManager().beginTransaction();
                fragmentTransaction2.replace( R.id.events_container, new Employe_Fragment() );
                fragmentTransaction2.commit();
            }
        } );

        return view;
    }
}
