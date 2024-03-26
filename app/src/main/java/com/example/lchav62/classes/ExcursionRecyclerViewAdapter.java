package com.example.lchav62.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lchav62.classes.ExcursionsModel;
import com.example.lchav62.R;
import com.example.lchav62.activities.ExcursionDetailsActivity;

import java.util.List;

public class ExcursionRecyclerViewAdapter extends RecyclerView.Adapter<ExcursionRecyclerViewAdapter.ExcursionViewHolder> {

    // Declare necessary variables
    Context context;
    private List<ExcursionsModel> excursionsModelList;

    // Constructor to initialize the adapter with a context
    public ExcursionRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    // Create the view holder for each item in the RecyclerView
    @NonNull
    @Override
    public ExcursionRecyclerViewAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the list
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder((itemView));
    }

    // Bind the data to each view holder in the RecyclerView
    @Override
    public void onBindViewHolder(@NonNull ExcursionRecyclerViewAdapter.ExcursionViewHolder holder, int position) {

        // Check if the list of excursions is not null
        if (excursionsModelList != null) {
            ExcursionsModel current = excursionsModelList.get(position);
            String title = current.getExcursionTitle();
            holder.excursionListItem.setText(title);
        } else {
            holder.excursionListItem.setText("No Title");
        }

        // Set a click listener for each item in the list to open the ExcursionDetailsActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final ExcursionsModel current = excursionsModelList.get(position);

                // Create an intent to open the ExcursionDetailsActivity and pass necessary data
                Intent intent = new Intent(context, ExcursionDetailsActivity.class);
                intent.putExtra("vacationId", current.getVacationId());
                intent.putExtra("excursionId", current.getExcursionId());
                intent.putExtra("excursionTitle", current.getExcursionTitle());
                intent.putExtra("excursionStartDate", current.getExcursionStartDate());
                context.startActivity(intent);
            }
        });
    }

    // Get the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        if (excursionsModelList != null)
            return excursionsModelList.size();
        else return 0;
    }

    // Set the list of excursions to display in the RecyclerView
    public void setExcursions(List<ExcursionsModel> excursions) {
        excursionsModelList = excursions;
        notifyDataSetChanged();
    }

    // Define the ViewHolder to hold the views for each item in the RecyclerView
    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionListItem;

        // Constructor to initialize the views in the ViewHolder
        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionListItem = itemView.findViewById(R.id.excursionListItem);
        }
    }
}
