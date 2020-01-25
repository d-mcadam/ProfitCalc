package com.example.profitcalcapp.Utilities;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profitcalcapp.Data.DataEntry;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;

import java.util.List;

public class DataEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Storage storage;

    private Context context;
    private List<DataEntry> items;

    private final int defaultHeight = 1245;
    private final int defaultDivision = 11;
    private final int expandedDivision = 8;

    public int focusedPosition = -1;

    public DataEntryAdapter(Context context, List<DataEntry> items, Storage storage){
        this.context = context;
        this.items = items;
        this.storage = storage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println(parent.getMeasuredHeight());
        View row = LayoutInflater.from(context).inflate(R.layout.custom_row_data_entry, parent, false);
        row.getLayoutParams().height = defaultHeight / defaultDivision;
        return new Item(row);
    }

    protected void ResetViewHolder(RecyclerView.ViewHolder holder){
        holder.itemView.getLayoutParams().height = defaultHeight / defaultDivision;
        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.originalWhite, null));
        ((Item) holder).view.setVisibility(View.INVISIBLE);
        ((Item) holder).view.setClickable(false);
        ((Item) holder).edit.setVisibility(View.INVISIBLE);
        ((Item) holder).edit.setClickable(false);
        ((Item) holder).delete.setVisibility(View.INVISIBLE);
        ((Item) holder).delete.setClickable(false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final int selectedColour = context.getResources().getColor(R.color.selectedGrey, null);
        final DataEntry item = items.get(position);
        ((Item)holder).title.setText(item.getTitle());

        if (focusedPosition == position){
            if (((ColorDrawable)holder.itemView.getBackground()).getColor() != selectedColour){
                holder.itemView.getLayoutParams().height = defaultHeight / expandedDivision;
                holder.itemView.setBackgroundColor(selectedColour);
                ((Item) holder).view.setVisibility(View.VISIBLE);
                ((Item) holder).view.setClickable(true);
                ((Item) holder).edit.setVisibility(View.VISIBLE);
                ((Item) holder).edit.setClickable(true);
                ((Item) holder).delete.setVisibility(View.VISIBLE);
                ((Item) holder).delete.setClickable(true);
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

        ((Item) holder).view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DATA_ENTRY_PASS_KEY
            }
        });
        ((Item) holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DATA_ENTRY_PASS_KEY
            }
        });
        ((Item) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    public class Item extends RecyclerView.ViewHolder{
        TextView title;
        ImageButton view;
        ImageButton edit;
        ImageButton delete;

        public Item(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.rowTitleDataEntry);
            view = itemView.findViewById(R.id.rowButtonViewDataEntry);
            edit = itemView.findViewById(R.id.rowButtonEditDataEntry);
            delete = itemView.findViewById(R.id.rowButtonDeleteDataEntry);
        }
    }

}
