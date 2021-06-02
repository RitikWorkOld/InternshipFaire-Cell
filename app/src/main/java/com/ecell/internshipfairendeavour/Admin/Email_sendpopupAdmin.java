package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Email_sendpopupAdmin extends AppCompatActivity {
        private EditText student_email_et,message_et,subject_et;
        TextView employe_email;
        Button send_btn;
ImageView cross;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_email_sendpopup );
        employe_email=findViewById( R.id.employ_email_et );
        student_email_et=findViewById( R.id.student_email_et );
        send_btn=findViewById( R.id.send_btn_email_popup );
        message_et=findViewById( R.id.message_et );
        subject_et=findViewById( R.id.subject_et );
cross=findViewById( R.id.cross_btn_rf );


cross.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();

    }
} );

        ArrayList<String> email=getIntent().getExtras().getStringArrayList("emails");
        Log.d("has","emails"+email);


        for(int i = 0; i < email.size(); i++) {
            student_email_et.append( email.get( i ) );
            if(i!=email.size()-1) {
                student_email_et.append( "," );
            }
        }
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "admin" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Admin employe = dataSnapshot.getValue( Admin.class);

            employe_email.setText( employe.getId() );
                send_btn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!student_email_et.getText().toString().isEmpty()) {
                            if (!employe_email.getText().toString().isEmpty()) {
                                if (!subject_et.getText().toString().isEmpty()) {
                                    if (!message_et.getText().toString().isEmpty()) {
                                        String to = student_email_et.getText().toString();
                                        String subject = subject_et.getText().toString();
                                        String message = message_et.getText().toString();


                                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                                        intent.setData( Uri.parse("mailto:")); // only email apps should handle this
                                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ecell@kiet.edu", "ecellwebtechnical@gmail.com"});
                                      //  intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
                                        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                                        intent.putExtra(Intent.EXTRA_TEXT, message);
                                    //    intent.putExtra(Intent.EXTRA_CC, new String[]{"Socialdukan@gmail.com", "info@socialdukan.com"} );
                                        intent.putExtra(Intent.EXTRA_BCC, new String[]{to} );
                                        if (intent.resolveActivity(getPackageManager()) != null) {
                                            startActivity(intent);
                                        }
                                    } else {

                                        message_et.setError( "Required" );
                                    }


                                } else {

                                    subject_et.setError( "Required" );
                                }


                            } else {

                                employe_email.setError( "Required" );
                            }
                        }
                        else{

                            Toast.makeText(getApplicationContext(), "You have not selected any student to send email", Toast.LENGTH_SHORT).show();

                            }

                    }
                } );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );




    }
}
