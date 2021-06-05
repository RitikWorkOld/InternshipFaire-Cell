package com.ecell.internshipfairendeavour.Employe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.ecell.internshipfairendeavour.Contact_Us;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Login_Employe;
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

import com.squareup.picasso.Picasso;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_three extends Fragment {

    public Fragment_three() {
        // Required empty public constructor
    }
    private TextView name_user,desc_text;
    private TextView user_email;
    private TextView user_ph;
    FloatingActionButton help_fab;
    private ProgressBar pb_userimg;
    private ImageView imageuser;
    private FirebaseAuth mFirebaseAuth;
    ImageView notification_btn,notification_badge;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_three, container, false );
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
        imageuser = view.findViewById(R.id.image_user);
        name_user = view.findViewById(R.id.name_user);
        user_email = view.findViewById(R.id.user_email);
        user_ph=view.findViewById( R.id.user_ph );
        ImageView about=view.findViewById( R.id.about );
        ImageView edit = view.findViewById( R.id.edit_profile );
        desc_text=view.findViewById( R.id.descrip_text );
        pb_userimg = view.findViewById(R.id.pb_userimg);
        pb_userimg.setVisibility( View.GONE );
        final RelativeLayout layout_signout = (RelativeLayout)view.findViewById(R.id.layout_signout);

        about.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Intent intent = new Intent(getActivity(), about_us.class);
                Intent intent = new Intent(getActivity(), Contact_Us.class);
                startActivity(intent);

             //   startActivity(intent);
            }
        } );
        edit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), edit_prof_emp.class);

                startActivity(intent);
            }
        } );
        layout_signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder( requireActivity() );
                builder.setTitle(R.string.app_name);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setMessage("Do you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mFirebaseAuth.getInstance().signOut();
                                //saving session

                                Intent intent = new Intent(getActivity(), Login_Employe.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


            }
        } );

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild("eid").equalTo( FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Employe user = dataSnapshot1.getValue(Employe.class);

                    name_user.setText(user.name);
                    user_email.setText(user.email);
                    user_ph.setText( "+91"+user.contactn );
                    desc_text.setText(user.descrip);
                    if (user.profileimg!=null){

                        Picasso.get().load(user.profileimg).into(imageuser);

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


        return view;
    }
}
