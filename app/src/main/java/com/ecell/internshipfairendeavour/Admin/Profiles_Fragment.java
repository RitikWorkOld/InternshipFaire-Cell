package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ecell.internshipfairendeavour.Employe.Application_vh;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Profiles_Fragment extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    Button accept_profile,reject_profile;
    BucketRecyclerView rv_internall;
    DatabaseReference drinternall;
    ArrayList<String> selectedStudents_status;
    FirebaseRecyclerOptions<Employe> optionsinternall;
    FirebaseRecyclerAdapter<Employe, Application_vh> adapterinternall;
    private View no_app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.fragment_profiles_);

        rv_internall = findViewById(R.id.recycle_application);
        no_app=findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );
        accept_profile=findViewById( R.id.accept_profile );
        reject_profile=findViewById( R.id.reject_profile );
        selectedStudents_status = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        rv_internall.setHasFixedSize(true);
        rv_internall.setLayoutManager(new LinearLayoutManager(Profiles_Fragment.this));
        drinternall = FirebaseDatabase.getInstance().getReference().child("Employe");
        drinternall.keepSynced(true);
        optionsinternall = new FirebaseRecyclerOptions.Builder<Employe>().setQuery(drinternall,Employe.class).build();

        adapterinternall = new FirebaseRecyclerAdapter<Employe, Application_vh>(optionsinternall) {
            @Override
            protected void onBindViewHolder(@NonNull final Application_vh holder, int position, @NonNull final Employe model) {
                holder.cmpname.setText(model.getName());
                holder.cmpsubname.setText(model.getContactn());

                Picasso.get().load(model.getProfileimg()).resize( 400,400 ).into(holder.cmpimg);
                holder.checkBox.setChecked(model.isSelected());

                holder.checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.checkBox.isChecked()) {
                            model.setSelected(true);
                            selectedStudents_status.add(model.getEid());

                        }
                        else if (!holder.checkBox.isChecked()) {
                            model.setSelected(false);
                            selectedStudents_status.remove(model.getEid());

                        }
                    }
                } );


                holder.profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Profiles_Fragment.this, Student_Profile.class);

                        intent.putExtra( "userid",model.getEid() );

                        startActivity( intent );
                    }
                } );
            }

            @NonNull
            @Override
            public Application_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Application_vh(LayoutInflater.from(Profiles_Fragment.this).inflate(R.layout.application_vh, parent,false));
            }
        };
        rv_internall.setAdapter(adapterinternall);
        adapterinternall.startListening();

        accept_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                for(String userid : selectedStudents_status) {
                    rootRef.child("Employe").child( userid ).child("officialstatus").setValue("yes");



                }
            }
        } );
        reject_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                for(String userid : selectedStudents_status) {
                    rootRef.child("Employe").child( userid ).child("officialstatus").setValue("no");
                }
            }
        } );
    }
}
