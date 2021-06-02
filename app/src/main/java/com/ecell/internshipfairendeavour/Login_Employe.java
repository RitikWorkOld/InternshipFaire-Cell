package com.ecell.internshipfairendeavour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.appcompat.app.AppCompatActivity;

import com.ecell.internshipfairendeavour.Employe.Dashboard_emp;
import com.ecell.internshipfairendeavour.Employe.Employe_detail;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Employe.Reg_Employe;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login_Employe extends AppCompatActivity implements TextWatcher,
        CompoundButton.OnCheckedChangeListener {
    Button btnSignUp,login;
    ImageButton go;
    boolean session;
    EditText emailId, password;
    FirebaseAuth mFirebaseAuth;
    String pstatus;
    String ostatus;
    TextView fgt_pass;
    ImageView chatbot;
    private ProgressBar progressBars;

    private CheckBox rem_userpass;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs1";
    private static final String KEY_REMEMBER = "remember1";
    private static final String KEY_USERNAME = "username1";
    private static final String KEY_PASS = "password1";
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //---( TO REMOVE STATUS BAR )---//
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_login__employe );
        login=findViewById( R.id.go1 );
        fgt_pass=findViewById( R.id.frgt_pass );
        fgt_pass.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Login_Employe.this, Change_Pass.class );
                startActivity( intent );
                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
            }
        } );
        if(!haveNetworkConnection()){


            Toast.makeText(Login_Employe.this,"No Network Connection",Toast.LENGTH_LONG).show();
        }




        btnSignUp=findViewById( R.id.signup);

        progressBars = findViewById(R.id.progressBar2);
        progressBars.setVisibility(View.GONE);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        rem_userpass = (CheckBox)findViewById(R.id.checkBox);
        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rem_userpass.setChecked(true);
        else
            rem_userpass.setChecked(false);




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
                    // Toast.makeText(LoginActivity.this,"Please Login",Toast.LENGTH_SHORT).show();*******************************************************************
                }
            }
        };


        login.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBars.setVisibility(View.VISIBLE);
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    // progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    emailId.setError( "Please enter email id" );
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    //progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    password.setError( "Please enter your password" );
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    //   progressBars.setVisibility(View.GONE);
                    progressBars.setVisibility(View.GONE);
                    Toast.makeText( Login_Employe.this, "Fields Are Empty!", Toast.LENGTH_SHORT ).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    //check this runs
                    Log.d( "LOOP 1", "status: login " );//ye lga rhndo

                    mFirebaseAuth.signInWithEmailAndPassword( email, pwd ).addOnCompleteListener( Login_Employe.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                //progressBars.setVisibility(View.GONE);
                                progressBars.setVisibility(View.GONE);
                                Toast.makeText( Login_Employe.this, "Something went wrong", Toast.LENGTH_SHORT ).show();

                            } else {
                                // progressBars.setVisibility(View.GONE);
                                Log.d( ">> NOTWORKING 1", "onComplete: + COME IN LOOP " );


                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
                                databaseReference.keepSynced(true);
                                databaseReference.orderByChild("eid").equalTo(mFirebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                            Employe user = dataSnapshot1.getValue( Employe.class);

                                            pstatus = user.profilestatus;
                                            ostatus=user.officialstatus;

                                            Log.d("HEL**************","**************************************   "+pstatus);

                                            FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser();

                                            //     if ( firebaseUser.isEmailVerified())
                                            //      {
                                            progressBars.setVisibility(View.GONE);
                                            if (pstatus.equals("yes") && ostatus.equals( "yes" ) ){
                                                Log.d("HEL","msg= yha agya");
                                                // Toast.makeText(Login_Employe.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Login_Employe.this, Dashboard_emp.class);
                                                //Saveone.saveone(getApplicationContext(),"session","true");

                                                startActivity(intent);
                                                finish();
                                            }
                                            if ( ostatus.equals( "Blocked" ) ){
                                                progressBars.setVisibility(View.GONE);
                                                Log.d("HEL","msg= yha agya");
                                              //  Saveone.saveone(getApplicationContext(),"session","false");
                                                Toast.makeText(Login_Employe.this, "Your account has been blocked due to any suspicious activity. Please do reach out on info@socialdukan.com to clarify the same.", Toast.LENGTH_SHORT).show();

                                            }
                                               /* else if(pstatus.equals( "yes" ) && ostatus.equals("no")){
                                                    Toast.makeText(Login_Employe.this, "Your account is in verification, Please Wait", Toast.LENGTH_SHORT).show();

                                                }*/

                                            if(ostatus.equals( "Not Checked" ) && pstatus.equals( "no" ) ||ostatus.equals( "Not Checked" ) && pstatus.equals( "yes" )  ) {
                                                progressBars.setVisibility(View.GONE);
                                                Log.d("HEL","msg= yha agya");
                                                // Toast.makeText(Login_Employe.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                                              //  Saveone.saveone(getApplicationContext(),"session","false");
                                                Intent intent = new Intent(Login_Employe.this, Employe_detail.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                            //}
                                            // else
                                            //  {
                                            // email is not verified, so just prompt the message to the user and restart this activity.
                                            // NOTE: don't forget to log out the user.
                                            //       FirebaseAuth.getInstance().signOut();
                                            ////       progressBars.setVisibility(View.GONE);
                                            //      Toast.makeText(Login_Employe.this, "Email Not Verified", Toast.LENGTH_SHORT).show();
                                            //      Saveone.saveone(getApplicationContext(),"session","false");
                                            //restart this activity
                                            //  }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        progressBars.setVisibility(View.GONE);
                                        Toast.makeText( Login_Employe.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT ).show();
                                    }
                                });
                            }
                        }
                    } );
                }
            }
        } );



        btnSignUp.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == btnSignUp) {
                    Intent intent = new Intent( Login_Employe.this, Reg_Employe.class );
                    startActivity( intent );
                    overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out );
                    finish();
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