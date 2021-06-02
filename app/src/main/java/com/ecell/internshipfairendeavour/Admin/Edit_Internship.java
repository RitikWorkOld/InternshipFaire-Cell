package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.ecell.internshipfairendeavour.Internship.model.internall_md;
import com.ecell.internshipfairendeavour.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Edit_Internship extends AppCompatActivity {
private EditText intern_name,intern_location,intern_duration,intern_stipend,intern_work;
    DatabaseReference databaseReferencedetail;
    Button update;

String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit__internship );
        key=getIntent().getStringExtra( "key" );
        intern_name=findViewById( R.id.intern_name );
        update=findViewById( R.id.update );
        intern_location=findViewById( R.id.intern_location );
        intern_duration=findViewById( R.id.intern_duration );
        intern_stipend=findViewById( R.id.intern_stipend );
        intern_work=findViewById( R.id.intern_work );
update.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Internships").child( key );
        databaseReferencecmpexp.keepSynced(true);
        databaseReferencecmpexp.child("intname").setValue(intern_name.getText().toString());
        databaseReferencecmpexp.child("location").setValue(intern_location.getText().toString());
        databaseReferencecmpexp.child("amount").setValue(intern_stipend.getText().toString());
        databaseReferencecmpexp.child("worktime").setValue(intern_work.getText().toString());
        databaseReferencecmpexp.child("duration").setValue(intern_duration.getText().toString());
        Toast.makeText( getApplicationContext(),"Updated",Toast.LENGTH_LONG ).show();
        finish();
    }
} );
        databaseReferencedetail = FirebaseDatabase.getInstance().getReference().child("Internships");
        databaseReferencedetail.keepSynced(true);
        databaseReferencedetail.orderByChild("id").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    internall_md valueintern = dataSnapshot1.getValue(internall_md.class);


                  intern_name.setText( valueintern.getIntname() );
                  intern_location.setText( valueintern.getLocation() );
                  intern_duration.setText( valueintern.getDuration() );
                  intern_stipend.setText( valueintern.getAmount() );
                  intern_work.setText( valueintern.getWorktime() );

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
