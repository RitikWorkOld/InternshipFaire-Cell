package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ecell.internshipfairendeavour.R;


public class Fragment_three_admin_main extends Fragment {
    CardView card1,card3,card4,card5,card6;

    public Fragment_three_admin_main() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_three_admin_main, container, false );
        card1=view.findViewById( R.id.card1 );

        card3=view.findViewById( R.id.card3 );
        card4=view.findViewById( R.id.card4 );
        card5=view.findViewById( R.id.card5 );
        card6=view.findViewById( R.id.card6 );
      

        card1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Intern_Fragment.class);

                startActivity( intent );
            }
        } );

        card4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Profiles_Fragment.class);

                startActivity( intent );
            }
        } );
        card5.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FragThree.class);

                startActivity( intent );
            }
        } );
        card6.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getActivity(), AdminWorkArrow.class);

              startActivity( intent );
            }
        } );

        return view;
    }
}