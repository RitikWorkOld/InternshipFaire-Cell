package com.ecell.internshipfairendeavour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Reg_Student extends AppCompatActivity implements View.OnClickListener {
    Button btnSignIn;
    private EditText emailId,password,number1,fname1;
    FirebaseAuth mFirebaseAuth;


    private ProgressBar progressBars;

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
        setContentView( R.layout.activity_reg_student );
        mFirebaseAuth = FirebaseAuth.getInstance();
        progressBars = findViewById(R.id.progressBar2);
        progressBars.setVisibility(View.GONE);

        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fname1 = findViewById(R.id.fname);
        number1 = findViewById(R.id.cnumber);




        findViewById( R.id.go ).setOnClickListener( this );



        btnSignIn=findViewById(R.id.btn_signin);

        btnSignIn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==btnSignIn){
                    Intent intent = new Intent(Reg_Student.this, Login_Student.class);
                    startActivity(intent);
                    emailId.setText( "" );
                    password.setText( "" );
                    fname1.setText( "" );
                    number1.setText( "" );
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

            case R.id.go:
                progressBars.setVisibility(View.VISIBLE);
                findViewById( R.id.go ).setVisibility( View.VISIBLE );
               // final String phonenumber=ccp.getSelectedCountryCode();
                boolean valid = validateUser();


                if (valid) {
                    final String number=number1.getText().toString().trim();
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users");
                    dbref.keepSynced(true);
                    dbref.orderByChild("contactn").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null){
                                progressBars.setVisibility(View.GONE);
                                findViewById(R.id.go).setVisibility(View.VISIBLE);
                                Toast.makeText(Reg_Student.this,"User on this phone Number Already Exists",Toast.LENGTH_SHORT).show();
                            }
                            else {
                                registeruser();
                                //  progressBar.setVisibility(View.GONE);
                                findViewById( R.id.go ).setVisibility( View.VISIBLE );
                                //Toast.makeText(RegAct.this,"NO user found",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Reg_Student.this,"Some error occurred",Toast.LENGTH_SHORT).show();

                        }
                    });

                }
                else {
                    progressBars.setVisibility(View.GONE);

                }
                break;
        }


    }


    private boolean validateUser() {
        final String email=emailId.getText().toString().trim();
        final String pwd=password.getText().toString().trim();
        final String fname=fname1.getText().toString().trim();

        final String number=number1.getText().toString().trim();


        if(fname.isEmpty()){
            fname1.setError(getString(R.string.input_error_name));
            fname1.requestFocus();
            return false;
        }
        else if(email.isEmpty()){
            emailId.setError(getString(R.string.input_error_email));
            emailId.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailId.setError(getString(R.string.input_error_email_invalid));
            emailId.requestFocus();
            return false;
        }
        else if(pwd.isEmpty()){
            Toast.makeText( getApplicationContext(),"Password Required",Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if (pwd.length() < 6 ) {
            Toast.makeText( getApplicationContext(),"Password should be atleast 6 characters long",Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if(!PASSWORD_PATTERN.matcher(pwd).matches()){

            Toast.makeText( getApplicationContext(),"1 Digit? \n 1 LowerCase? \n 1 UpperCase? \n 1 Special Character? \n atleast 6 character?",Toast.LENGTH_SHORT ).show();
            return false;
        }



        else if(number.isEmpty()){
            number1.setError("Please Enter Your Number");
            number1.requestFocus();
            return false;
        }

        else if (number.length() != 10) {
            number1.setError(getString(R.string.input_error_phone_invalid));
            number1.requestFocus();
            return false;
        }


        else {
            return true;
        }

    }
    private void registeruser(){
        final String email = emailId.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        final String fname = fname1.getText().toString().trim();
        final String number = number1.getText().toString().trim();
    //    final String cd = ccp.getSelectedCountryCode();



        //  progressBar.setVisibility(View.VISIBLE);
        //verify_btn.setVisibility(View.GONE);
        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Reg_Student.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    String uid = FirebaseAuth.getInstance().getUid();
                    User user=new User(fname,email,number,uid,pwd,null,"no","91");

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // progressBar.setVisibility(View.GONE);
                            //progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(Reg_Student.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Reg_Student.this, Login_Student.class);
                                startActivity( intent );
                                finish();

                            }
                            else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(Reg_Student.this,"Some error occurred",Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });




                }
                else {

                    Toast.makeText(Reg_Student.this,"Registration Failed!", Toast.LENGTH_LONG).show();

                    finish();
                }
            }
        });
    }

}