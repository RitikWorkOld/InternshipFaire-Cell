package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ecell.internshipfairendeavour.Admin.model.internall_md;
import com.ecell.internshipfairendeavour.Admin.model.internall_vh;

import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class Intern_Fragment extends AppCompatActivity {
    BucketRecyclerView rv_internall;
    DatabaseReference drinternall;
    private View no_app;
    FirebaseRecyclerOptions<internall_md> optionsinternall;
    FirebaseRecyclerAdapter<internall_md, internall_vh> adapterinternall;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.fragment_intern_);

        rv_internall = findViewById(R.id.rv_internall);
        rv_internall.setHasFixedSize(true);
        no_app=findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );
        rv_internall.setLayoutManager(new LinearLayoutManager(this));

        drinternall = FirebaseDatabase.getInstance().getReference().child("Internships");
        Query query = drinternall.orderByChild("admin_status" ).equalTo("Not Checked");
        drinternall.keepSynced(true);

        optionsinternall = new FirebaseRecyclerOptions.Builder<internall_md>().setQuery(query,internall_md.class).build();

        adapterinternall = new FirebaseRecyclerAdapter<internall_md, internall_vh>(optionsinternall) {
            @Override
            protected void onBindViewHolder(@NonNull internall_vh holder, int position, @NonNull final internall_md model) {
                holder.cmpname.setText(model.getIntname());
                holder.cmpsubname.setText(model.getCmpname());
                Picasso.get().load(model.getIntimguri()).resize( 400,400 ).into(holder.cmpimg);
                holder.location.setText(model.getLocation());
                holder.amount.setText(model.getAmount());
                holder.duration.setText(model.getDuration());



                holder.layout_card.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intern_Fragment.this, InternDetail_admin.class);
                        intent.putExtra( "key",model.getId() );
                        intent.putExtra( "userId",model.getUserid() );


                        startActivity( intent );
                    }
                } );

            }

            @NonNull
            @Override
            public internall_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new internall_vh(LayoutInflater.from(Intern_Fragment.this).inflate(R.layout.internship_card_layout, parent,false));
            }
        };
        rv_internall.setAdapter(adapterinternall);
        adapterinternall.startListening();
    }
}
