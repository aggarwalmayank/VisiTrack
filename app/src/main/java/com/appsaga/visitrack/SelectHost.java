package com.appsaga.visitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class SelectHost extends AppCompatActivity {

    ListView hostlist;
    AVLoadingIndicatorView av;
    ImageView back,add;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_host);
         initialization();

        // show loader as data is reterived from database
        av.show();

        // open activity to add new host
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectHost.this,AddHost.class));
            }
        });


        //reterive host from database and set array adapter/list that we see
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ArrayList<Host> host= new ArrayList<>();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Host host1= new Host(ds.getValue(Host.class));
                    host.add(host1);
                }
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        av.hide();
                        HostAdapter hadapter= new HostAdapter(SelectHost.this, host);
                        hostlist.setAdapter(hadapter);
                    }
                }, 1500);

                //called when a particular host is clicked
                hostlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Host hosts= host.get(position);
                        FormActivity.h=hosts;
                       FormActivity.selectHost.setText(hosts.getName());
                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //called when back button is pressed present of top left of the screen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormActivity.h=null;
                finish();
            }
        });

    }

    public void initialization(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("host");
        back=findViewById(R.id.back);
        add=findViewById(R.id.add);
        hostlist=findViewById(R.id.host_list);
        av=findViewById(R.id.loader);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FormActivity.h=null;
    }
}
