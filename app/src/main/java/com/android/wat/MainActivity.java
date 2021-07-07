package com.android.wat;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.wat.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference downRef;
    DatabaseReference upRef;

    long upValue, downValue ;

    TextView up_a1_txt,down_a1_txt;
    Button button_first;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        up_a1_txt =  findViewById(R.id.up_a1_txt);
        down_a1_txt = findViewById(R.id.down_a1_txt);
        button_first = findViewById(R.id.button_first);

        database = FirebaseDatabase.getInstance();
        upRef = database.getReference("mtr").child("Up").child("a_1");
        downRef = database.getReference("mtr").child("Down").child("a_1");



    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        readUpDB();
        readDownDB();

    }
    private void readUpDB() {
        // Read from the database
        upRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                upValue = dataSnapshot.getValue(long.class);
                up_a1_txt.setText("UP value to a1 is: "+upValue);
                Log.d("TAG", "Value is: " + upValue);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    private void readDownDB() {
        // Read from the database
        downRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                downValue = dataSnapshot.getValue(long.class);
                down_a1_txt.setText("Down value to a1 is: "+downValue);

                if (downValue == 1){
                    button_first.setText("OFF");
                }else{
                    button_first.setText("ON");
                }

                Log.d("TAG", "Value is: " + downValue);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
    private void swichOFF() {
        upRef.setValue(0);
        init();
    }
    private void swichON() {
        // Write a message to the database
        upRef.setValue(1);
        init();
    }

    public void clickOnbutton(View view) {

        if (downValue == 1){
            swichOFF();
        }else{
            swichON();
        }
    }
}