package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.ecell.internshipfairendeavour.Admin.model.internall_md;
import com.ecell.internshipfairendeavour.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Desc2Fragment_admin extends Fragment {

    TextView textView1;
    String key;
    EditText textview1_desc_et;
    ImageView pencil_desc,cross_desc;
    Button update_et;

    public Desc2Fragment_admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_desc2_admin, container, false );

        textView1 = view.findViewById(R.id.textview1_wca);


        key = getArguments().getString("key");
        pencil_desc=view.findViewById( R.id.pencil_desc );
        cross_desc=view.findViewById( R.id.cross_desc );
        update_et=view.findViewById( R.id.update_et1 );
        textview1_desc_et=view.findViewById( R.id.textview1_desc_et );
        pencil_desc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pencil_desc.setVisibility( View.GONE );
                textView1.setVisibility( View.GONE );
                textview1_desc_et.setVisibility( View.VISIBLE );
                update_et.setVisibility( View.VISIBLE );
                cross_desc.setVisibility( View.VISIBLE );
            }
        } );
        cross_desc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pencil_desc.setVisibility( View.VISIBLE );
                textView1.setVisibility( View.VISIBLE );
                textview1_desc_et.setVisibility( View.GONE );
                update_et.setVisibility( View.GONE );
                cross_desc.setVisibility( View.GONE );
            }
        } );

        update_et.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Internships").child( key );
                databaseReferencecmpexp.keepSynced(true);
                databaseReferencecmpexp.child("wca1").setValue(textview1_desc_et.getText().toString());
                Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
                pencil_desc.setVisibility( View.VISIBLE );
                textView1.setVisibility( View.VISIBLE );
                textview1_desc_et.setVisibility( View.GONE );
                update_et.setVisibility( View.GONE );
                cross_desc.setVisibility( View.GONE );
            }
        } );

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Internships");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("id").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    internall_md value = dataSnapshot1.getValue(internall_md.class);

                    textView1.setText(value.wca1+"\n");

                    textview1_desc_et.setText( value.wca1 );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
}
