package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.ecell.internshipfairendeavour.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOneAdmin extends Fragment {

    public FragmentOneAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        final FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add( R.id.container_shedule,new FragmentOneA_Admin());
        fragmentTransaction.commit();


        final View view = inflater.inflate( R.layout.fragment_one, container, false );
        return view;
    }
}
