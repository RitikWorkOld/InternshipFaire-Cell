package com.ecell.internshipfairendeavour.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.ecell.internshipfairendeavour.Employe.Employe_profile;
import com.ecell.internshipfairendeavour.Employe.Employe_vh;
import com.ecell.internshipfairendeavour.Employe.Model.Employe;
import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;
import com.ecell.internshipfairendeavour.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Employe_Fragment extends Fragment {
    private static final int REQUEST_CODE = 1000;
    BucketRecyclerView rv_internall1;
    DatabaseReference drinternall1;
    TextView total1;
    private View no_app;
    CheckBox all;
    ImageView downloadxls;
    DatabaseReference drinternall;
    FirebaseRecyclerOptions<Employe> optionsinternall2;
    FirebaseRecyclerAdapter<Employe, Employe_vh> adapterinternall2;
    FirebaseRecyclerOptions<Employe> optionsinternall1;
    Button send_notif,send_email;
    Row[] rownames;
    ArrayList<String> selectedEmploye_main,selectedEmploye_noti,all_main,all_noti,all_names,selected_names,all_numbers,selected_numbers;
    FirebaseRecyclerAdapter<Employe, Employe_vh> adapterinternall1;
    public Employe_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate( R.layout.fragment_employe_, container, false );
        rv_internall1 = view.findViewById( R.id.rv_internall1);
        no_app=view.findViewById( R.id.no_new_notifications );
        rv_internall1.showIfEmpty( no_app );
        downloadxls = view.findViewById(R.id.downloadxls);
        send_email=view.findViewById( R.id.send_email );
        send_notif=view.findViewById( R.id.send_noti );
        selectedEmploye_main = new ArrayList<>();
        all_names = new ArrayList<>();
        all_numbers = new ArrayList<>();
        selected_names = new ArrayList<>();
        selected_numbers = new ArrayList<>();
        selectedEmploye_noti = new ArrayList<>();
        total1=view.findViewById( R.id.total );
        all=view.findViewById( R.id.all );
        all_noti = new ArrayList<>();
        all_main = new ArrayList<>();
        drinternall = FirebaseDatabase.getInstance().getReference().child("Employe");
        drinternall.keepSynced(true);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Employe employe = dataSnapshot1.getValue(Employe.class);

                    all_main.add(employe.getEmail());
                    all_noti.add(employe.getEid());
                    all_numbers.add(employe.getContactn());
                    all_names.add(employe.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        rv_internall1.setHasFixedSize(true);

        rv_internall1.setLayoutManager(new LinearLayoutManager(getContext()));
        drinternall1 = FirebaseDatabase.getInstance().getReference().child("Employe");
        Query query = drinternall1.orderByChild("officialstatus" ).equalTo("yes");
        drinternall1.keepSynced(true);

        optionsinternall1 = new FirebaseRecyclerOptions.Builder<Employe>().setQuery(query,Employe.class).build();

        adapterinternall1 = new FirebaseRecyclerAdapter<Employe, Employe_vh>(optionsinternall1) {
            @Override
            protected void onBindViewHolder(@NonNull final Employe_vh holder, int position, @NonNull final Employe model) {
                holder.cmpname.setText(model.getName());
                holder.cmpsubname.setText(model.getContactn());
                holder.checkBox.setChecked(model.isSelected());

                holder.checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.checkBox.isChecked()) {
                            model.setSelected(true);
                            selectedEmploye_main.add(model.getEmail());
                            selectedEmploye_noti.add(model.getEid());
                            selected_names.add(model.getName());

                            selected_numbers.add(model.getContactn());

                        }
                        else if (!holder.checkBox.isChecked()) {
                            model.setSelected(false);
                            selectedEmploye_main.remove(model.getEmail());
                            selectedEmploye_noti.remove(model.getEid());
                            selected_names.remove(model.getName());
                            selected_numbers.remove(model.getContactn());

                        }
                        downloadxls.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rownames = new Row[all_names.size()+1];
                                createXls();
                            }
                        });
                        send_notif.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if(selectedEmploye_noti.isEmpty()){

                                    Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Log.d("PARNEET------------"," noti id == "+selectedEmploye_noti);
                            //        Intent intent = new Intent( getActivity(), MainActivity.class );
                             //       intent.putStringArrayListExtra( "uid_noti", selectedEmploye_noti );
                             //       startActivity( intent );
                                }
                            }
                        } );
                        send_email.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(selectedEmploye_main.isEmpty()){

                                    Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                                    intent.putStringArrayListExtra( "emails", selectedEmploye_main );

                                    startActivity( intent );

                                }
                            }
                        } );
                    }
                } );
                holder.profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Employe_profile.class);
intent.putExtra( "userid",model.getEid() );
                        startActivity( intent );
                    }
                } );
            }

            @NonNull
            @Override
            public Employe_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Employe_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.employe_vh, parent,false));
            }
        };
        rv_internall1.setAdapter(adapterinternall1);
        adapterinternall1.startListening();

        all.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!all.isChecked()){
                    Query query = drinternall1.orderByChild("officialstatus" ).equalTo("yes");
                    drinternall1.keepSynced(true);
                    optionsinternall1 = new FirebaseRecyclerOptions.Builder<Employe>().setQuery(query,Employe.class).build();

                    adapterinternall1 = new FirebaseRecyclerAdapter<Employe, Employe_vh>(optionsinternall1) {
                        @Override
                        protected void onBindViewHolder(@NonNull final Employe_vh holder, int position, @NonNull final Employe model) {
                            holder.cmpname.setText(model.getName());
                            holder.cmpsubname.setText(model.getContactn());
                            holder.checkBox.setChecked(false);

                            selectedEmploye_main.clear();
                            selectedEmploye_noti.clear();

                            selected_names.clear();
                            selected_numbers.clear();
                            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(holder.checkBox.isChecked()) {
                                        model.setSelected(true);
                                        selectedEmploye_main.add(model.getEmail());
                                        selectedEmploye_noti.add(model.getEid());
                                        selected_names.add(model.getName());
                                        selected_numbers.add(model.getContactn());

                                    }
                                    else if (!holder.checkBox.isChecked()) {
                                        model.setSelected(false);
                                        selectedEmploye_main.remove(model.getEmail());
                                        selectedEmploye_noti.remove(model.getEid());
                                        selected_names.remove(model.getName());
                                        selected_numbers.remove(model.getContactn());

                                    }
                                }
                            } );
                            downloadxls.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rownames = new Row[all_names.size()+1];
                                    createXls();
                                }
                            });
                            send_notif.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(selectedEmploye_noti.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d("PARNEET------------"," noti id == "+selectedEmploye_noti);
                                  //      Intent intent = new Intent( getActivity(), MainActivity.class );
                               //         intent.putStringArrayListExtra( "uid_noti", selectedEmploye_noti );
                                 //       startActivity( intent );
                                    }
                                }
                            } );
                            send_email.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(selectedEmploye_main.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                                        intent.putStringArrayListExtra( "emails", selectedEmploye_main );

                                        startActivity( intent );

                                    }
                                }
                            } );
                            holder.profile.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), Student_Fragment_Detail.class);
                                    intent.putExtra( "userid",model.getEid() );
                                    startActivity( intent );
                                }
                            } );
                        }

                        @NonNull
                        @Override
                        public Employe_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new Employe_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.employe_vh, parent,false));
                        }
                    };
                    rv_internall1.setAdapter(adapterinternall1);
                    adapterinternall1.startListening();
                }
                else {
                    selectedEmploye_main.clear();
                    selectedEmploye_main.addAll(all_main);
                    selectedEmploye_noti.clear();
                    selectedEmploye_noti.addAll(all_noti);
                    selected_names.clear();
                    selected_names.addAll(all_names);
                    selected_numbers.clear();
                    selected_numbers.addAll(all_numbers);
                    Query query = drinternall1.orderByChild("officialstatus" ).equalTo("yes");
                    drinternall1.keepSynced(true);
                    optionsinternall1 = new FirebaseRecyclerOptions.Builder<Employe>().setQuery(query,Employe.class).build();

                    adapterinternall1 = new FirebaseRecyclerAdapter<Employe, Employe_vh>(optionsinternall1) {
                        @Override
                        protected void onBindViewHolder(@NonNull final Employe_vh holder, int position, @NonNull final Employe model) {
                            holder.cmpname.setText(model.getName());
                            holder.cmpsubname.setText(model.getContactn());
                            holder.checkBox.setChecked(true);

                            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(holder.checkBox.isChecked()) {
                                        model.setSelected(true);
                                        selectedEmploye_main.add(model.getEmail());
                                        selectedEmploye_noti.add(model.getEid());
                                        selected_names.add(model.getName());
                                        selected_numbers.add(model.getContactn());

                                    }
                                    else if (!holder.checkBox.isChecked()) {
                                        model.setSelected(false);
                                        selectedEmploye_main.remove(model.getEmail());
                                        selectedEmploye_noti.remove(model.getEid());
                                        selected_names.remove(model.getName());
                                        selected_numbers.remove(model.getContactn());
                                    }
                                }
                            } );
                            downloadxls.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rownames = new Row[all_names.size()+1];
                                    createXls();
                                }
                            });
                            send_notif.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(selectedEmploye_noti.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d("PARNEET------------"," noti id == "+selectedEmploye_noti);
                                    //    Intent intent = new Intent( getActivity(), MainActivity.class );
                                    //    intent.putStringArrayListExtra( "uid_noti", selectedEmploye_noti );
                                   //     startActivity( intent );
                                    }
                                }
                            } );
                            send_email.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(selectedEmploye_main.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                                        intent.putStringArrayListExtra( "emails", selectedEmploye_main );

                                        startActivity( intent );

                                    }
                                }
                            } );
                            holder.profile.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), Student_Fragment_Detail.class);
                                    intent.putExtra( "userid",model.getEid() );
                                    startActivity( intent );
                                }
                            } );
                        }

                        @NonNull
                        @Override
                        public Employe_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new Employe_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.employe_vh, parent,false));
                        }
                    };
                    rv_internall1.setAdapter(adapterinternall1);
                    adapterinternall1.startListening();
                }
            }
        } );


        return view;
    }
    private void createXls() {

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
        }
        else {
            Workbook wb=new HSSFWorkbook();
            Cell cell=null;
            CellStyle cellStyle=wb.createCellStyle();
            cellStyle.setFillForegroundColor( HSSFColor.HSSFColorPredefined.BLUE.getIndex());
            Sheet sheet=null;
            sheet = wb.createSheet("Name of sheet");
            Row row =sheet.createRow(0);

            for (int i=0;i<selected_names.size();i++){
                rownames[i] = sheet.createRow(i+1);
            }

            cell=row.createCell(0);
            cell.setCellValue("Name");
            cell.setCellStyle(cellStyle);

            cell=row.createCell(1);
            cell.setCellValue("Mobile Number");
            cell.setCellStyle(cellStyle);

            cell=row.createCell(2);
            cell.setCellValue("Email");
            cell.setCellStyle(cellStyle);

            for (int i=0;i<selected_names.size();i++){
                cell = rownames[i].createCell(0);
                cell.setCellValue(selected_names.get(i));
                cell.setCellStyle(cellStyle);
            }

            for (int i=0;i<selectedEmploye_main.size();i++){
                cell = rownames[i].createCell(2);
                cell.setCellValue(selectedEmploye_main.get(i));
                cell.setCellStyle(cellStyle);
            }

            for (int i=0;i<selected_numbers.size();i++){
                cell = rownames[i].createCell(1);
                cell.setCellValue(selected_numbers.get(i));
                cell.setCellStyle(cellStyle);
            }

            sheet.setColumnWidth(0,(10*500));
            sheet.setColumnWidth(1,(10*500));
            sheet.setColumnWidth(2,(10*700));

            File file = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),"EmployeData.xls");
            FileOutputStream outputStream = null;

            try {

                if(selected_names.isEmpty()){

                    Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                }
                if(!selected_names.isEmpty()){
                    outputStream=new FileOutputStream(file);
                    wb.write(outputStream);
                    Toast.makeText(getActivity().getApplicationContext(),"Saved in Downloads",Toast.LENGTH_LONG).show();
                }


            } catch (IOException e) {
                e.printStackTrace();

                Toast.makeText(getActivity().getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                try {
                    if (outputStream!=null)
                        outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Employe");
        databaseReference.keepSynced(true);
        databaseReference.orderByChild( "officialstatus" ).equalTo( "yes" ).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Long sum = dataSnapshot.getChildrenCount();
                String total = String.valueOf(sum);
                total1.setText(total);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
