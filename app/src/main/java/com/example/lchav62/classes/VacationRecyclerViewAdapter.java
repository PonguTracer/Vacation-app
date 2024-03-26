package com.example.lchav62.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lchav62.R;
import com.example.lchav62.activities.VacationDetailsActivity;

import java.util.List;

public class VacationRecyclerViewAdapter extends RecyclerView.Adapter<VacationRecyclerViewAdapter.VacationViewHolder> {
    Context context;
    List<VacationModel> vacationModelList;

    // Constructor to initialize the adapter with the context
    public VacationRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    // onCreateViewHolder: Inflates the layout for each item and returns the ViewHolder
    @NonNull
    @Override
    public VacationRecyclerViewAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(view);
    }

    // onBindViewHolder: Binds data to the ViewHolder and handles item click events
    @Override
    public void onBindViewHolder(@NonNull VacationRecyclerViewAdapter.VacationViewHolder holder, int position) {

        // Check if the list of vacationModelList is not null
        if (vacationModelList != null) {
            VacationModel currentVacation = vacationModelList.get(position);
            String title = currentVacation.getVacationTitle();
            holder.vacationListItem.setText(title);
        } else {
            // If the list is null, show "No Title"
            holder.vacationListItem.setText("No Title");
        }

        // Set click listener for each item in the RecyclerView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final VacationModel currentVacation = vacationModelList.get(position);

                // Create an intent to open VacationDetailsActivity and pass vacation information as extras
                Intent intent = new Intent(context, VacationDetailsActivity.class);
                intent.putExtra("vacationId", currentVacation.getVacationId());
                intent.putExtra("vacationTitle", currentVacation.getVacationTitle());
                intent.putExtra("vacationLodging", currentVacation.getVacationLodging());
                intent.putExtra("vacationStartDate", currentVacation.getVacationStartDate());
                intent.putExtra("vacationEndDate", currentVacation.getVacationEndDate());
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    // getItemCount: Returns the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        if (vacationModelList != null)
            return vacationModelList.size();
        else return 0;
    }

    // Method to set the list of vacation items and update the RecyclerView
    public void setVacations(List<VacationModel> vacationModels) {
        vacationModelList = vacationModels;
        notifyDataSetChanged();
    }

    // ViewHolder class to hold the view of each item in the RecyclerView
    class VacationViewHolder extends RecyclerView.ViewHolder {

        private final TextView vacationListItem;

        // Constructor for the ViewHolder
        private VacationViewHolder(View itemView) {
            super(itemView);
            vacationListItem = itemView.findViewById(R.id.vacationListItem);
        }
    }
}
