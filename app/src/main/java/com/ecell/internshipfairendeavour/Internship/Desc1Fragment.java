package com.ecell.internshipfairendeavour.Internship;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ecell.internshipfairendeavour.Internship.model.internall_md;
import com.ecell.internshipfairendeavour.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Desc1Fragment extends Fragment {

    public Desc1Fragment() {
        // Required empty public constructor
    }

    TextView textView1,textView2,perks_view;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_desc1, container, false );

        textView1 = view.findViewById(R.id.textview1_wyg);
        perks_view=view.findViewById( R.id.perks_view );
        textView2 = view.findViewById(R.id.textview2_wyg);
        key = getArguments().getString("key");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Internships");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("id").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    internall_md value = dataSnapshot1.getValue(internall_md.class);
                    if(!value.wca1.equals("")){
                        perks_view.setVisibility( View.VISIBLE );
                        textView1.setVisibility(View.VISIBLE);

                    }
                    textView1.setText(value.wcg1);
                    textView2.setText(value.wcg2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
