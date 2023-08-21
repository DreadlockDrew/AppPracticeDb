package com.example.dbpractice1.DataIntegration;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dbpractice1.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> items;



    private OnItemActionListener listener;
    public interface OnItemActionListener {
        void onDeleteItem(int itemId);
        void onEditItem(int itemId,String name,int quantity);

    }


    public void setOnItemActionListener(OnItemActionListener listener) {
        this.listener = listener;
    }

    // Constructor for the adapter
    // Initializes the adapter with a list of Item objects. This list will be displayed in the RecyclerView.
    public ItemAdapter(List<Item> items) {
        this.items = items;
    }

    /*
    This method is called when the RecyclerView needs a new ViewHolder to represent an item.
    It inflates the item layout (item_layout.xml) and initializes a new ViewHolder with this
    layout.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(itemView);
    }

    /*
    Binds the data from a specific Item object in the list to a ViewHolder.
    It sets the item name and quantity to the appropriate TextViews.
    It also sets up click listeners for the edit and delete buttons.
    This method is called for every item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item currentItem = items.get(position);

        holder.tvItemName.setText(currentItem.getItemName());
        holder.tvItemQuantity.setText(String.valueOf(currentItem.getItemQuantity()));

        holder.btnEdit.setOnClickListener(view -> {

            Log.d("ItemAdapter", String.format("Original Content ID|%d|Name|%s|Quantity|%d|",
                    currentItem.getItemID(),
                    currentItem.getItemName(),
                    currentItem.getItemQuantity()));

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            LayoutInflater inflater = LayoutInflater.from(view.getContext());
            View dialogView = inflater.inflate(R.layout.dialog_edit_item, null);
            builder.setView(dialogView);

            EditText etItemName = dialogView.findViewById(R.id.etItemName);
            EditText etItemQuantity = dialogView.findViewById(R.id.etItemQuantity);

            etItemName.setText(currentItem.getItemName());
            etItemQuantity.setText(String.valueOf(currentItem.getItemQuantity()));

            builder.setTitle("Edit Item")
                    .setPositiveButton("Save", (dialog, id) -> {
                        // Get updated values
                        String updatedName = etItemName.getText().toString().trim();
                        int updatedQuantity = Integer.parseInt(etItemQuantity.getText().toString().trim());

                        // Update the item in the database

                        Log.d("ItemAdapter", String.format("Sending ID|%d|Name|%s|Quantity|%d| to ItemActivity",
                                currentItem.getItemID(),
                                currentItem.getItemName(),
                                currentItem.getItemQuantity()));




                        listener.onEditItem(currentItem.getItemID(),updatedName,updatedQuantity);


                        // Update the RecyclerView item
                        //items.set(position, currentItem);
                        //notifyItemChanged(position);
                    })
                    .setNegativeButton("Cancel", (dialog, id) -> {
                        dialog.dismiss();
                    });
            builder.create().show();
        });

        holder.btnDelete.setOnClickListener(view -> {
            if(listener != null) {
                listener.onDeleteItem(currentItem.getItemID());
            }
        });
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    // ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvItemQuantity;
        Button btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    public void updateData(List<Item> newItems) {
        this.items = newItems;
        notifyDataSetChanged(); // This will refresh the entire list
    }

}