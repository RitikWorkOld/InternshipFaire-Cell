package com.ecell.internshipfairendeavour.Employe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Notifications.Notifications;
import com.ecell.internshipfairendeavour.Notifications.Notifications_Dots;
import com.ecell.internshipfairendeavour.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.Objects;

import static android.view.View.GONE;

public class FragmentOneA extends Fragment implements View.OnClickListener {
private RadioGroup radioGroup_detail,radioGroup_type,radioGroup_duration,radioGroup_stipend_perk;
private CheckBox one,two,three,four;
private RadioButton radio_fixed,radio_perfor_based,radio_unpaid,radio_negot;
    private String comp_id;
    private String wcg1;
    private String img;
    private String duration;
    private String month;
    private String description;
    ImageView notification_btn,notification_badge;

    private String inter_det,inter_type,start_date,stipnPerks;

    private Spinner spinner_permonth,spinner_choose_scale;
    private String permonth,scale,name;

    private Button add_questions;


    private LinearLayout perform_based_show1,negot_layout,unpaid_noshow;
    private EditText other_selected,city_edit_text,no_of_opening,skills,que1,que2,resp_edit_text,main_stipend_et,inc_based_et,neg_to_et;
    private TextView perform_based1,title_city,FromET,ToET,another_que,another_que2,leave;


    private RadioButton intern_type_option1,intern_type_option2;
    private RadioButton immed,later;
    private LinearLayout layout1,layout2;
private RadioButton radiobtn1,radiobtn2,radiobtn3,radiobtn4,radiobtn5,
        radiobtn6,radiobtn7,radiobtn8,radiobtn9,radiobtn10,radiobtn11,radiobtn12,radiobtn13_other;
    private View coming_soon;
    private Button sbmit_btn;
    FloatingActionButton help_fab;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate( R.layout.fragment_one_a,container,false);



        notification_btn = (ImageView) view.findViewById(R.id.iv_notification_btn);
        notification_badge = (ImageView)view.findViewById(R.id.notificationbadge);
        notification_badge.setVisibility(View.GONE);
        DatabaseReference databaseReferencenot = FirebaseDatabase.getInstance().getReference().child("NotificationDots")
                .child( FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReferencenot.keepSynced(true);
        databaseReferencenot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Notifications_Dots notifications_dots = dataSnapshot.getValue(Notifications_Dots.class);
                if (notifications_dots != null)
                {
                    if (notifications_dots.getDotstatus().equals("yes")){
                        notification_badge.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReferencenotup = FirebaseDatabase.getInstance().getReference().child("NotificationDots")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReferencenotup.child("dotstatus").setValue("no");
                databaseReferencenotup.keepSynced(true);
                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);

            }
        });
        skills=view.findViewById( R.id.skills );

        radioGroup_detail=view.findViewById( R.id.group_detail );
        radioGroup_type=view.findViewById( R.id.options_interntype );
        radioGroup_duration=view.findViewById( R.id.intern_duration );

        FromET=view.findViewById( R.id.from_editText );
        spinner_permonth=view.findViewById( R.id.spinner_permonth );
         spinner_choose_scale=view.findViewById( R.id.choose_scale );
        ToET=view.findViewById( R.id.to_editText );
        que1=view.findViewById( R.id.quest_2 );
        resp_edit_text=view.findViewById( R.id.resp_edit_text );
        main_stipend_et=view.findViewById( R.id.main_stipend_edit_text );
        inc_based_et=view.findViewById( R.id.inc_based_et );
        neg_to_et=view.findViewById( R.id.neg_to_et );


        leave=view.findViewById( R.id.leave );
        leave.setVisibility( GONE );


        another_que2=view.findViewById( R.id.anoth_que2 );
        que2=view.findViewById( R.id.quest_3 );
radiobtn1=view.findViewById( R.id.option_1 );
perform_based1=view.findViewById( R.id.perfor_based_show );
immed=view.findViewById( R.id.radio_immed );
another_que=view.findViewById( R.id.anoth_que );
later=view.findViewById( R.id.radio_later );
layout1=view.findViewById( R.id.later_duration1 );
layout2=view.findViewById( R.id.later_duration2 );
radio_fixed=view.findViewById( R.id.radio_fixed );
no_of_opening=view.findViewById( R.id.no_of_opening );
        radio_perfor_based=view.findViewById( R.id.radio_perf_based );
        radio_unpaid=view.findViewById( R.id.radio_unpaid );
        radio_negot=view.findViewById( R.id.radio_nego );
        intern_type_option1=view.findViewById( R.id.option_type1 );
        intern_type_option2=view.findViewById( R.id.option_type2 );
perform_based_show1=view.findViewById( R.id.perform_based_show1 );
radioGroup_stipend_perk=view.findViewById( R.id.stipendperk );
negot_layout=view.findViewById( R.id.negotiable_layout );
unpaid_noshow=view.findViewById( R.id.unpaid_noshow );
other_selected=view.findViewById( R.id.other_selected );
one=view.findViewById( R.id.checkbox_certificate );
        two=view.findViewById( R.id.checkbox_LOR );
        three=view.findViewById( R.id.checkbox_flex_workhr );
        four=view.findViewById( R.id.checkbox_days_week );
        radiobtn2=view.findViewById( R.id.option_2 );
        radiobtn3=view.findViewById( R.id.option_3 );
        radiobtn4=view.findViewById( R.id.option_4 );
        radiobtn5=view.findViewById( R.id.option_5 );
        radiobtn6=view.findViewById( R.id.option_6 );
        radiobtn7=view.findViewById( R.id.option_7 );
        radiobtn8=view.findViewById( R.id.option_8 );
        radiobtn9=view.findViewById( R.id.option_9 );
        radiobtn10=view.findViewById( R.id.option_10 );
        radiobtn11=view.findViewById( R.id.option_11 );
        radiobtn12=view.findViewById( R.id.option_12 );
        radiobtn13_other=view.findViewById( R.id.option_13 );
        title_city=view.findViewById( R.id.title_city );
        city_edit_text=view.findViewById( R.id.city_edittext );
        spinner_choose_scale.setVisibility( GONE );
        another_que.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    que1.setVisibility( View.VISIBLE );
                another_que.setVisibility( GONE );
                leave.setVisibility( View.VISIBLE );
                another_que2.setVisibility( View.VISIBLE );
            }
        } );
        another_que2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                que2.setVisibility( View.VISIBLE );
                another_que2.setVisibility( GONE );
            }
        } );


        final Spinner spinner_duration=view.findViewById( R.id.spinner_duration );
        final Spinner spinner_month=view.findViewById( R.id.spinner_month );
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource( getContext(),R.array.duration,R.layout.spinner_item_intern );

        spinner_duration.setAdapter( adapter );
        spinner_duration.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration=spinner_duration.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource( getContext(),R.array.month,R.layout.spinner_item_intern );

        spinner_month.setAdapter( adapter1 );
        spinner_month.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month=spinner_month.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource( getContext(),R.array.per_month,R.layout.spinner_item_intern );

        spinner_permonth.setAdapter( adapter2 );
        spinner_permonth.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                permonth=spinner_permonth.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource( getContext(),R.array.per_scale,R.layout.spinner_item_intern );

        spinner_choose_scale.setAdapter( adapter3 );
        spinner_choose_scale.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scale=spinner_choose_scale.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );
        radiobtn1.setOnClickListener( this );
        radiobtn2.setOnClickListener( this );
        radiobtn3.setOnClickListener( this );
        radiobtn4.setOnClickListener( this );
        radiobtn5.setOnClickListener( this );
        radiobtn6.setOnClickListener( this );
        radiobtn7.setOnClickListener( this );
        radiobtn8.setOnClickListener( this );
        radiobtn9.setOnClickListener( this );
        radiobtn10.setOnClickListener( this );
        radiobtn11.setOnClickListener( this );
        radiobtn12.setOnClickListener( this );
        radiobtn13_other.setOnClickListener( this );
        intern_type_option1.setOnClickListener( this );
        intern_type_option2.setOnClickListener( this );
        immed.setOnClickListener( this );
        later.setOnClickListener( this );
        radio_negot.setOnClickListener( this );
        radio_unpaid.setOnClickListener( this );
        radio_perfor_based.setOnClickListener( this );
        radio_fixed.setOnClickListener( this );


        sbmit_btn=view.findViewById( R.id.sbmit_btn );
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Employe" ).child( FirebaseAuth.getInstance().getCurrentUser().getUid() );
        databaseReference.keepSynced( true );
        databaseReference.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Employe employe = dataSnapshot.getValue(Employe.class);
img=employe.getProfileimg();
name=employe.getName();

                    description=employe.getDescrip();
                comp_id= employe.getCmpid()   ;


                    wcg1="";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );




        sbmit_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder( requireActivity() );
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher_round );
                builder.setMessage("Do you want to submit your Application?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String notiid = FirebaseDatabase.getInstance().getReference().child("Internships")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid() ).push().getKey();
                                int option = radioGroup_detail.getCheckedRadioButtonId();
                                RadioButton radioButton = view.findViewById( option );

                                int option_type = radioGroup_type.getCheckedRadioButtonId();
                                RadioButton radioButton_type = view.findViewById( option_type );

                                int option_duration=radioGroup_duration.getCheckedRadioButtonId();
                                RadioButton radioButton_dur=view.findViewById( option_duration );

                                int option_stipend = radioGroup_stipend_perk.getCheckedRadioButtonId();
                                RadioButton radioButton_stipend = view.findViewById( option_stipend );

                                if(!radioButton.getText().equals( "Other" ) && other_selected.getText().toString().isEmpty() ||
                                        radioButton.getText().equals( "Other" ) && !other_selected.getText().toString().isEmpty()  ){
                                    if(!radioButton_type.getText().equals( "Regular (In-Office/On-field)" )&&
                                            city_edit_text.getText().toString().isEmpty()|| radioButton_type.getText().toString().equals( "Regular (In-Office/On-field)" )&& !city_edit_text.getText().toString().isEmpty()&& !no_of_opening.getText().toString().isEmpty()){
                                        if(!radioButton_dur.getText().equals( "Later" ) && FromET.getText().toString().isEmpty() && ToET.getText().toString().isEmpty() ||radioButton_dur.getText().equals( "Later" ) && !FromET.getText().toString().isEmpty() && !ToET.getText().toString().isEmpty() ){
                                            if(!resp_edit_text.getText().toString().isEmpty()){
                                                if(!skills.getText().toString().isEmpty()){
                                                    if(radioButton_stipend.getText().equals( "Fixed" )&& !main_stipend_et.getText().toString().isEmpty()
                                                    || radioButton_stipend.getText().equals( "Performance based" ) && !main_stipend_et.getText().toString().isEmpty() && !inc_based_et.getText().toString().isEmpty()
                                                    || radioButton_stipend.getText().equals( "Negotiable" ) && !main_stipend_et.getText().toString().isEmpty() && !neg_to_et.getText().toString().isEmpty()
                                                    || radioButton_stipend.getText().equals( "Unpaid" )){

                                                        DatabaseReference databaseReference_resp = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                        databaseReference_resp.keepSynced(true);
                                                        databaseReference_resp.child("desc2").setValue( resp_edit_text.getText().toString());
                                                        databaseReference_resp.child("wca1").setValue( skills.getText().toString());

                                                        //saving session
                                                        databaseReference_resp.child("desc1").setValue(description );
                                                        databaseReference_resp.child("intimguri").setValue(img );
                                                        databaseReference_resp.child("id").setValue(notiid );
                                                        databaseReference_resp.child("companyid").setValue(comp_id );
                                                        databaseReference_resp.child("cmpname").setValue(name );
                                                        databaseReference_resp.child( "ctext2" ).setValue( "yes" );
                                                        databaseReference_resp.child( "ctext3" ).setValue( "yes" );
                                                        databaseReference_resp.child( "worktime" ).setValue( "2-3 hour/day" );
                                                        databaseReference_resp.child("ques1").setValue( "Q2. "+que1.getText().toString());
                                                        databaseReference_resp.child("ques2").setValue( "Q3. "+que2.getText().toString());
                                                        databaseReference_resp.child("id_status").setValue("Not Checked" );
                                                        databaseReference_resp.child("admin_status").setValue("Not Checked" );
                                                        databaseReference_resp.child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid() );



                                                        if(one.isChecked())
                                                        {
                                                            wcg1 = wcg1 + "● Certificate"+"\n";

                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReference.keepSynced( true );
                                                            databaseReference.child("wcg1").setValue( wcg1+"." );

                                                        }
                                                        if(two.isChecked()) {
                                                            wcg1 = wcg1 + "● Letter of recommendation"+"\n";
                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReference.keepSynced( true );
                                                            databaseReference.child("wcg1").setValue( wcg1 );

                                                        }
                                                        if(three.isChecked()) {
                                                            wcg1 = wcg1 + "● Flexible Work hours"+"\n";
                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReference.keepSynced( true );
                                                            databaseReference.child( "wcg1" ).setValue( wcg1 );

                                                        }
                                                        if(four.isChecked()) {
                                                            wcg1 = wcg1 + "● 5 days a week"+"\n";
                                                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReference.keepSynced( true );
                                                            databaseReference.child("wcg1").setValue( wcg1 );

                                                        }

                                                        if(radioButton.getText().equals( "Other" ) && !other_selected.getText().toString().isEmpty() ) {
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReferencec_detail1.keepSynced(true);
                                                            databaseReferencec_detail1.child("intname").setValue(other_selected.getText().toString());
                                                        }
                                                        if(!radioButton.getText().equals( "Other" ) && other_selected.getText().toString().isEmpty() ) {
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReferencec_detail1.keepSynced(true);
                                                            databaseReferencec_detail1.child("intname").setValue(radioButton.getText().toString());
                                                        }
                                                        if(radioButton_type.getText().equals( "Regular (In-Office/On-field)" )&&
                                                                !city_edit_text.getText().toString().isEmpty() && !no_of_opening.getText().toString().isEmpty() ) {
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReferencec_detail1.keepSynced(true);
                                                            databaseReferencec_detail1.child( "type" ).setValue( radioButton_type.getText().toString() );
                                                            databaseReferencec_detail1.child( "location" ).setValue( city_edit_text.getText().toString() );
                                                            databaseReferencec_detail1.child( "ctext1" ).setValue( "no" );

                                                            databaseReferencec_detail1.child( "no_opening" ).setValue( no_of_opening.getText().toString() );
                                                        }
                                                        if(radioButton_type.getText().equals( "Work from home" )&&
                                                               !no_of_opening.getText().toString().isEmpty() ) {
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReferencec_detail1.keepSynced(true);
                                                            databaseReferencec_detail1.child( "type" ).setValue( radioButton_type.getText().toString() );
                                                            databaseReferencec_detail1.child( "location" ).setValue( "Work from home" );
                                                            databaseReferencec_detail1.child( "ctext1" ).setValue( "yes" );
                                                            databaseReferencec_detail1.child( "no_opening" ).setValue( no_of_opening.getText().toString() );
                                                        }
                                                        if(!(radioButton_dur.getText().equals( "Later" )) && FromET.getText().toString().isEmpty() && ToET.getText().toString().isEmpty()){

                                                            DatabaseReference databaseReference_duration = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReference_duration.keepSynced(true);
                                                            databaseReference_duration.child("duration").setValue( duration+" "+month+"s" );
                                                        }
                                                        if((radioButton_dur.getText().equals( "Later" )) && !FromET.getText().toString().isEmpty() && !ToET.getText().toString().isEmpty()){

                                                            DatabaseReference databaseReference_duration = FirebaseDatabase.getInstance().getReference().child("Internships").child( notiid );
                                                            databaseReference_duration.keepSynced(true);
                                                            databaseReference_duration.child("duration").setValue( duration+" "+month+"s" );
                                                            databaseReference_duration.child( "from" ).setValue( FromET.getText().toString() );
                                                            databaseReference_duration.child( "to" ).setValue( ToET.getText().toString() );
                                                        }

                                                        if(radioButton_stipend.getText().equals( "Fixed" )&& !main_stipend_et.getText().toString().isEmpty()){
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReferencec_detail1.keepSynced( true );
                                                            databaseReferencec_detail1.child( "amount" ).setValue( main_stipend_et.getText().toString() );
                                                            databaseReferencec_detail1.child( "stipend_per" ).setValue(permonth );

                                                        }
                                                        if( radioButton_stipend.getText().equals( "Performance based" ) && !main_stipend_et.getText().toString().isEmpty() && !inc_based_et.getText().toString().isEmpty()){
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReferencec_detail1.keepSynced( true );
                                                            databaseReferencec_detail1.child( "amount" ).setValue( main_stipend_et.getText().toString() );
                                                            databaseReferencec_detail1.child( "incentiveBased" ).setValue( inc_based_et.getText().toString() );
                                                            databaseReferencec_detail1.child( "stipend_per" ).setValue(permonth );
                                                            databaseReferencec_detail1.child( "stipend_scale_per" ).setValue(scale );
                                                        }
                                                        if(radioButton_stipend.getText().equals( "Negotiable" ) && !main_stipend_et.getText().toString().isEmpty() && !neg_to_et.getText().toString().isEmpty()){
                                                            DatabaseReference databaseReferencec_detail1 = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReferencec_detail1.keepSynced( true );
                                                            databaseReferencec_detail1.child( "amount" ).setValue( main_stipend_et.getText().toString()+" - " + neg_to_et.getText().toString()  );
                                                            databaseReferencec_detail1.child( "negotiatedTo" ).setValue( neg_to_et.getText().toString() );
                                                            databaseReferencec_detail1.child( "stipend_per" ).setValue(permonth );
                                                        }
                                                        if( radioButton_stipend.getText().equals( "Unpaid" )){
                                                            DatabaseReference databaseReferencec_ = FirebaseDatabase.getInstance().getReference().child( "Internships" ).child( notiid );
                                                            databaseReferencec_.keepSynced( true );
                                                            databaseReferencec_.child( "amount" ).setValue( "Unpaid");
                                                        }

                                                     Intent intent = new Intent(getActivity(), Application_Proceed.class);

                                                             startActivity(intent);

                                                    }
                                                    else{


                                                        Toast.makeText( getActivity(), "Please fill all the required fields", Toast.LENGTH_SHORT ).show();


                                                    }
                                                }
                                                else{

                                                    skills.setError( "Required" );
                                                    Toast.makeText( getActivity(), "Field Required1", Toast.LENGTH_SHORT ).show();


                                                }

                                            }
                                            else{

                                                resp_edit_text.setError( "Required" );
                                                Toast.makeText( getActivity(), "Field Required2", Toast.LENGTH_SHORT ).show();


                                            }


                                        }
                                        else{
                                            Toast.makeText( getActivity(), "field Required StartDate", Toast.LENGTH_SHORT ).show();

                                        }






                                    }
                                    else{

                                        Toast.makeText( getActivity(), "Field Required3", Toast.LENGTH_SHORT ).show();

                                    }


                                }
                                else{
                                    if(radioButton.getText().equals( "Other" ) && other_selected.getText().toString().isEmpty() ) {
                                        other_selected.setError( "Required" );
                                        Toast.makeText( getActivity(), "Other field Required", Toast.LENGTH_SHORT ).show();
                                    }
                                }















                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

                // Concatenation of the checked options in if

                // isChecked() is used to check whether
                // the CheckBox is in true state or not.



                //---------------------------------------------------------------------------------------------

                //---------------------------------------------------------------------------------------------





            }
        } );







        //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        //SWIPE CODE FOR FRAGMENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        return view;
    }


    @Override
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.option_1:
            case R.id.option_6:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
            case R.id.option_5:
            case R.id.option_7:
            case R.id.option_8:
            case R.id.option_9:
            case R.id.option_10:
            case R.id.option_11:
            case R.id.option_12:
                other_selected.setVisibility( GONE );

                break;
            case R.id.option_13:
                other_selected.setVisibility( View.VISIBLE );
                break;
            case R.id.option_type1:
                title_city.setVisibility( View.VISIBLE );
                city_edit_text.setVisibility( View.VISIBLE );


                break;
            case R.id.option_type2:
                title_city.setVisibility( GONE );
                city_edit_text.setVisibility( GONE );


                break;
            case R.id.radio_immed:
                layout1.setVisibility( GONE );
                layout2.setVisibility( GONE );

                break;
            case R.id.radio_later:
                layout1.setVisibility( View.VISIBLE );
                layout2.setVisibility( View.VISIBLE );


                break;
            case R.id.radio_fixed:
                negot_layout.setVisibility( GONE );
                perform_based1.setVisibility( GONE );

                perform_based_show1.setVisibility( GONE );
                spinner_choose_scale.setVisibility( GONE );
                spinner_permonth.setVisibility( View.VISIBLE );
                unpaid_noshow.setVisibility( View.VISIBLE );
                break;
            case R.id.radio_perf_based:
                perform_based1.setVisibility( View.VISIBLE );
                perform_based_show1.setVisibility( View.VISIBLE );
                unpaid_noshow.setVisibility( View.VISIBLE );

                spinner_permonth.setVisibility( View.VISIBLE );
                negot_layout.setVisibility( GONE );
                spinner_choose_scale.setVisibility( View.VISIBLE );
                break;
            case R.id.radio_nego:
                negot_layout.setVisibility( View.VISIBLE );
                unpaid_noshow.setVisibility( View.VISIBLE );
                perform_based1.setVisibility( GONE );

                spinner_choose_scale.setVisibility( GONE );
                spinner_permonth.setVisibility( View.VISIBLE );
                perform_based_show1.setVisibility( GONE );
                break;
            case R.id.radio_unpaid:
                negot_layout.setVisibility( GONE );
                spinner_choose_scale.setVisibility( GONE );
                perform_based1.setVisibility( GONE );

                spinner_permonth.setVisibility( GONE );
                perform_based_show1.setVisibility( GONE );
                unpaid_noshow.setVisibility( GONE );

                break;


        }

    }
}
