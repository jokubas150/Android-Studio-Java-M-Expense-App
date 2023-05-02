package com.example.courseworkfinal;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private ArrayList<Expense> expenses;

    public ExpenseAdapter(ArrayList<Expense> expenses) { this.expenses = expenses; }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses, parent, false);
        return new ExpenseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        holder.tvItemExpense.setText(expenses.get(position).getExpense());
        holder.tvItemAmount.setText(expenses.get(position).getAmount());
        holder.tvItemTime.setText(expenses.get(position).getTime());
        holder.tvItemComments.setText(expenses.get(position).getComments());

        holder.btnDeleteExpense.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure you want to delete " + expenses.get(position).getExpense() + " ?") ;// code from https://www.youtube.com/watch?v=7-Bl_DHR-5I&ab_channel=IntelliLogics
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
                int result = dbHelper.deleteExpense(expenses.get(position).getExpense_id());

                if (result > 0) {
                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT);
                    expenses.remove(expenses.get(position));
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT);
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });
    }

    @Override
    public int getItemCount() { return expenses.size(); }

    public class ExpenseViewHolder extends RecyclerView.ViewHolder{
        public TextView tvItemExpense;
        public TextView tvItemAmount;
        public TextView tvItemTime;
        public TextView tvItemComments;
        public Button btnDeleteExpense;

        public ExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemExpense = itemView.findViewById(R.id.tvItemExpense);
            tvItemAmount = itemView.findViewById(R.id.tvItemAmount);
            tvItemTime = itemView.findViewById(R.id.tvItemTime);
            tvItemComments = itemView.findViewById(R.id.tvItemComments);
            btnDeleteExpense = itemView.findViewById(R.id.btnDeleteExpense);
        }
    }
}
