package com.ecell.internshipfairendeavour.Employe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Login_Employe;
import com.ecell.internshipfairendeavour.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.regex.Pattern;

public class Reg_Employe extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIn;
    DatabaseReference reff;
    long maxid;
    ImageView chatbot;
    private ProgressBar progressBars;
    private EditText emailId,password,number1,fname1,cmpwebsite;
    FirebaseAuth mFirebaseAuth;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reg__employe );
        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        progressBars = findViewById(R.id.progressBar2);
        progressBars.setVisibility(View.GONE);
        password = findViewById(R.id.password);
        fname1 = findViewById(R.id.fname);
        number1 = findViewById(R.id.cnumber);
        cmpwebsite=findViewById( R.id.cmp_website );


        findViewById( R.id.go1 ).setOnClickListener( this );



        btnSignIn=findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnSignIn){
                    Intent intent = new Intent(Reg_Employe.this, Login_Employe.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    finish();
                }
            }
        } );



    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.go1:
                progressBars.setVisibility(View.VISIBLE);
                findViewById( R.id.go1 ).setVisibility( View.VISIBLE );

                boolean valid = validateUser();

                //  Log.i("Hello", "working");
                if (valid) {
                    final String number=number1.getText().toString().trim();
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Employe");
                    dbref.keepSynced(true);
                    dbref.orderByChild("contactn").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                progressBars.setVisibility(View.GONE);
                                findViewById(R.id.go1).setVisibility(View.VISIBLE);
                                Toast.makeText(Reg_Employe.this,"User on this Phone Number already exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                        registeruser();

                                //Toast.makeText(RegAct.this,"NO user found",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                break;
        }

    }


    private boolean validateUser() {
        final String email=emailId.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        final String fname=fname1.getText().toString().trim();
final String website=cmpwebsite.getText().toString().trim();
        final String number=number1.getText().toString().trim();


        if(fname.isEmpty()){
            fname1.setError(getString(R.string.input_error_name));
            fname1.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }
        else if(email.isEmpty()){
            emailId.setError(getString(R.string.input_error_email));
            emailId.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError(getString(R.string.input_error_email_invalid));
            emailId.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }
        else if(pwd.isEmpty()){
            password.setError(getString(R.string.input_error_password));
            password.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }

        else if (pwd.length() < 6 ) {
            password.setError(getString(R.string.input_error_password_length));
            password.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(pwd).matches()){

            password.setError("1 Digit? \n 1 LowerCase? \n 1 UpperCase? \n 1 Special Character? \n atleast 6 character?");
            password.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }



        else if(number.isEmpty()){
            number1.setError("Please Enter Your Number");
            number1.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }
        else if(website.isEmpty()){
            cmpwebsite.setError("Website Required");
            cmpwebsite.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;

        }
        else if (number.length() != 10) {
            number1.setError(getString(R.string.input_error_phone_invalid));
            number1.requestFocus();
            progressBars.setVisibility(View.GONE);
            return false;
        }


        else {
            return true;
        }
    }
    private void registeruser() {

      /*  Intent intent = getIntent();
        final String email = intent.getStringExtra("email");
        final String pwd = intent.getStringExtra("password");
        final String fname = intent.getStringExtra("name");
        final String website=intent.getStringExtra("website");
        final String number = intent.getStringExtra("number");*/


        //  progressBar.setVisibility(View.VISIBLE);
        final String fname=fname1.getText().toString().trim();
        final String email=emailId.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        final String number = number1.getText().toString().trim();
        final String website = cmpwebsite.getText().toString().trim();
        //verify_btn.setVisibility(View.GONE);
        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                  /*  mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            otp.setVisibility( View.GONE );
                            verify_btn.setVisibility( View.GONE );
                            enterotp.setVisibility( View.GONE );



                            chk_email.setVisibility( View.VISIBLE );
                            email_txt.setVisibility( View.VISIBLE );
                            login_btn.setVisibility( View.VISIBLE );
                            login_btn.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Verification_2.this, Login_Employe.class);

                                    startActivity( intent );
                                    finish();

                                }
                            } );
                        }
                    });*/

                    //final String refrelid = endvr.concat(number);
                    String uid = FirebaseAuth.getInstance().getUid();
                    reff=FirebaseDatabase.getInstance().getReference().child( "Employe" );
                    reff.addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                maxid=(dataSnapshot.getChildrenCount());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                    Employe employe=new Employe("no","00"+ maxid+2,fname,email,number,uid,pwd,null,"https://"+website,"no","Not Checked",null,"no");

                    FirebaseDatabase.getInstance().getReference("Employe")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(employe).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // progressBar.setVisibility(View.GONE);
                            //progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {


                                //Saveone.saveone(getApplicationContext(),"session","false");
                                Intent intent = new Intent(Reg_Employe.this, Login_Employe.class);
                                Toast.makeText(Reg_Employe.this, "Registration Successful", Toast.LENGTH_LONG).show();
                                startActivity( intent );
                                finish();

                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid
                                    // Intent intent = new Intent(RequestOtp.this,Reg_Fail.class);
                                    //  startActivity(intent);
                                    //  finish();
                                    Toast.makeText(Reg_Employe.this, "Error Occurred, Please try again later", Toast.LENGTH_LONG).show();
                                }

                            }
                        }
                    });

                }
                else {
                    //progressBar.setVisibility(View.GONE);
                    //Toast.makeText(RegAct.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    //   Intent intent = new Intent(RequestOtp.this,Reg_Fail.class);ion F
                    // startActivity(intent);
                    Toast.makeText(Reg_Employe.this, "Registration Failed", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

}

