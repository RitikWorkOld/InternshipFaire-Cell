package com.ecell.internshipfairendeavour.Notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Notifications extends AppCompatActivity {

    private BucketRecyclerView recyclerView;
    private FirebaseRecyclerOptions<Noti_Helper> options;
    private FirebaseRecyclerAdapter<Noti_Helper,Notification_ViewHolder> adapter;
    private DatabaseReference databaseReference;
    private View no_new_notifications;


    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView( R.layout.activity_notifications);

        no_new_notifications = findViewById(R.id.no_new_notifications);


        recyclerView = findViewById(R.id.noti_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.showIfEmpty(no_new_notifications);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("notification")
        .child( FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Noti_Helper>().setQuery(databaseReference,Noti_Helper.class).build();

        adapter = new FirebaseRecyclerAdapter<Noti_Helper, Notification_ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final Notification_ViewHolder notification_viewHolder, int i, @NonNull final Noti_Helper noti_helper) {

                notification_viewHolder.Title.setText(noti_helper.getTitle());
                notification_viewHolder.Description.setText(noti_helper.getBody());
                notification_viewHolder.Date.setText(noti_helper.getDate());     //added

            }
            @NonNull
            @Override
            public Notification_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Notification_ViewHolder( LayoutInflater.from(Notifications.this).inflate(R.layout.notification_card,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }

  /*  @Override
    public void onBackPressed() {
        Intent intent = new Intent(Notifications.this, Dashboard.class);
        startActivity(intent);
        finish();
    }*/
}
