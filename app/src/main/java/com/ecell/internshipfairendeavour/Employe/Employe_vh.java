package com.ecell.internshipfairendeavour.Employe;

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


public class Employe_vh extends RecyclerView.ViewHolder {

    public   ImageView cmpimg;
    public   TextView cmpname;
    public  TextView cmpsubname;
    public RadioButton radioButton1;
    public Button profile;
    public Button email;
    // public RadioButton radioButton,radioButton2,radioButton3;
    public CheckBox checkBox;


    public  RelativeLayout layout_card;

    public Employe_vh(@NonNull View itemView) {
        super(itemView);

        cmpimg = itemView.findViewById( R.id.icd_cmpimg);
        cmpname = itemView.findViewById(R.id.icd_cmpname);
        layout_card=itemView.findViewById( R.id.layout_card );
        cmpsubname = itemView.findViewById(R.id.icd_cmpsubname);
        profile=itemView.findViewById( R.id.profile_btn );
        //   email=itemView.findViewById( R.id.email_send );
        checkBox=itemView.findViewById( R.id.checker );
       /* radioButton1=itemView.findViewById( R.id.option_1 );
        radioButton=itemView.findViewById( R.id.option_2 );
        radioButton2=itemView.findViewById( R.id.option_3 );
        radioButton3=itemView.findViewById( R.id.option_4 );
        /*final RadioGroup radioGroup1 = (RadioGroup) itemView.findViewById(R.id.group_detail);
        final RadioGroup radioGroup2 = (RadioGroup) itemView.findViewById(R.id.group_detail1);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != -1)
                    radioGroup2.check(i);

            }
        });


        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != -1)
                    radioGroup1.check(i);
            }
        });*/

    }
}