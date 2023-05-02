package com.example.courseworkfinal;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsTripMain  extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public TextView tvTrip, tvTransport, tvCurrency, tvDestination, tvDate, tvRisk, tvDescription, tvDatePicker;
    public Button btnCancelPopup, btnSavePopup, btnNext;
    public EditText editTextDestination, editTextDescription, editTextDate;
    public Spinner spinnerNameTrip, spinnerTransport, spinnerCurrency;
    public RadioGroup radioGroupRisk;

    public String [] tripArray = {"Select Trip", "Conference", "Client Meeting"};
    public String [] currencyArray = {"Select Currency", "Not needed", "$ USD", "€ Euro", "$ AUD", "¥ JPY"};
    public String [] transportArray = {"Select Transport", "Bus", "Taxi", "Train", "Plane"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_details);

        btnNext = (Button)findViewById(R.id.btnUpdateSave);
        spinnerNameTrip = (Spinner)findViewById(R.id.spinnerUpdateNameTrip);
        spinnerTransport = (Spinner)findViewById(R.id.spinnerUpdateTransport);
        spinnerCurrency = (Spinner)findViewById(R.id.spinnerUpdateCurrency);

        radioGroupRisk = (RadioGroup)findViewById(R.id.radioGroupUpdateRisk);

        editTextDestination = (EditText)findViewById(R.id.editTextUpdateDestination);
        editTextDescription = (EditText)findViewById(R.id.editTextUpdateDescription);

        tvDatePicker = (TextView)findViewById(R.id.tvDatePicker);

        ArrayAdapter<String> dataAdapterTrip = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tripArray);
        spinnerNameTrip.setAdapter(dataAdapterTrip);

        ArrayAdapter<String> dataAdapterCurrency = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencyArray);
        spinnerCurrency.setAdapter(dataAdapterCurrency);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, transportArray);
        spinnerTransport.setAdapter(dataAdapter2);

        btnNext.setOnClickListener(view -> {
            if (spinnerNameTrip.getSelectedItemPosition() == 0) {
                ((TextView)spinnerNameTrip.getSelectedView()).setError("Required Field"); //Code from https://stackoverflow.com/questions/28235689/how-can-an-error-message-be-set-for-the-spinner-in-android
            }
            if (spinnerTransport.getSelectedItemPosition() == 0){
                ((TextView)spinnerTransport.getSelectedView()).setError("Required Field");
            }
            if (spinnerCurrency.getSelectedItemPosition() == 0){
                ((TextView)spinnerCurrency.getSelectedView()).setError("Required Field");
            }
            if (tvDatePicker.getText().equals("Tap Here to Select Date")){
                tvDatePicker.setTextColor(Color.parseColor("#ff0000"));
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            if (TextUtils.isEmpty(editTextDestination.getText())){
                editTextDestination.setError("Required Field");
            }
            if(radioGroupRisk.getCheckedRadioButtonId() == -1) {
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select Risk", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            else {
                //display info
                createNewContactDialog();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            ((DetailsTripMain)getActivity()).updateDOB(year, month, day);
        }
    }

    public void updateDOB(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        TextView tvDatePicker = (TextView) findViewById(R.id.tvDatePicker);
        tvDatePicker.setText(date);
    }

    public void showDatePickerDialog(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void createNewContactDialog() { // Some of the CODE FROM https://www.youtube.com/watch?v=4GYKOzgQDWI&ab_channel=CodingMark
        dialogBuilder = new AlertDialog.Builder(this);
        final View contactPopupView = getLayoutInflater().inflate(R.layout.popup,null);

        tvTrip = contactPopupView.findViewById(R.id.tvTrip);
        tvTransport = contactPopupView.findViewById(R.id.tvTransport);
        tvCurrency = contactPopupView.findViewById(R.id.tvCurrency);
        tvDestination = contactPopupView.findViewById(R.id.tvDestination);
        tvDate = contactPopupView.findViewById(R.id.tvDate);
        tvRisk = contactPopupView.findViewById(R.id.tvRisk);
        tvDescription = contactPopupView.findViewById(R.id.tvDescription);

        spinnerNameTrip = findViewById(R.id.spinnerUpdateNameTrip);
        spinnerTransport = findViewById(R.id.spinnerUpdateTransport);
        spinnerCurrency = findViewById(R.id.spinnerUpdateCurrency);

        radioGroupRisk = findViewById(R.id.radioGroupUpdateRisk);
        editTextDestination = findViewById(R.id.editTextUpdateDestination);
        editTextDescription = findViewById(R.id.editTextUpdateDescription);
        tvDatePicker = findViewById(R.id.tvDatePicker);

        int riskId = radioGroupRisk.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(riskId);

        // CONVERT FROM USER INPUT TO STRING
        // CODE FROM https://www.youtube.com/watch?v=fJEFZ6EOM9o&ab_channel=CodinginFlow
        String textTrip = spinnerNameTrip.getSelectedItem().toString(),
                textTransport = spinnerTransport.getSelectedItem().toString(),
                textCurrency = spinnerCurrency.getSelectedItem().toString(),
                textRisk = radioButton.getText().toString(),
                textDate = tvDatePicker.getText().toString(),
                textDestination = editTextDestination.getText().toString(),
                textDescription = editTextDescription.getText().toString();

        // SET TEXT FROM USER INPUT
        tvTrip.setText(textTrip);
        tvTransport.setText(textTransport);
        tvCurrency.setText(textCurrency);
        tvDate.setText(textDate);
        tvRisk.setText(textRisk);
        tvDestination.setText(textDestination);
        tvDescription.setText(textDescription);

        btnCancelPopup = contactPopupView.findViewById(R.id.btnCancelPopup);
        btnSavePopup = contactPopupView.findViewById(R.id.btnSavePopup);

        dialogBuilder.setView(contactPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        Intent intent = new Intent(this, TripDetailsActivity.class);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Detail d = new Detail(textTrip, textTransport, textCurrency, textDestination,
                textDate, textRisk, textDescription);

        btnSavePopup.setOnClickListener(view -> {

            long detailsId = dbHelper.insertTripDetails(d);
            Toast.makeText(this, "Trip has been created with id: " + detailsId, Toast.LENGTH_LONG).show();
            startActivity(intent);
            dialog.dismiss();

        });

        btnCancelPopup.setOnClickListener(view -> dialog.dismiss());
    }
}

