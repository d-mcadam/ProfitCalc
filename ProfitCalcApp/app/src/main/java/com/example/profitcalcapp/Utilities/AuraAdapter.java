package com.example.profitcalcapp.Utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.R;

import java.util.List;

public class AuraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Storage storage;

    private Context context;
    private List<Aura> items;

    public int focusedPosition = -1;

    public AuraAdapter(Context context, List<Aura> items, Storage storage){
        this.context = context;
        this.items = items;
        this.storage = storage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.custom_row_aura, parent, false);
        row.getLayoutParams().height = parent.getMeasuredHeight() / 12;
        Item item = new Item(row);
        return item;
    }

    protected void ResetViewHolder(RecyclerView.ViewHolder holder){
        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.originalWhite));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final int selectedColour = context.getResources().getColor(R.color.selectedGrey);
        final Aura item = items.get(position);
        ((Item)holder).title.setText(item.getTitle());

        if (focusedPosition == position){
            if (((ColorDrawable)holder.itemView.getBackground()).getColor() != selectedColour){
                holder.itemView.setBackgroundColor(selectedColour);
            }else{
                ResetViewHolder(holder);
            }
        }else{
            ResetViewHolder(holder);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedPosition = position;
                notifyDataSetChanged();
            }
        });
        //you can set additional listeners for buttons

    }

    @Override
    public int getItemCount() { return items.size(); }

    public class Item extends RecyclerView.ViewHolder{
        TextView title;

        public Item(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.rowTitleAura);
        }
    }

}
