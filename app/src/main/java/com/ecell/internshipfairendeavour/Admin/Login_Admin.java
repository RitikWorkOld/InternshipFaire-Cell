package com.ecell.internshipfairendeavour.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.R;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login_Admin extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {
    Button changepass;
    EditText emailId, password;
    FirebaseAuth mFirebaseAuth;
    TextView text0;

    TextView fgt_pass;

    private ProgressBar progressBars;
    Button fab;
    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs2";
    private static final String KEY_REMEMBER = "remember2";
    private static final String KEY_USERNAME = "username2";
    private static final String KEY_PASS = "password2";
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login_admin );


        fgt_pass=findViewById( R.id.frgt_pass );
        fgt_pass.setVisibility( View.GONE );
        fab=findViewById( R.id.go1 );
        progressBars = findViewById(R.id.progressBar2);
        progressBars.setVisibility(View.GONE);
        text0=findViewById( R.id.text0 );
        text0.setText( "Forget Password?" );

        changepass=findViewById( R.id.signup);
        changepass.setText("Change Password");
        if(!haveNetworkConnection()){


            Toast.makeText(Login_Admin.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }




        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox)findViewById(R.id.checkBox);





        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById( R.id.Lemail );
        password = findViewById( R.id.Lpass );
        emailId.setText( sharedPreferences.getString( KEY_USERNAME, "" ) );
        password.setText( sharedPreferences.getString( KEY_PASS, "" ) );

        emailId.addTextChangedListener( this );
        password.addTextChangedListener( this );
        rem_userpass.setOnCheckedChangeListener( this );

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser==null){

                }
            }
        };


        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBars.setVisibility(View.VISIBLE);
                final String email = emailId.getText().toString();
                final String pwd = password.getText().toString();
                if(!email.equals( "ecell@kiet.edu" )){
                    emailId.setError( "Incorrect Email" );
                    progressBars.setVisibility(View.GONE);
                }
                else if (email.isEmpty()) {
                    progressBars.setVisibility(View.GONE);
                    emailId.setError( "Please enter email id" );
                    progressBars.setVisibility(View.GONE);
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    progressBars.setVisibility(View.GONE);
                    password.setError( "Please enter your password" );
                    progressBars.setVisibility(View.GONE);
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    progressBars.setVisibility(View.GONE);
                    Toast.makeText( Login_Admin.this, "Fields Are Empty!", Toast.LENGTH_SHORT ).show();
                    progressBars.setVisibility(View.GONE);
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    //check this runs
                    Log.d( "LOOP 1", "status: login " );//ye lga rhndo

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("admin");
                    databaseReference.keepSynced(true);
                    databaseReference.orderByChild("id").equalTo("A6OF87peuygrVAB52bnuAdCIuuo2").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                            mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(Login_Admin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(!task.isSuccessful()){
                                        // progressBars.setVisibility(View.GONE);
                                        progressBars.setVisibility(View.GONE);


                                        Toast.makeText(Login_Admin.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        //progressBars.setVisibility(View.GONE);
                                        progressBars.setVisibility(View.GONE);
                                        Log.d(">> NOTWORKING 1", "onComplete: + COME IN LOOP ");
                                        ////yha bhi aaya run statement...ok

                                        //saving session
                                        //Save.save(getApplicationContext(),"session","true");

                                        Intent intToHome = new Intent(getApplicationContext(),Dashboard_Admin.class);//not working TEAM.
                                        startActivity(intToHome);

                                        finish();

                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText( Login_Admin.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT ).show();
                            progressBars.setVisibility(View.GONE);
                        }
                    });



                }
            }
        } );





    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void managePrefs() {
        if (rem_userpass.isChecked()) {
            editor.putString( KEY_USERNAME, emailId.getText().toString().trim() );
            editor.putString( KEY_PASS, password.getText().toString().trim() );
            editor.putBoolean( KEY_REMEMBER, true );
            editor.apply();
        } else {
            editor.putBoolean( KEY_REMEMBER, false );
            editor.remove( KEY_PASS );//editor.putString(KEY_PASS,"");
            editor.remove( KEY_USERNAME );//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}