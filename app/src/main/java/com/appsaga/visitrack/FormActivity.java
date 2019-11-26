package com.appsaga.visitrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.ref.PhantomReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cdflynn.android.library.checkview.CheckView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public class FormActivity extends AppCompatActivity {

    LinearLayout slayout;
    String message1, message2, phoneno;
    private LinearLayout shost;
    private TextView proceed, next;
    static TextView selectHost;
    private EditText name, phone, email, visid;
    DatabaseReference mref;
    static Host h = null;
    Visitor current;
    boolean present = true, permissiongranted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        askSmsPermission();
        initialization();

        //open activity to select host
        shost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FormActivity.this, SelectHost.class));
            }
        });


        //called when user press checkin button
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //checks base conditions if name email phone are valid or not
                if (name.getText().toString().equalsIgnoreCase(""))
                    name.setError("Invalid Name");
                else if (email.getText().toString().equalsIgnoreCase("") || !email.getText().toString().contains("@"))
                    email.setError("Invalid Email");
                else if (phone.getText().toString().equalsIgnoreCase("") || phone.getText().toString().length() != 10)
                    phone.setError("Invalid Number");
                else if (h == null) {
                    Toast.makeText(FormActivity.this, "Select Host", Toast.LENGTH_SHORT).show();
                } else {

                    // if all the details are valid it get added to the database
                    addtodb();
                    Toast.makeText(FormActivity.this, "Check-In Successfully", Toast.LENGTH_SHORT).show();
                    //and all input field are resetted to null
                    name.setText("");
                    phone.setText("");
                    email.setText("");
                    selectHost.setText("Select Host");
                    h = null;

                }
            }
        });


        //on pressing checkout button it get called
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mref.child("Visitor").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Visitor> visitor = new ArrayList<>();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Visitor visit = new Visitor(ds.getValue(Visitor.class), ds.getKey());
                            visitor.add(visit);
                        }

                        //check if visitor token is valid or not
                        for (Visitor v : visitor) {
                            if (v.getKey().equals(visid.getText().toString())) {
                                present = false;
                                current = v;
                                break;
                            }
                        }
                        if (visid.getText().toString().equalsIgnoreCase("") || present)
                            visid.setError("Invalid Token");
                        else {
                            //if token is valid its checkout time get updated to database
                            final Visitor finalCurrent = current;
                            mref.child("Visitor").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    DateFormat df = new SimpleDateFormat("ddMMyyHHmmssZ", java.util.Locale.getDefault());
                                    Date today = Calendar.getInstance().getTime();
                                    String date = df.format(today);
                                    mref.child("Visitor").child(visid.getText().toString()).child("CheckOut").setValue(date.substring(6, 8) + ":" + date.substring(8, 10));


                                    message2 = "Name: " + finalCurrent.getName() + "\n" +
                                            "Email: " + finalCurrent.getEmail() + "\n" +
                                            "Check-in Time: " + finalCurrent.getCheckIn() + " IST\n" +
                                            "Check-out Time: " + date.substring(6, 8) + ":" + date.substring(8, 10) + " IST\n" +
                                            "Host Name: " + finalCurrent.getHostName() + "\n" +
                                            "Address Visited: " + "Innovaccer Office";


                                    SendMail s = new SendMail(FormActivity.this, finalCurrent.getEmail(), "Visit Details", message2);
                                    s.execute();

                                    // phoneno = finalCurrent.getPhone();


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            Handler h = new Handler();
                            h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(FormActivity.this, "Check-Out Successfully", Toast.LENGTH_SHORT).show();
                                    visid.setText("");
                                    visid.setError(null);
                                }
                            }, 1000);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


        // used to hide keyboard when tapped on screen
        slayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputMethodManager = (InputMethodManager)
                        view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
  }

  // initialize all views like text ,layouts etc
    public void initialization() {
       slayout = findViewById(R.id.slayout);
        visid = findViewById(R.id.visid);
        next = findViewById(R.id.next);
        shost = findViewById(R.id.get);
        proceed = findViewById(R.id.proceed);
        selectHost = findViewById(R.id.selecthost);
        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        mref = FirebaseDatabase.getInstance().getReference();
    }

    //this method add visitor data to firebase Realtime database
    public void addtodb() {
        DateFormat df = new SimpleDateFormat("ddMMyyHHmmssZ", java.util.Locale.getDefault());
        Date today = Calendar.getInstance().getTime();
        String date = df.format(today);
        final HashMap<String, Object> insert = new HashMap<>();
        insert.put("Name", name.getText().toString());
        insert.put("Email", email.getText().toString());
        insert.put("HostName", h.getName());
        insert.put("Phone", phone.getText().toString());
        insert.put("HostEmail", h.getEmail());
        insert.put("HostPhone", h.getPhone());
        insert.put("CheckOut", "");
        insert.put("timestamp", date.substring(0, 12));
        insert.put("CheckIn", date.substring(6, 8) + ":" + date.substring(8, 10));
        final String visitortoken = date.substring(0, 12);

        mref.child("Visitor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mref.child("Visitor").child(visitortoken).setValue(insert);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        message1 = "Visitor Name: " + name.getText().toString() + "\n" +
                "Visitor Email: " + email.getText().toString() + "\n" +
                "visitor Phone: " + phone.getText().toString();
        phoneno = h.getPhone();
        sendSMS(phoneno,message1);
        SendMail s2 = new SendMail(FormActivity.this, email.getText().toString(),"visit token ","your visit token is:  " +visitortoken );
        SendMail s = new SendMail(FormActivity.this, h.getEmail(), "visitor details", message1);
        s.execute();
        s2.execute();
    }



    //this method is for asking permission to send sms
    public void askSmsPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        permissiongranted = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    //this method is used to send sms and use smsManager class
    public void sendSMS(String no, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(no, null, message, null, null);
    }
}

