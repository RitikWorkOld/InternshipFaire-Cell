package com.ecell.internshipfairendeavour.Employe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

public class Employe_profile extends AppCompatActivity {

    TextView cmp_name,cmp_email,cmp_no,status;
    ImageView user_img;
    TextView desc_view;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.employe_profile );

        cmp_name=findViewById( R.id.cmp_name );
        user_img=findViewById( R.id.user_img );
        cmp_email=findViewById( R.id.cmp_email );
        cmp_no=findViewById( R.id.cmp_no );

        desc_view=findViewById( R.id.desc_view );



        userid=getIntent().getStringExtra( "userid" );



    }
    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("eid").equalTo( userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Employe bnd_helper = dataSnapshot1.getValue(Employe.class);

                    String n = bnd_helper.name;
                    String n_one=bnd_helper.email;
                    String n_two=bnd_helper.contactn;
                    String n_three=bnd_helper.descrip;
                    cmp_name.setText(n);
                    cmp_email.setText( n_one );

                    cmp_no.setText("+91 "+ n_two );
                    desc_view.setText( n_three );

                    Picasso.get().load(bnd_helper.profileimg).resize( 400,400 ).into(user_img);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
