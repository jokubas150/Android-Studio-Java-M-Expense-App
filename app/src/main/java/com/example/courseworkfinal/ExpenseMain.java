package com.example.courseworkfinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class ExpenseMain extends AppCompatActivity {
    public String [] tripExpense = {"Select Expense", "Food", "Hotel", "Fuel", "Tickets"};
    int tripId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        Button btnSave = findViewById(R.id.btnExpensesSave);

        EditText editTextExpense = findViewById(R.id.editTextExpense);
        Spinner spinnerTypeExpense = findViewById(R.id.spinnerTypeExpense);
        EditText editTextComments = findViewById(R.id.editTextComments);

        ArrayAdapter<String> dataAdapterExpense = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tripExpense);
        spinnerTypeExpense.setAdapter(dataAdapterExpense);

        btnSave.setOnClickListener(view -> {
            if (spinnerTypeExpense.getSelectedItemPosition() == 0) {
                ((TextView)spinnerTypeExpense.getSelectedView()).setError("Required Field"); //Code from https://stackoverflow.com/questions/28235689/how-can-an-error-message-be-set-for-the-spinner-in-android
            }
            if (TextUtils.isEmpty(editTextExpense.getText())){
                editTextExpense.setError("Required Field");
            }
            else {
                saveDetails();
            }
        });
    }

    private void saveDetails() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Detail detail = (Detail)getIntent().getExtras().getSerializable("details");

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        EditText nameAmount = findViewById(R.id.editTextExpense);
        Spinner nameType = findViewById(R.id.spinnerTypeExpense);
        EditText commentsText = findViewById(R.id.editTextComments);

        String amount = nameAmount.getText().toString();
        String expense = nameType.getSelectedItem().toString();
        String comments = commentsText.getText().toString();
        String time = timeFormat.format(date);
        tripId = detail.getDetails_id();

        Expense e = new Expense(expense, amount, time, comments, tripId);

        long expenseId = dbHelper.insertExpenses(e, tripId);

        Toast.makeText(this, "Expense has been created with id: " +
                expenseId, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, TripDetailsActivity.class);

        startActivity(intent);
    }
}
