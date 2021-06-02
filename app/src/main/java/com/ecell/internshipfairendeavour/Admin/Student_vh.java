package com.ecell.internshipfairendeavour.Admin;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecell.internshipfairendeavour.R;


public class Student_vh extends RecyclerView.ViewHolder {

    public   ImageView cmpimg;
    public   TextView cmpname;
    public  TextView cmpsubname;
    public RadioButton radioButton1;
    public Button profile;
    public Button email;
    public ImageView tick;
    // public RadioButton radioButton,radioButton2,radioButton3;
    public CheckBox checkBox;


    public  RelativeLayout layout_card;

    public Student_vh(@NonNull View itemView) {
        super(itemView);
tick=itemView.findViewById( R.id.tick );
        cmpimg = itemView.findViewById( R.id.icd_cmpimg);
        cmpname = itemView.findViewById(R.id.icd_cmpname);
        layout_card=itemView.findViewById( R.id.layout_card );
        cmpsubname = itemView.findViewById(R.id.icd_cmpsubname);
        profile=itemView.findViewById( R.id.profile_btn );
        //   email=itemView.findViewById( R.id.email_send );
        checkBox=itemView.findViewById( R.id.checker );


    }
}