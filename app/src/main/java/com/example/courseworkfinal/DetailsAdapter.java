package com.example.courseworkfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder>{
    private ArrayList<Detail> details;
    public DetailsAdapter(ArrayList<Detail> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public DetailsAdapter.DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details, parent, false);
        return new DetailsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {

        final Detail detail = details.get(position);

        holder.tvItemTrip.setText(details.get(position).getTrip());
        holder.tvItemTransport.setText(details.get(position).getTransport());
        holder.tvItemDate.setText(details.get(position).getDate());
        holder.tvItemDescription.setText(details.get(position).getDescription());
        holder.tvItemDestination.setText(details.get(position).getDestination());
        holder.tvItemCurrency.setText(details.get(position).getCurrency());
        holder.tvItemAssessment.setText(details.get(position).getRisk());

        holder.btnItemDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

            builder.setTitle("Confirm");
            builder.setMessage("Are you sure you want to delete " + details.get(position).getDestination() + " trip?") ;// code from https://www.youtube.com/watch?v=7-Bl_DHR-5I&ab_channel=IntelliLogics
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                DatabaseHelper dbHelper = new DatabaseHelper(view.getContext());
                int result = dbHelper.deleteTrip(details.get(position).getDetails_id());

                if (result > 0) {
                    Toast.makeText(view.getContext(), "Deleted", Toast.LENGTH_SHORT);
                    details.remove(details.get(position));
                    notifyDataSetChanged();
                }
                else {
                    Toast.makeText(view.getContext(), "Failed", Toast.LENGTH_SHORT);
                }
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });

        holder.btnItemUpdate.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), UpdateDetails.class);

            intent.putExtra("details", detail);
            intent.putExtra("details_id", details.get(position).getDetails_id());
            intent.putExtra("trip_name", details.get(position).getTrip());
            intent.putExtra("type_transport", details.get(position).getTransport());
            intent.putExtra("required_currency", details.get(position).getCurrency());
            intent.putExtra("destination", details.get(position).getDestination());
            intent.putExtra("trip_date", details.get(position).getDate());
            intent.putExtra("required_risk", details.get(position).getRisk());
            intent.putExtra("description", details.get(position).getDescription());

            view.getContext().startActivity(intent);
        });

        holder.btnAddExpense.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ExpenseMain.class);
            intent.putExtra("details", detail);
            view.getContext().startActivity(intent);
        });

        holder.trip_details_view.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ExpenseDetailActivity.class);
            intent.putExtra("details", detail);
            view.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() { return details.size(); }

    public void filter(String text){
        ArrayList<Detail> detailsCopy = (ArrayList<Detail>)details.clone();

        details.clear();
        if(text == null || text.isEmpty()){
            details.addAll(detailsCopy);
        } else{
            text = text.toLowerCase();
            for (Detail detail: detailsCopy){
                if (detail.getTrip().toLowerCase().contains(text) || detail.getDestination().toLowerCase().contains(text)){
                    details.add(detail);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class DetailsViewHolder extends RecyclerView.ViewHolder{

        public CardView trip_details_view;
        public TextView tvItemTrip;
        public TextView tvItemCurrency;
        public TextView tvItemDate;
        public TextView tvItemDescription;
        public TextView tvItemTransport;
        public TextView tvItemDestination;
        public TextView tvItemAssessment;
        public ImageButton btnItemDelete;
        public ImageButton btnItemUpdate;
        public ImageButton btnAddExpense;
        public SearchView searchView;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            trip_details_view = itemView.findViewById(R.id.trip_details_view);
            tvItemTrip = itemView.findViewById(R.id.tvItemTrip);
            tvItemTransport = itemView.findViewById(R.id.tvItemTransport);
            tvItemCurrency = itemView.findViewById(R.id.tvItemCurrency);
            tvItemDate = itemView.findViewById(R.id.tvItemDate);
            tvItemDescription = itemView.findViewById(R.id.tvItemDescription);
            tvItemDestination = itemView.findViewById(R.id.tvItemDestination);
            tvItemAssessment = itemView.findViewById(R.id.tvItemAssessment);
            btnItemDelete = itemView.findViewById(R.id.btnItemDelete);
            btnItemUpdate = itemView.findViewById(R.id.btnItemUpdate);
            btnAddExpense = itemView.findViewById(R.id.btnAddExpense);
            searchView = itemView.findViewById(R.id.search_menu);
        }
    }
}
