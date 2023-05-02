package com.example.courseworkfinal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class UpdateDetails extends AppCompatActivity {
    public EditText editTextUpdateDestination, editTextUpdateDescription;
    public Spinner spinnerUpdateNameTrip, spinnerUpdateTransport, spinnerUpdateCurrency;
    public RadioGroup radioGroupUpdateRisk;
    public RadioButton radioButton;
    public Button btnSave;
    int detailId;
    private TextView tvDatePicker;
    public String [] tripArray = {"Select Trip", "Conference", "Client Meeting"};
    public String [] currencyArray = {"Select Currency", "Not needed", "$ USD", "€ Euro", "$ AUD", "¥ JPY"};
    public String [] transportArray = {"Select Transport", "Bus", "Taxi", "Train", "Plane"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);
        Detail detail = (Detail)getIntent().getExtras().getSerializable("details");

        DatabaseHelper dbHandler = new DatabaseHelper(this);

        spinnerUpdateNameTrip = findViewById(R.id.spinnerUpdateNameTrip);
        spinnerUpdateTransport = findViewById(R.id.spinnerUpdateTransport);
        spinnerUpdateCurrency = findViewById(R.id.spinnerUpdateCurrency);

        radioGroupUpdateRisk = findViewById(R.id.radioGroupUpdateRisk);

        editTextUpdateDestination = findViewById(R.id.editTextUpdateDestination);
        editTextUpdateDescription = findViewById(R.id.editTextUpdateDescription);

        tvDatePicker = findViewById(R.id.tvDatePickerUpdate);
        btnSave = findViewById(R.id.btnUpdateSave);

        ArrayAdapter<String> dataAdapterTrip = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, tripArray);
        spinnerUpdateNameTrip.setAdapter(dataAdapterTrip);

        ArrayAdapter<String> dataAdapterCurrency = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, currencyArray);
        spinnerUpdateCurrency.setAdapter(dataAdapterCurrency);

        ArrayAdapter<String> dataAdapterTransport = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, transportArray);
        spinnerUpdateTransport.setAdapter(dataAdapterTransport);

        //SET The fields from previous intent
        //Get the ID FROM previous intent to this
        detailId = detail.getDetails_id();
        //Spinner choices
        String tripSelection = detail.getTrip();
        int spinnerTripPosition = dataAdapterTrip.getPosition(tripSelection);
        spinnerUpdateNameTrip.setSelection(spinnerTripPosition);

        String transportSelection = detail.getTransport();
        int spinnerTransportPosition = dataAdapterTransport.getPosition(transportSelection);
        spinnerUpdateTransport.setSelection(spinnerTransportPosition);

        String currencySelection = detail.getCurrency();
        int spinnerCurrencyPosition = dataAdapterCurrency.getPosition(currencySelection);
        spinnerUpdateCurrency.setSelection(spinnerCurrencyPosition);
        //Edit text choices
        tvDatePicker.setText(detail.getDate());
        editTextUpdateDestination.setText(detail.getDestination());
        editTextUpdateDescription.setText(detail.getDescription());
        //RadioButton Choice
        String radioChoice = detail.getRisk();

        int riskId = radioGroupUpdateRisk.getCheckedRadioButtonId();
        RadioButton radioButton = findViewById(riskId);

        btnSave.setOnClickListener(view -> {
            if (TextUtils.isEmpty(editTextUpdateDestination.getText())){
                editTextUpdateDestination.setError("Required Field");
            }
            if (spinnerUpdateNameTrip.getSelectedItemPosition() == 0) {
                ((TextView)spinnerUpdateNameTrip.getSelectedView()).setError("Required Field"); //Code from https://stackoverflow.com/questions/28235689/how-can-an-error-message-be-set-for-the-spinner-in-android
            }
            if (spinnerUpdateTransport.getSelectedItemPosition() == 0){
                ((TextView)spinnerUpdateTransport.getSelectedView()).setError("Required Field");
            }
            if (tvDatePicker.getText().equals("Tap Here to Select Date")){
                Toast toast = Toast.makeText(getApplicationContext(), "Please Select Date", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            else {
                String tripUpdated = spinnerUpdateNameTrip.getSelectedItem().toString(),
                        textTransportUpdated = spinnerUpdateTransport.getSelectedItem().toString(),
                        textCurrencyUpdated = spinnerUpdateCurrency.getSelectedItem().toString(),
                        //textUpdatedRisk = radioButton.getText().toString(),
                        textDateUpdated = tvDatePicker.getText().toString(),
                        textDestinationUpdated = editTextUpdateDestination.getText().toString(),
                        textDescriptionUpdated = editTextUpdateDescription.getText().toString();

                String textUpdatedRisk = "";

                Detail updatedDetail = new Detail(detailId, tripUpdated, textTransportUpdated, textCurrencyUpdated, textDestinationUpdated,
                        textDateUpdated, textUpdatedRisk, textDescriptionUpdated);

                dbHandler.updateTrip(updatedDetail);

                Intent intentNew = new Intent(UpdateDetails.this, TripDetailsActivity.class);
                this.startActivity(intentNew);
                finish();

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
            ((UpdateDetails)getActivity()).updatedDOB(year, month, day);
        }
    }

    public void updatedDOB(int year, int month, int day) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());

        tvDatePicker = findViewById(R.id.tvDatePickerUpdate);
        tvDatePicker.setText(date);
    }

    public void showDatePickerDialogs(View v){
        DialogFragment newFragment = new UpdateDetails.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datedPicker");
    }
}
