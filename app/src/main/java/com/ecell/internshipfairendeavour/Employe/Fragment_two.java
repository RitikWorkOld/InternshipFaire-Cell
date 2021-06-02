package com.ecell.internshipfairendeavour.Employe;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecell.internshipfairendeavour.Admin.model.internall_vh;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Internship.model.internall_md;
import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.Notifications.Notifications;
import com.ecell.internshipfairendeavour.Notifications.Notifications_Dots;
import com.ecell.internshipfairendeavour.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_two extends Fragment {
    BucketRecyclerView rv_internall;
    private View no_app;
    DatabaseReference drinternall;
    FirebaseRecyclerOptions<internall_md> optionsinternall;
    FirebaseRecyclerAdapter<internall_md, internall_vh> adapterinternall;
    private FirebaseAuth mFirebaseAuth;
    ImageView notification_btn,notification_badge;
    private String cmpid,eid;

    public Fragment_two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate( R.layout.fragment_two, container, false );
        mFirebaseAuth = FirebaseAuth.getInstance();
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
        rv_internall = view.findViewById( R.id.recyclerview);
        rv_internall.setHasFixedSize(true);
        no_app=view.findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );
        rv_internall.setLayoutManager(new LinearLayoutManager(container.getContext()));
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("eid").equalTo( Objects.requireNonNull( mFirebaseAuth.getCurrentUser() ).getUid()).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Employe user = dataSnapshot1.getValue(Employe.class);
                    cmpid=user.getCmpid();
                    eid=user.getEid();

                    drinternall = FirebaseDatabase.getInstance().getReference().child("Internships");
                    drinternall.keepSynced(true);
                    Query query = drinternall.orderByChild("id_status").equalTo(eid+"Accepted");

                    optionsinternall = new FirebaseRecyclerOptions.Builder<internall_md>().setQuery(query,internall_md.class).build();

                    adapterinternall = new FirebaseRecyclerAdapter<internall_md, internall_vh>(optionsinternall) {
                        @Override
                        protected void onBindViewHolder(@NonNull internall_vh holder, int position, @NonNull final internall_md model) {
                            holder.cmpname.setText(model.getIntname());
                            holder.cmpsubname.setText(model.getCmpname());
                            Picasso.get().load(model.getIntimguri()).into(holder.cmpimg);
                            holder.location.setText(model.getLocation());
                            holder.amount.setText(model.getAmount());
                            holder.duration.setText(model.getDuration());

                            holder.layout_card.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getActivity(), Application_detail.class);
                                    intent.putExtra("key",model.getId());
                                    intent.putExtra("cmpid",model.getCompanyid());
                                    startActivity( intent );
                                }
                            } );
                        }

                        @NonNull
                        @Override
                        public internall_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new internall_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.dashboard_vh, parent,false));
                        }
                    };
                    rv_internall.setAdapter(adapterinternall);
                    adapterinternall.startListening();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });







        return view;
    }


}
