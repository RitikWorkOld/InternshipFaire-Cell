package com.ecell.internshipfairendeavour.Employe.Model;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecell.internshipfairendeavour.R;


public class event_profile_vh extends RecyclerView.ViewHolder {

    public   ImageView cmpimg;
    public   TextView cmpname;
    public  TextView cmpsubname;
public TextView paidstatus;
    public Button email;

    public CheckBox checkBox;


    public  RelativeLayout layout_card;

    public event_profile_vh(@NonNull View itemView) {
        super(itemView);
paidstatus=itemView.findViewById( R.id.paid_status );
        cmpimg = itemView.findViewById( R.id.icd_cmpimg);
        cmpname = itemView.findViewById(R.id.icd_cmpname);
        layout_card=itemView.findViewById( R.id.layout_card );
        cmpsubname = itemView.findViewById(R.id.icd_cmpsubname);

        //   email=itemView.findViewById( R.id.email_send );
        checkBox=itemView.findViewById( R.id.checker );


    }
}