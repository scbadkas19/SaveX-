package com.example.aosproject1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class expense extends AppCompatActivity {

    final  FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase mydb;
    DatabaseReference mydbref;



    private static String d;
    String t;
    String catexp;



    EditText expense;
    EditText place;
    EditText dat;
    EditText tim;


    private static ArrayAdapter<String> adapter;
    private static String date;

    Spinner spin1;
    Intent i;

    Button b;

    Float n1;
    String n2;
    String n3;
    String n4;
    String n5;

    Member member;

     String  userid;


    long maxid=0;


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        userid=user.getUid();
        b = (Button)findViewById(R.id.Add);
        expense=(EditText)findViewById(R.id.expense);
        place=(EditText)findViewById(R.id.place);
        d=nav_act.getDate();
        dat= (EditText)findViewById(R.id.dat);
        dat.setText(d);
        t=nav_act.getTime();
        tim= (EditText)findViewById(R.id.tim);
        tim.setText(t);


        spin1 = (Spinner) findViewById(R.id.spinnerexpense);
        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                {
                    Toast.makeText(expense.this,"Please select a category of expense",Toast.LENGTH_SHORT).show();
                }
                else
                {
                     catexp = String.valueOf(spin1.getSelectedItem());
                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        member=new Member();
        mydb=FirebaseDatabase.getInstance();
        mydbref=mydb.getReference().child("Users").child(userid).child("Entries").child(d);
        mydbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 n1 = Float.parseFloat(expense.getText().toString().trim());
                 n2 = place.getText().toString().trim();
                 n3 = dat.getText().toString();
                 n4 = tim.getText().toString();
                 n5 = catexp;
                 member.setExpense(n1);
                 member.setDescription(n2);
                 member.setDate(n3);
                 member.setTime(n4);
                 member.setCategory(n5);
                 mydbref.child(String.valueOf(maxid+1)).setValue(member);
                 //mydbref.push().setValue(member);
                Toast.makeText(expense.this,"Data successfully added to Database",Toast.LENGTH_SHORT).show();

            }
        });


    }




}
