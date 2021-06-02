package com.ecell.internshipfairendeavour.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
public class Internship_Admin extends Fragment {
    BucketRecyclerView rv_internall;
    DatabaseReference drinternall;
    private View no_app;
    FirebaseRecyclerOptions<internall_md> optionsinternall;
    FirebaseRecyclerAdapter<internall_md, internall_vh> adapterinternall;
    public Internship_Admin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate( R.layout.fragment_internship__admin, container, false );


        rv_internall = view.findViewById( R.id.recyclerview );
        rv_internall.setHasFixedSize( true );
        no_app=view.findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );
        rv_internall.setLayoutManager( new LinearLayoutManager( container.getContext() ) );
        drinternall = FirebaseDatabase.getInstance().getReference().child( "Internships" );
        drinternall.keepSynced( true );
        Query query = drinternall.orderByChild( "admin_status" ).equalTo( "Accepted" );

        optionsinternall = new FirebaseRecyclerOptions.Builder<internall_md>().setQuery( query, internall_md.class ).build();

        adapterinternall = new FirebaseRecyclerAdapter<internall_md, internall_vh>( optionsinternall ) {
            @Override
            protected void onBindViewHolder(@NonNull internall_vh holder, int position, @NonNull final internall_md model) {
                holder.cmpname.setText( model.getIntname() );
                holder.cmpsubname.setText( model.getCmpname() );
                Picasso.get().load( model.getIntimguri() ).into( holder.cmpimg );
                holder.location.setText( model.getLocation() );
                holder.amount.setText( model.getAmount() );
                holder.duration.setText( model.getDuration() );

                holder.layout_card.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent( getActivity(), Application_detailAdmin.class );
                        intent.putExtra( "id", model.getId() );
intent.putExtra( "cmpid",model.getCompanyid() );

                        Log.d("Has","checkthis2"+model.getId());
                        startActivity( intent );
                    }
                } );
            }

            @NonNull
            @Override
            public internall_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new internall_vh( LayoutInflater.from( view.getContext() ).inflate( R.layout.dashboard_vh, parent, false ) );
            }
        };
        rv_internall.setAdapter( adapterinternall );
        adapterinternall.startListening();




        return view;
    }
}
