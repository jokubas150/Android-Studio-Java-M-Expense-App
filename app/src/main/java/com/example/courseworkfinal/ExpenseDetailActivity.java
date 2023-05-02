package com.example.courseworkfinal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseDetailActivity extends AppCompatActivity {
    RecyclerView.Adapter myTripAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    int detailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.rvDetails);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper db = new DatabaseHelper(this);

        Detail detail = (Detail)getIntent().getExtras().getSerializable("details");

        detailId = detail.getDetails_id();
        ArrayList<Expense> expenses = db.getExpensesByTrip(detailId);

        myTripAdapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(myTripAdapter);
    }
}
