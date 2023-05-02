package com.example.courseworkfinal;

public class Expense {
    private String expense;
    private String amount;
    private String time;
    private String comments;
    private int expense_id;
    private int f_trip_id;

    public Expense (String expense, String amount, String time, String comments, int f_trip_id){
        this.expense = expense;
        this.amount = amount;
        this.time = time;
        this.comments = comments;
        this.f_trip_id = f_trip_id;
    }
    public Expense (int expense_id, String expense, String amount, String time, String comments, int f_trip_id){
        this.expense_id = expense_id;
        this.expense = expense;
        this.amount = amount;
        this.time = time;
        this.comments = comments;
        this.f_trip_id = f_trip_id;
    }

    public void setExpense(String expense) { this.expense = expense; }
    public String getExpense() { return expense; }

    public void setAmount(String amount) { this.amount = amount; }
    public String getAmount() {  return amount; }

    public void setTime(String time) { this.time = time; }
    public String getTime() { return time; }

    public void setComments(String comments) {  this.comments = comments; }
    public String getComments() { return comments; }

    public void setExpense_id(int expense_id) { this.expense_id = expense_id; }
    public int getExpense_id() { return expense_id; }

    public void setF_trip_id(int f_trip_id) { this.f_trip_id = f_trip_id; }
    public int getF_trip_id() { return f_trip_id; }
}
