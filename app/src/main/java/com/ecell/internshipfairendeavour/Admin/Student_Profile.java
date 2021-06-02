package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class Student_Profile extends AppCompatActivity {
private Button accept,reject,block,unblock;
    TextView cmp_name,cmp_email,cmp_no,status;
    ImageView user_img;
    TextView desc_view;

String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student__profile );
        accept=findViewById( R.id.accept_profile );
        reject=findViewById( R.id.reject_profile );
        cmp_name=findViewById( R.id.cmp_name );
        user_img=findViewById( R.id.user_img );
        cmp_email=findViewById( R.id.cmp_email );
        cmp_no=findViewById( R.id.cmp_no );
        desc_view=findViewById( R.id.desc_view );
        status=findViewById( R.id.status );
        block=findViewById( R.id.block );
        unblock=findViewById( R.id.unblock );


userid=getIntent().getStringExtra( "userid" );
        block.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Employe").child( userid );
                databaseReferencecmpexp.keepSynced(true);
                databaseReferencecmpexp.child("officialstatus").setValue("Blocked");
                block.setText( "Blocked" );
                block.setVisibility( View.GONE );
                Toast.makeText(getApplicationContext(), "Blocked", Toast.LENGTH_SHORT).show();
            }
        } );
        unblock.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Employe").child( userid );
                databaseReferencecmpexp.keepSynced(true);
                databaseReferencecmpexp.child("officialstatus").setValue("Unblocked");
                unblock.setText( "Unblocked" );
                block.setVisibility( View.VISIBLE );
                Toast.makeText(getApplicationContext(), "Unblocked", Toast.LENGTH_SHORT).show();
            }
        } );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                    rootRef.child("Employe").child( userid ).child("officialstatus").setValue("yes");
accept.setVisibility( View.GONE );
                Toast.makeText(getApplicationContext(),"Accepted",Toast.LENGTH_LONG).show();



            }
        } );
        reject.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

                    rootRef.child("Employe").child( userid ).child("officialstatus").setValue("no");
                Toast.makeText(getApplicationContext(),"Rejected",Toast.LENGTH_LONG).show();

accept.setVisibility( View.VISIBLE );



            }
        } );

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
status.setText( bnd_helper.getOfficialstatus() );
                    cmp_no.setText("+91 "+ n_two );

                    if (bnd_helper.getOfficialstatus().equals( "Blocked" )){

                        block.setVisibility( View.GONE );
                        unblock.setVisibility( View.VISIBLE );
                    }

                    if (bnd_helper.getOfficialstatus().equals( "yes" )){
accept.setVisibility( View.GONE );
reject.setVisibility( View.VISIBLE );
                    }
                    if (bnd_helper.getOfficialstatus().equals( "no" )){
                        accept.setVisibility( View.VISIBLE );
                        reject.setVisibility( View.GONE );
                    }


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
