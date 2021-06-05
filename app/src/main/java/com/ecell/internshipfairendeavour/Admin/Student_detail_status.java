package com.ecell.internshipfairendeavour.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecell.internshipfairendeavour.Employe.Profile_activity1;
import com.ecell.internshipfairendeavour.Employe.Profile_activity2;
import com.ecell.internshipfairendeavour.Employe.Profile_activity3;
import com.ecell.internshipfairendeavour.Employe.Profile_activity4;
import com.ecell.internshipfairendeavour.Employe.Student_detail_emp;
import com.ecell.internshipfairendeavour.Employe.applied_intern_md;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Student_detail_status extends AppCompatActivity {
    private Button matri_btn,inter_btn,dip_btn,clg_btn,acc,rej;

    private FirebaseRecyclerAdapter<addexp1_model, addexp1_viewholder> adapterexp1;
    private FirebaseRecyclerOptions<addexp1_model> optionsexp1;
    FirebaseRecyclerOptions<addexp_model> optionsexp;
    private FirebaseRecyclerAdapter<addexp_model, addexp_viewholder> adapterexp;
    String userid,cmpid,key;
    private TextView user_ph;
    private TextView name_user;
    private TextView user_email;
    private DatabaseReference reff;
    private boolean abili = true;
    private ImageView imageuser;
    ProgressBar pb_userimg;
    private boolean work_exp = true;
    private boolean perdet_1 = true;
    private boolean ed_deta = true;
    String uid;
    private View view1,view2;
    private TextView dob,address,occ,wa_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_student_detail_status );
        address = findViewById( R.id.add_text );
        dob =findViewById( R.id.dob_text );
        imageuser =findViewById(R.id.image_user);

        acc = findViewById( R.id.acc );
        rej = findViewById( R.id.rej );


        name_user = findViewById(R.id.name_user);
        user_email = findViewById(R.id.user_email);
        user_ph=findViewById( R.id.user_ph );
        userid= getIntent().getStringExtra("userid");
        occ = findViewById( R.id.curr_occ_text );
        view1=findViewById( R.id.layout_test1 );
        view2=findViewById( R.id.layout_test2 );
        wa_no =findViewById( R.id.wa_no_text );
        matri_btn=findViewById( R.id.matr_btn );
        inter_btn=findViewById( R.id.inter_btn );
        dip_btn=findViewById( R.id.dip_btn );
        pb_userimg =findViewById(R.id.pb_userimg);
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


        acc.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.child( "Users" ).child( userid ).child( "officialstatus" ).setValue( "yes" );
            }
        } );
        rej.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                rootRef.child( "Users" ).child( userid ).child( "officialstatus" ).setValue( "no" );
            }
        } );

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);

                    name_user.setText(user.name);
                    user_email.setText(user.email);
                    String code=user.getCode();
                    if(user.code!=null){
                        user_ph.setText("(+"+code+")"+ user.getContactn() );
                    }
                    else{
                        user_ph.setText("(+91)"+ user.getContactn() );
                    }
                    //  user_ph.setText( "+91"+user.contactn );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final DatabaseReference databaseReferencemain = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReferencemain.keepSynced(true);
        databaseReferencemain.orderByChild("uid").equalTo( FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);
                    if (user.profileimg!=null){

                        Picasso.get().load(user.getProfileimg()).resize(400,400).into(imageuser);

                    }
                    else {
                        imageuser.setImageResource(R.drawable.user);

                    }
                    pb_userimg.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reff= FirebaseDatabase.getInstance().getReference().child( "Users" );
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



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
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
                return new addexp_viewholder( LayoutInflater.from( Student_detail_status.this).inflate( R.layout.exp_card_layout, parent, false ) );
            }



        };
        rv_exp.setAdapter(adapterexp);
        adapterexp.startListening();

        BucketRecyclerView rv_exp1 = findViewById( R.id.rv_exp1 );
        rv_exp1.setHasFixedSize(true);


        rv_exp1.showIfEmpty( view2 );

        rv_exp1.setLayoutManager(new LinearLayoutManager(Student_detail_status.this));

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
                return new addexp1_viewholder( LayoutInflater.from(Student_detail_status.this).inflate(R.layout.exp1_card_layout,parent,false));
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