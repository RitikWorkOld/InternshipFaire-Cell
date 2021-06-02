package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.ecell.internshipfairendeavour.Admin.model.internall_md;
import com.ecell.internshipfairendeavour.Internship.Desc1Fragment;
import com.ecell.internshipfairendeavour.Internship.Desc2Fragment;
import com.ecell.internshipfairendeavour.Internship.DescFragment;
import com.ecell.internshipfairendeavour.R;
import com.ecell.internshipfairendeavour.SimpleFragmentPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

public class InternDetail_admin extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
private Button accept,reject;

    private TabLayout tabLayout;
    DatabaseReference databaseReferencedetail;
    String key,userid;
    ImageView pencil;
    ImageView cmpimage;
    TextView intername,cmpname,location,stipend,duration,worktime,status_show;

    TextView ctext1,ctext2,ctext3;

    //This is our viewPager
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_intern_detail_admin );


accept=findViewById( R.id.accept );
reject=findViewById( R.id.reject );
pencil=findViewById( R.id.pencil_desc );

status_show=findViewById( R.id.status_show );
        cmpimage = findViewById(R.id.icd_cmp_img);
        intername = findViewById(R.id.cmp_work_detail);
        cmpname = findViewById(R.id.cmp_name_detail);
        location = findViewById(R.id.icd_location);
        stipend = findViewById(R.id.stipend_detail);
        duration = findViewById(R.id.icd_duration);
        worktime = findViewById(R.id.work_details);
        ctext1 = findViewById(R.id.title1);
        ctext2 = findViewById( R.id.title2);
        ctext3 = findViewById(R.id.title3);



        key=getIntent().getStringExtra( "key" );
        userid=getIntent().getStringExtra( "userId" );
        pencil.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Edit_Internship.class);
                intent.putExtra( "key",key );

                startActivity( intent );
            }
        } );


accept.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference databaseReferencecmpexp = FirebaseDatabase.getInstance().getReference().child("Internships").child( key );
        databaseReferencecmpexp.keepSynced(true);
        databaseReferencecmpexp.child("id_status").setValue(userid+"Accepted");
        databaseReferencecmpexp.child("admin_status").setValue("Accepted");
        accept.setText( "Accepted" );
        Toast.makeText(getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
        finish();
    }
} );
reject.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DatabaseReference databaseReferencecmpexp1 = FirebaseDatabase.getInstance().getReference().child("Internships").child( key );
        databaseReferencecmpexp1.keepSynced(true);
        databaseReferencecmpexp1.child("id_status").setValue(userid+"Rejected");
        databaseReferencecmpexp1.child("admin_status").setValue("Rejected");
        reject.setText( "Rejected" );
        Toast.makeText(getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();
        finish();
    }
} );
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("key",key);
        DescFragment descFragment = new DescFragment();
        descFragment.setArguments(bundle);
        Desc1Fragment desc1Fragment = new Desc1Fragment();
        desc1Fragment.setArguments(bundle);
        Desc2Fragment desc2Fragment = new Desc2Fragment();
        desc2Fragment.setArguments(bundle);

        fragmentTransaction.commit();

        databaseReferencedetail = FirebaseDatabase.getInstance().getReference().child("Internships");
        databaseReferencedetail.keepSynced(true);
        databaseReferencedetail.orderByChild("id").equalTo(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    internall_md valueintern = dataSnapshot1.getValue(internall_md.class);

                    Picasso.get().load(valueintern.intimguri).resize( 400,400 ).into(cmpimage);
                    status_show.setText( valueintern.getAdmin_status() );
                    intername.setText(valueintern.intname);
                    cmpname.setText(valueintern.cmpname);
                    location.setText(valueintern.location);
                    stipend.setText(valueintern.amount);
                    duration.setText(valueintern.duration);
                    worktime.setText(valueintern.worktime);
                    if (valueintern.getCtext1().equals("no")){
                        ctext1.setPaintFlags(ctext1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    if (valueintern.getCtext2().equals("no")){
                        ctext2.setPaintFlags(ctext2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    if (valueintern.getCtext3().equals("no")){
                        ctext3.setPaintFlags(ctext3.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//Initializing the tablayout
        tabLayout = (TabLayout) findViewById( R.id.sliding_tab );

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Description));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Description1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.Description2));
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Creating our pager adapter
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(),key);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
