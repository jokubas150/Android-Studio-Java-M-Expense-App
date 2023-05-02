package com.example.courseworkfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Names of tables
    private static final String DATABASE_NAME = "m_expense_database_updated";
    private static final String TRIP_TABLE = "details_table";
    private static final String EXPENSE_TABLE = "expenses_table";


    // TRIP DATABASE COLUMN NAMES
    public static final String ID_TRIP_COLUMN = "trip_id";
    public static final String NAME_TRIP_COLUMN = "trip_name";
    public static final String TRANSPORT_TRIP_COLUMN = "type_transport";
    public static final String CURRENCY_TRIP_COLUMN = "required_currency";
    public static final String DESTINATION_TRIP_COLUMN = "destination";
    public static final String DATE_TRIP_COLUMN = "trip_date";
    public static final String RISK_TRIP_COLUMN = "required_risk";
    public static final String DESCRIPTION_TRIP_COLUMN = "description";

    // EXPENSES DATABASE COLUMN NAMES
    public static final String ID_EXPENSE_COLUMN = "expense_id";
    public static final String TYPE_EXPENSE_COLUMN = "type_expense";
    public static final String AMOUNT_EXPENSE_COLUMN = "amount";
    public static final String TIME_EXPENSE_COLUMN = "time";
    public static final String COMMENTS_EXPENSE_COLUMN = "comments";
    public static final String F_TRIP_ID_COLUMN = "f_trip_id"; // Foreign KEY

    private SQLiteDatabase database;

    // TRIP TABLE
    private static final String DATABASE_CREATE_TRIP = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)",
            TRIP_TABLE, ID_TRIP_COLUMN, NAME_TRIP_COLUMN, TRANSPORT_TRIP_COLUMN,
            CURRENCY_TRIP_COLUMN, DESTINATION_TRIP_COLUMN, DATE_TRIP_COLUMN,
            RISK_TRIP_COLUMN, DESCRIPTION_TRIP_COLUMN);

    // EXPENSE TABLE
    private static final String DATABASE_CREATE_EXPENSE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s INTEGER f_trip_id,  FOREIGN KEY ('f_trip_id') REFERENCES " + TRIP_TABLE + " ('trip_id'))", // https://stackoverflow.com/questions/60750335/creating-sqlite-database-table-with-foreign-keys
            EXPENSE_TABLE, ID_EXPENSE_COLUMN, TYPE_EXPENSE_COLUMN,
            AMOUNT_EXPENSE_COLUMN, TIME_EXPENSE_COLUMN, COMMENTS_EXPENSE_COLUMN, F_TRIP_ID_COLUMN);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        database = getWritableDatabase();
    }

    public void deleteTables(){
        database.execSQL("DELETE FROM "+ TRIP_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_TRIP);
        db.execSQL(DATABASE_CREATE_EXPENSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        onCreate(db);
    }

    public long insertTripDetails(Detail d){
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_TRIP_COLUMN, d.getTrip());
        rowValues.put(TRANSPORT_TRIP_COLUMN, d.getTransport());
        rowValues.put(CURRENCY_TRIP_COLUMN, d.getCurrency());
        rowValues.put(DESTINATION_TRIP_COLUMN, d.getDestination());
        rowValues.put(DATE_TRIP_COLUMN, d.getDate());
        rowValues.put(RISK_TRIP_COLUMN, d.getRisk());
        rowValues.put(DESCRIPTION_TRIP_COLUMN, d.getDescription());

        return database.insertOrThrow(TRIP_TABLE, null, rowValues);
    }

    public ArrayList<Detail> getAllTrips() {
        Cursor results = database.query("details_table", new String[] {"trip_id", "trip_name",
                        "type_transport", "required_currency", "destination", "trip_date", "required_risk",
                        "description"},//, "f_expense_id"},
                null, null, null, null, "trip_id");

        ArrayList<Detail> listTripDetails = new ArrayList<>();
        String resultText = "";

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int details_id = results.getInt(0);
            String trip = results.getString(1);
            String transport = results.getString(2);
            String currency = results.getString(3);
            String destination = results.getString(4);
            String date = results.getString(5);
            String risk = results.getString(6);
            String description = results.getString(7);
            //int f_expense_id = results.getInt(8);

            listTripDetails.add(new Detail(details_id, trip, transport, currency,
                    destination, date, risk, description));//, f_expense_id));

            results.moveToNext();
        }
        return listTripDetails;
    }

    public int deleteTrip (int id){
      return database.delete(TRIP_TABLE, ID_TRIP_COLUMN + "=?", new String[]{ String.valueOf(id)});
    }


    public int updateTrip (Detail d){
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_TRIP_COLUMN, d.getTrip());
        rowValues.put(TRANSPORT_TRIP_COLUMN, d.getTransport());
        rowValues.put(CURRENCY_TRIP_COLUMN, d.getCurrency());
        rowValues.put(DESTINATION_TRIP_COLUMN, d.getDestination());
        rowValues.put(DATE_TRIP_COLUMN, d.getDate());
        rowValues.put(RISK_TRIP_COLUMN, d.getRisk());
        rowValues.put(DESCRIPTION_TRIP_COLUMN, d.getDescription());

        return database.update(TRIP_TABLE, rowValues, ID_TRIP_COLUMN+"=?", new String[]{String.valueOf(d.getDetails_id())});
    }

    public long insertExpenses(Expense e, int tripId){
        ContentValues rowValues = new ContentValues();

        rowValues.put(TYPE_EXPENSE_COLUMN, e.getExpense());
        rowValues.put(AMOUNT_EXPENSE_COLUMN, e.getAmount());
        rowValues.put(TIME_EXPENSE_COLUMN, e.getTime());
        rowValues.put(COMMENTS_EXPENSE_COLUMN, e.getComments());
        rowValues.put(F_TRIP_ID_COLUMN, tripId);

        return database.insertOrThrow(EXPENSE_TABLE, null, rowValues);
    }

    public int deleteExpense (int id){
        return database.delete(EXPENSE_TABLE, ID_EXPENSE_COLUMN + "=?", new String[]{ String.valueOf(id)});
    }

    public ArrayList<Expense> getExpensesByTrip(int id) {
        Cursor results = database.query("expenses_table", new String[] {"expense_id", "type_expense",
                "amount", "time", "comments", "f_trip_id"},
                F_TRIP_ID_COLUMN + "="+ id, null, null, null, "f_trip_id");

        ArrayList<Expense> listExpense = new ArrayList<>();
        String resultText = "";

        results.moveToFirst();
        while (!results.isAfterLast()) {
            int expense_id = results.getInt(0);
            String expense = results.getString(1);
            String amount = results.getString(2);
            String time = results.getString(3);
            String comments = results.getString(4);
            int f_trip_id = results.getInt(5);

            listExpense.add(new Expense(expense_id, expense, amount, time, comments, f_trip_id));

            results.moveToNext();
        }
        return listExpense;
    }
}
