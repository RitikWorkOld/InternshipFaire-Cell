package com.ecell.internshipfairendeavour.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
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

import com.ecell.internshipfairendeavour.Notifications.Customised.BucketRecyclerView;

import com.ecell.internshipfairendeavour.R;
import com.ecell.internshipfairendeavour.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
public class Student_Fragment extends Fragment {
    private static final int REQUEST_CODE = 1000;
    BucketRecyclerView rv_internall;
    Button send_email,send_notif;
    DatabaseReference drinternall;
    TextView total1;
    CheckBox all;
    ImageView downloadxls;
    FirebaseRecyclerOptions<User_admin> optionsinternall;
    private View no_app;
    String uid,clg,adrs;
    ArrayList<String> selectedStudent_main,selectedStudent_noti,all_main,all_noti,all_names,all_numbers,selected_numbers,selected_names;
    FirebaseRecyclerAdapter<User_admin, Student_vh> adapterinternall;

    Row[] rownames;

    public Student_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate( R.layout.fragment_student_, container, false );
        rv_internall = view.findViewById(R.id.rv_internall);
        no_app=view.findViewById( R.id.no_new_notifications );
        rv_internall.showIfEmpty( no_app );
        selectedStudent_main = new ArrayList<>();
        all_main = new ArrayList<>();
        all_names = new ArrayList<>();
        all_numbers = new ArrayList<>();
        selectedStudent_noti = new ArrayList<>();

        selected_numbers = new ArrayList<>();
        selected_names = new ArrayList<>();

        all_noti = new ArrayList<>();
        send_email=view.findViewById( R.id.send_email );
        all=view.findViewById( R.id.all );
        send_notif=view.findViewById( R.id.send_noti );
        total1=view.findViewById( R.id.total );
        rv_internall.setHasFixedSize(true);

        downloadxls = view.findViewById(R.id.downloadxls);

        rv_internall.setLayoutManager(new LinearLayoutManager(getContext()));
        drinternall = FirebaseDatabase.getInstance().getReference().child("Users");
        drinternall.keepSynced(true);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    User user = dataSnapshot1.getValue(User.class);

                    all_main.add(user.getEmail());
                    all_numbers.add(user.getContactn());
                    all_names.add(user.getName());
                    all_noti.add(user.getUid());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        optionsinternall = new FirebaseRecyclerOptions.Builder<User_admin>().setQuery(drinternall,User_admin.class).build();

        adapterinternall = new FirebaseRecyclerAdapter<User_admin, Student_vh>(optionsinternall) {
            @Override
            protected void onBindViewHolder(@NonNull final Student_vh holder, int position, @NonNull final User_admin model) {
                holder.cmpname.setText(model.getName());
                holder.cmpsubname.setText(model.getContactn());
                holder.checkBox.setChecked(model.isSelected());

                holder.checkBox.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(holder.checkBox.isChecked()) {
                            model.setSelected(true);
                            selectedStudent_main.add(model.getEmail());
                            selectedStudent_noti.add(model.getUid());
                            selected_names.add(model.getName());
                            selected_numbers.add(model.getContactn());


                        }
                        else if (!holder.checkBox.isChecked()) {
                            model.setSelected(false);
                            selectedStudent_main.remove(model.getEmail());
                            selectedStudent_noti.remove(model.getUid());
                            selected_names.remove(model.getName());
                            selected_numbers.remove(model.getContactn());

                        }
                    }
                } );
                send_notif.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(selectedStudent_noti.isEmpty()){

                            Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                        }
                        else {
                       //     Intent intent = new Intent( getActivity(), MainActivity.class );
                        //    intent.putStringArrayListExtra( "uid_noti", selectedStudent_noti );
                  //          startActivity( intent );
                        }
                    }
                } );
                send_email.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(selectedStudent_main.isEmpty()){

                            Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                            intent.putStringArrayListExtra( "emails", selectedStudent_main );

                            startActivity( intent );

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

                holder.profile.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), Student_Fragment_Detail.class);
                        intent.putExtra( "userid",model.getUid() );
                        startActivity( intent );
                    }
                } );
            }

            @NonNull
            @Override
            public Student_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Student_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.student_vh, parent,false));
            }
        };
        rv_internall.setAdapter(adapterinternall);
        adapterinternall.startListening();


        all.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!all.isChecked()){
                    optionsinternall = new FirebaseRecyclerOptions.Builder<User_admin>().setQuery(drinternall,User_admin.class).build();

                    adapterinternall = new FirebaseRecyclerAdapter<User_admin, Student_vh>(optionsinternall) {
                        @Override
                        protected void onBindViewHolder(@NonNull final Student_vh holder, int position, @NonNull final User_admin model) {
                            holder.cmpname.setText(model.getName());
                            holder.cmpsubname.setText(model.getContactn());
                            holder.checkBox.setChecked(false);

                            selectedStudent_main.clear();
                            selectedStudent_noti.clear();
                            selected_names.clear();
                            selected_numbers.clear();

                            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(holder.checkBox.isChecked()) {
                                        model.setSelected(true);
                                        selectedStudent_main.add(model.getEmail());
                                        selectedStudent_noti.add(model.getUid());
                                        selected_names.add(model.getName());
                                        selected_numbers.add(model.getContactn());
                                    }
                                    else if (!holder.checkBox.isChecked()) {
                                        model.setSelected(false);
                                        selectedStudent_main.remove(model.getEmail());
                                        selectedStudent_noti.remove(model.getUid());
                                        selected_names.remove(model.getName());
                                        selected_numbers.remove(model.getContactn());

                                    }
                                }
                            } );
                            send_notif.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(selectedStudent_noti.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                 //       Intent intent = new Intent( getActivity(), MainActivity.class );
                                   //     intent.putStringArrayListExtra( "uid_noti", selectedStudent_noti );
                                  //      startActivity( intent );
                                    }
                                }
                            } );
                            send_email.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(selectedStudent_main.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                                        intent.putStringArrayListExtra( "emails", selectedStudent_main );

                                        startActivity( intent );

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

                            holder.profile.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), Student_Fragment_Detail.class);
                                    intent.putExtra( "userid",model.getUid() );
                                    startActivity( intent );
                                }
                            } );
                        }

                        @NonNull
                        @Override
                        public Student_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new Student_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.student_vh, parent,false));
                        }
                    };
                    rv_internall.setAdapter(adapterinternall);
                    adapterinternall.startListening();
                }
                else {
                    selectedStudent_main.clear();
                    selectedStudent_main.addAll(all_main);
                    selectedStudent_noti.clear();
                    selectedStudent_noti.addAll(all_noti);
                    selected_names.clear();
                    selected_names.addAll(all_names);
                    selected_numbers.clear();
                    selected_numbers.addAll(all_numbers);

                    optionsinternall = new FirebaseRecyclerOptions.Builder<User_admin>().setQuery(drinternall,User_admin.class).build();

                    adapterinternall = new FirebaseRecyclerAdapter<User_admin, Student_vh>(optionsinternall) {
                        @Override
                        protected void onBindViewHolder(@NonNull final Student_vh holder, int position, @NonNull final User_admin model) {
                            holder.cmpname.setText(model.getName());
                            holder.cmpsubname.setText(model.getContactn());
                            holder.checkBox.setChecked(true);

                            holder.checkBox.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(holder.checkBox.isChecked()) {
                                        model.setSelected(true);
                                        selectedStudent_main.add(model.getEmail());
                                        selectedStudent_noti.add(model.getUid());
                                        selected_names.add(model.getName());
                                        selected_numbers.add(model.getContactn());

                                    }
                                    else if (!holder.checkBox.isChecked()) {
                                        model.setSelected(false);
                                        selectedStudent_main.remove(model.getEmail());
                                        selectedStudent_noti.remove(model.getUid());
                                        selected_names.remove(model.getName());
                                        selected_numbers.remove(model.getContactn());

                                    }
                                }
                            } );
                            send_notif.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(selectedStudent_noti.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                //        Intent intent = new Intent( getActivity(), MainActivity.class );
                               //         intent.putStringArrayListExtra( "uid_noti", selectedStudent_noti );
                                 //       startActivity( intent );
                                    }
                                }
                            } );
                            send_email.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(selectedStudent_main.isEmpty()){

                                        Toast.makeText(getContext(), "You have not selected any student", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Intent intent = new Intent( getActivity(), Email_sendpopupAdmin.class );

                                        intent.putStringArrayListExtra( "emails", selectedStudent_main );

                                        startActivity( intent );

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

                            holder.profile.setOnClickListener( new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(),Student_Fragment_Detail.class);
                                    intent.putExtra( "userid",model.getUid() );
                                    startActivity( intent );
                                }
                            } );
                        }

                        @NonNull
                        @Override
                        public Student_vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            return new Student_vh(LayoutInflater.from(view.getContext()).inflate(R.layout.student_vh, parent,false));
                        }
                    };
                    rv_internall.setAdapter(adapterinternall);
                    adapterinternall.startListening();
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
            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
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

            for (int i=0;i<selectedStudent_main.size();i++){
                cell = rownames[i].createCell(2);
                cell.setCellValue(selectedStudent_main.get(i));
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


            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(),"Data.xls");
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
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
