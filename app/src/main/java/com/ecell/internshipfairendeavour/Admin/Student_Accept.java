package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ecell.internshipfairendeavour.Employe.Application_vh;
import com.ecell.internshipfairendeavour.Employe.Email_sendpopup;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Employe.Student_detail_emp;
import com.ecell.internshipfairendeavour.Employe.applied_intern_md;
import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.ecell.internshipfairendeavour.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Student_Accept extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    Button accept_profile,reject_profile;
    BucketRecyclerView rv_internall;
    DatabaseReference drinternall;

    ArrayList<String> selectedStudents_status;
    ArrayList<String> statusStudents;
    CheckBox all;
    FirebaseRecyclerOptions<User_admin> optionsinternall;
    FirebaseRecyclerAdapter<User_admin, Student_vh> adapterinternall;
    ArrayList<String> selectedStudent_main,selectedStudent_noti,all_main,all_noti,all_names,all_numbers,selected_numbers,selected_names;
    private View no_app;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_student_accept);


        statusStudents = new ArrayList<>();
        selectedStudent_main = new ArrayList<>();
        all_main = new ArrayList<>();
        all_names = new ArrayList<>();
        all_numbers = new ArrayList<>();
        selectedStudent_noti = new ArrayList<>();

        selected_numbers = new ArrayList<>();
        selected_names = new ArrayList<>();

        all_noti = new ArrayList<>();

        rv_internall = findViewById(R.id.recycle_application);
        no_app=findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );

        accept_profile=findViewById( R.id.accept_profile );
        reject_profile=findViewById( R.id.reject_profile );

        selectedStudents_status = new ArrayList<>();
        mFirebaseAuth = FirebaseAuth.getInstance();
        rv_internall.setHasFixedSize(true);
        rv_internall.setLayoutManager(new LinearLayoutManager(Student_Accept.this));


        drinternall = FirebaseDatabase.getInstance().getReference().child("Users");

        drinternall.keepSynced(true);
        Query query1 = drinternall.orderByChild("officialstatus").equalTo("Not Checked");
        optionsinternall = new FirebaseRecyclerOptions.Builder<User_admin>().setQuery(query1, User_admin.class).build();

        adapterinternall = new FirebaseRecyclerAdapter<User_admin, Student_vh>(optionsinternall) {
            @Override
            protected void onBindViewHolder(@NonNull final Student_vh holder, int position, @NonNull final User_admin model) {
                holder.cmpname.setText(model.getName());
                holder.cmpsubname.setText(model.getContactn());
                holder.checkBox.setChecked(model.isSelected());


                holder.checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.checkBox.isChecked()) {
                            model.setSelected(true);
                            statusStudents.add(model.getUid());
                            selectedStudent_main.add(model.getEmail());
                            selectedStudent_noti.add(model.getUid());
                            selected_names.add(model.getName());
                            selected_numbers.add(model.getContactn());


                        }
                        else if (!holder.checkBox.isChecked()) {
                            model.setSelected(false);
                            selectedStudent_main.remove(model.getEmail());
                            selectedStudent_noti.remove(model.getUid());
                            selected_names.remove(model.getName());
                            selected_numbers.remove(model.getContactn());
                            statusStudents.remove(model.getUid());

                        }
                    }
                } );

                accept_profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedStudent_main.isEmpty()){

                            Toast.makeText(getApplicationContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            for (String userid : statusStudents) {
                                rootRef.child( "Users" ).child( userid ).child( "officialstatus" ).setValue( "yes" );


                            }


                        }
                    }
                } );
                reject_profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedStudent_main.isEmpty()){

                            Toast.makeText(getApplicationContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                            for (String userid : statusStudents) {
                                rootRef.child( "Users" ).child( userid ).child( "officialstatus" ).setValue( "no" );


                            }

                        }
                    }
                } );





                holder.profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(Student_Accept.this, Student_detail_status.class);

                        intent.putExtra( "userid",model.getUid() );

                        startActivity( intent );
                    }
                } );
            }

            @NonNull
            @Override
            public Student_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Student_vh(LayoutInflater.from(Student_Accept.this).inflate(R.layout.student_vh, parent,false));
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
