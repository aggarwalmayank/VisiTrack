package com.appsaga.visitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddHost extends AppCompatActivity {

    ImageView back;
    TextView add;
    EditText name, phone, email;
    DatabaseReference mref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_host);
        init();


        //called on back pressed
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //called when back button is pressed
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check base conditions
                if (name.getText().toString().equalsIgnoreCase(""))
                    name.setError("Invalid Name");
                else if (email.getText().toString().equalsIgnoreCase("") || !email.getText().toString().contains("@"))
                    email.setError("Invalid Email");
                else if (phone.getText().toString().equalsIgnoreCase("") || phone.getText().toString().length() != 10)
                    phone.setError("Invalid Number");
                else {

                    //add host to database
                    HashMap<String, Object> insert = new HashMap<>();
                    insert.put("Name", name.getText().toString());
                    insert.put("Email", email.getText().toString());
                    insert.put("Phone", phone.getText().toString());
                    mref.child("host").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            mref.child("host").push().setValue(insert);
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    public void init() {
        name = findViewById(R.id.fullname);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
    }
}
