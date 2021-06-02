package com.ecell.internshipfairendeavour.Employe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ecell.internshipfairendeavour.Internship.model.internall_md;
import com.ecell.internshipfairendeavour.ModelandViewholder.addexp1_model;
import com.ecell.internshipfairendeavour.ModelandViewholder.addexp1_viewholder;
import com.ecell.internshipfairendeavour.ModelandViewholder.addexp_model;
import com.ecell.internshipfairendeavour.ModelandViewholder.addexp_viewholder;
import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.ecell.internshipfairendeavour.User;
import com.ecell.internshipfairendeavour.profile.models.Personaldet_md;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Student_detail_emp extends AppCompatActivity {
    private Button matri_btn,inter_btn,dip_btn,clg_btn;
    private TextView answer1,ques2,answer2,ques3,answer3;
    private FirebaseRecyclerAdapter<addexp1_model, addexp1_viewholder>adapterexp1;
    private FirebaseRecyclerOptions<addexp1_model> optionsexp1;
    FirebaseRecyclerOptions<addexp_model> optionsexp;
    private FirebaseRecyclerAdapter<addexp_model, addexp_viewholder> adapterexp;
    String userid,cmpid,key;
    private DatabaseReference reff;
    private boolean abili = true;
    private boolean work_exp = true;
    private boolean perdet_1 = true;
    private boolean ed_deta = true;
    String uid;
    private View view1,view2;
    private TextView dob,address,occ,wa_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_detail_emp );
        dob =findViewById( R.id.dob_text );
        view1=findViewById( R.id.layout_test1 );
        view2=findViewById( R.id.layout_test2 );
        address = findViewById( R.id.add_text );
        answer1=findViewById( R.id.answer_1 );
        answer2=findViewById( R.id.answer_2 );
        answer3=findViewById( R.id.answer_3 );

        ques2=findViewById( R.id.ques_2 );
        ques3=findViewById( R.id.ques_3 );
        occ = findViewById( R.id.curr_occ_text );
        wa_no =findViewById( R.id.wa_no_text );
        matri_btn=findViewById( R.id.matr_btn );
        inter_btn=findViewById( R.id.inter_btn );
        dip_btn=findViewById( R.id.dip_btn );
        clg_btn=findViewById( R.id.college_btn );
        final RelativeLayout eddet1 = (RelativeLayout)findViewById(R.id.layout_eddet1);
        final RelativeLayout layout_profile2 = (RelativeLayout)findViewById(R.id.ed_detail1);
        final RelativeLayout layout_profile1 = (RelativeLayout)findViewById(R.id.pers_detail1);
        final RelativeLayout perdet1 = (RelativeLayout)findViewById(R.id.layout_perdet1);
        perdet1.setVisibility(View.GONE);
        final RelativeLayout work_det = (RelativeLayout)findViewById(R.id.work_det);
        final RelativeLayout layout_profile3 = (RelativeLayout)findViewById(R.id.pers_detail3);
        final RelativeLayout abi_det = (RelativeLayout)findViewById(R.id.abi_det);
        final RelativeLayout layout_profile4 = (RelativeLayout)findViewById(R.id.pers_detail4);
        BucketRecyclerView rv_exp =findViewById( R.id.rv_exp );
        rv_exp.setHasFixedSize( true );
        rv_exp.showIfEmpty( view1 );

        //  rv_exp.setLayoutManager( new LinearLayoutManager( this ) );
        rv_exp.setLayoutManager( new LinearLayoutManager(  Student_detail_emp.this));
        userid= getIntent().getStringExtra("userid");
        cmpid= getIntent().getStringExtra("cmpid");
        key= getIntent().getStringExtra("id");
        Log.d("HAS","CHECK " + cmpid +" "+key);

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Internships");
        databaseReference1.keepSynced(true);
        databaseReference1.orderByChild("id").equalTo(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    internall_md value = dataSnapshot1.getValue(internall_md.class);

                    if(!value.getQues1().equals( "Q2. " )){
                        ques2.setVisibility( View.VISIBLE );
                        ques2.setText( value.getQues1() );
                    }
                    if(!value.getQues2().equals( "Q3. " )){
                        ques3.setVisibility( View.VISIBLE );
                        ques3.setText( value.getQues2() );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff=FirebaseDatabase.getInstance().getReference().child( "Users" );
        reff.keepSynced(true);
        reff.orderByChild("uid").equalTo( userid).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){

                    User user = dataSnapshot2.getValue(User.class);
              uid=user.getUid();



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


        reff=FirebaseDatabase.getInstance().getReference().child( "Profile" ).child(userid);
        reff.keepSynced(true);
        reff.orderByChild("uid").equalTo("per"+ userid).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){

                    Personaldet_md personaldet_md = dataSnapshot2.getValue(Personaldet_md.class);

                    dob.setText(personaldet_md.getDob());
                    address.setText(personaldet_md.getAdress());
                    occ.setText(personaldet_md.getOccupation());
                    wa_no.setText(personaldet_md.getWanumber());
Log.d("HAS","HELLOHELLO"+key+uid);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Forms").child( cmpid );
                    databaseReference.keepSynced(true);
                    Log.d("HAS","CHECK");
                    databaseReference.orderByChild("internshipuid").equalTo(key+uid ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot0 : dataSnapshot.getChildren()){
                                applied_intern_md value = dataSnapshot0.getValue(applied_intern_md.class);
                                answer1.setText( value.getAnswer1() );
                                Log.d("HAS","CHECK");
                                if(!value.getAnswer2().equals( "QNP" )){
                                    answer2.setVisibility( View.VISIBLE );
                                    answer2.setText( value.getAnswer2() );
                                }
                                if(!value.getAnswer3().equals( "QNP" )){
                                    answer3.setVisibility( View.VISIBLE );
                                    answer3.setText( value.getAnswer3() );
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        Log.d("has","thi"+userid);
        DatabaseReference databaseReferenceexprv = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( userid).child( "cmpexp" );
        databaseReferenceexprv.keepSynced( true );

        optionsexp = new FirebaseRecyclerOptions.Builder<addexp_model>().setQuery( databaseReferenceexprv, addexp_model.class ).build();

        adapterexp = new FirebaseRecyclerAdapter<addexp_model, addexp_viewholder>( optionsexp ) {

            @Override
            protected void onBindViewHolder(@NonNull final addexp_viewholder holder, int position, @NonNull final addexp_model model) {
                holder.companynamelayout.setText( model.getCompanyname() );
                holder.companystartlayout.setText( model.getCompanystart() );
                holder.companyendlayout.setText( model.getCompanyend() );
                holder.companyrolelayout.setText( model.getCompanyrole() );
                holder.companybenefitslayout.setText( model.getCompanybenefits() );


                holder.companynamelayout.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.expand == false) {
                            holder.companystartlayout.setVisibility( View.VISIBLE );
                            holder.companyendlayout.setVisibility( View.VISIBLE );
                            holder.companyrolelayout.setVisibility( View.VISIBLE );
                            holder.companybenefitslayout.setVisibility( View.VISIBLE );

                            holder.expand = true;

                        } else {
                            holder.companystartlayout.setVisibility( View.GONE );
                            holder.companyendlayout.setVisibility( View.GONE );
                            holder.companyrolelayout.setVisibility( View.GONE );
                            holder.companybenefitslayout.setVisibility( View.GONE );
                            holder.editexp.setVisibility( View.GONE );
                            holder.cancelbtn.setVisibility( View.GONE );
                            holder.expand = false;
                        }
                    }
                } );
            }

            @NonNull
            @Override
            public addexp_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp_viewholder( LayoutInflater.from( Student_detail_emp.this).inflate( R.layout.exp_card_layout, parent, false ) );
            }



        };
        rv_exp.setAdapter(adapterexp);
        adapterexp.startListening();


        //////////-----------------------------------------------
        BucketRecyclerView rv_exp1 = findViewById( R.id.rv_exp1 );
        rv_exp1.setHasFixedSize(true);

rv_exp1.showIfEmpty( view2 );

        rv_exp1.setLayoutManager(new LinearLayoutManager(Student_detail_emp.this));

        DatabaseReference databaseReferenceexprv1 = FirebaseDatabase.getInstance().getReference().child( "Profile" ).child( userid ).child( "cmpskills" );
        databaseReferenceexprv1.keepSynced(true);

        optionsexp1  = new FirebaseRecyclerOptions.Builder<addexp1_model>().setQuery( databaseReferenceexprv1,addexp1_model.class).build();

        adapterexp1 = new FirebaseRecyclerAdapter<addexp1_model, addexp1_viewholder>(optionsexp1) {
            @Override
            protected void onBindViewHolder(@NonNull final addexp1_viewholder holder1, int position, @NonNull final addexp1_model model) {
                holder1.companynamelayout.setText(model.getSkills());

                holder1.show.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!holder1.expand){

                            holder1.companynamelayout.setVisibility(View.VISIBLE);

                            holder1.editexp.setVisibility(View.GONE);
                            holder1.expand = true;
                        }
                        else {

                            holder1.companynamelayout.setVisibility(View.GONE);
                            holder1.editexp.setVisibility(View.GONE);
                            holder1.expand = false;
                        }
                    }
                });



            }

            @NonNull
            @Override
            public addexp1_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new addexp1_viewholder( LayoutInflater.from(Student_detail_emp.this).inflate(R.layout.exp1_card_layout,parent,false));
            }
        };

        rv_exp1.setAdapter(adapterexp1);
        adapterexp1.startListening();
        layout_profile3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (work_exp){
                    work_exp = false;
                    work_det.setVisibility(View.VISIBLE);
                }
                else {
                    work_exp = true;
                    work_det.setVisibility(View.GONE);
                }
            }
        } );
        layout_profile2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed_deta){
                    ed_deta = false;
                    eddet1.setVisibility(View.VISIBLE);
                    matri_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Profile_activity1.class);
                            intent.putExtra( "userid",userid );
                            startActivity(intent);

                        }
                    } );
                    inter_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Profile_activity2.class);
                            intent.putExtra( "userid",userid );
                            startActivity(intent);

                        }
                    } );
                    dip_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Profile_activity3.class);
                            intent.putExtra( "userid",userid );
                            startActivity(intent);

                        }
                    } );
                    clg_btn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), Profile_activity4.class);
                            intent.putExtra( "userid",userid );
                            startActivity(intent);

                        }
                    } );
                }
                else {
                    ed_deta = true;
                    eddet1.setVisibility(View.GONE);
                }
            }
        });
        layout_profile4.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (abili){
                    abili = false;
                    abi_det.setVisibility(View.VISIBLE);
                }
                else {
                    abili = true;
                    abi_det.setVisibility(View.GONE);
                }
            }
        } );
        layout_profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (perdet_1){
                    perdet_1 = false;
                    perdet1.setVisibility(View.VISIBLE);
                }
                else {
                    perdet_1 = true;
                    perdet1.setVisibility(View.GONE);
                }
            }
        });


    }
    @Override
    public void onStart() {
        super.onStart();
        adapterexp.startListening();
        adapterexp1.startListening();


    }
    @Override
    public void onStop() {
        super.onStop();
        adapterexp.stopListening();
        adapterexp1.stopListening();
    }


}
