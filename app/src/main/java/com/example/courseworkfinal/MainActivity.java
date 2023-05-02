package com.example.courseworkfinal;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnView = findViewById(R.id.btnViewTrips);
        Button btnAdd = findViewById(R.id.btnAddTrip);
        Button btnDelete = findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(view -> addTrip());

        btnView.setOnClickListener(view -> viewDetails());

        btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmation");
            builder.setMessage("Are you sure you want to DELETE ALL trips?");

            builder.setPositiveButton("Yes", (dialogInterface, i) -> deleteAll());
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }

    private void addTrip(){
        Intent intent = new Intent(this, DetailsTripMain.class);
        startActivity(intent);
    }

    private void viewDetails() {
        Intent intent = new Intent(this, TripDetailsActivity.class);
        startActivity(intent);
    }

    private void deleteAll(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteTables();
    }
}