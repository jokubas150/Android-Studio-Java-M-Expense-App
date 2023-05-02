package com.example.courseworkfinal;

import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class TripDetailsActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    RecyclerView.Adapter myTripAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseHelper db;
    private ArrayList<Detail> details;
    private DetailsAdapter detailsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        recyclerView = findViewById(R.id.rvDetails);
        db = new DatabaseHelper(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        db = new DatabaseHelper(this);

        details = db.getAllTrips();

        myTripAdapter = new DetailsAdapter(details);
        recyclerView.setAdapter(myTripAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem search = menu.findItem(R.id.search_menu);
        final SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        detailsAdapter.filter(query);
        return true;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null) {
            detailsAdapter = new DetailsAdapter(new ArrayList<>());
            detailsAdapter.filter(newText);
        }
        return true;
    }
}
