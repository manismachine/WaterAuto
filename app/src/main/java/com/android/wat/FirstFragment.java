package com.android.wat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.wat.databinding.FragmentFirstBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    FirebaseDatabase database;
    DatabaseReference downRef;
    DatabaseReference upRef;

    String upValue, downValue ;

    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState
    ) {


        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = FirebaseDatabase.getInstance();
        upRef = database.getReference("mtr").child("Up").child("a_1");
        downRef = database.getReference("mtr").child("Down").child("a_1");

       init();

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (downValue.equalsIgnoreCase("1")){
                    swichOFF();
                }else{
                    swichON();
                }
            }
        });
    }

    private void init() {
        readUpDB();
        readDownDB();

        if (downValue.equalsIgnoreCase("1")){
            binding.buttonFirst.setText("stop");
        }else{
            binding.buttonFirst.setText("start");
        }
    }
    private void readUpDB() {
        // Read from the database
        upRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                upValue = dataSnapshot.getValue(String.class);
                binding.upA1Txt.setText("UP value to a1 is: "+upValue);
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
                downValue = dataSnapshot.getValue(String.class);
                binding.downA1Txt.setText("Down value to a1 is: "+downValue);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}