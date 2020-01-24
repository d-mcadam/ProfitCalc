package com.example.profitcalcapp.Utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.profitcalcapp.Activities.AuraManagementActivity;
import com.example.profitcalcapp.Activities.CreateAuraActivity;
import com.example.profitcalcapp.Data.Aura;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;

import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.EDIT_AURA_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class AuraAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Storage storage;
    private final Commands cmds = new Commands();

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
        return new Item(row);
    }

    protected void ResetViewHolder(RecyclerView.ViewHolder holder){
        holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.originalWhite, null));
        ((Item) holder).edit.setVisibility(View.INVISIBLE);
        ((Item) holder).edit.setClickable(false);
        ((Item) holder).delete.setVisibility(View.INVISIBLE);
        ((Item) holder).delete.setClickable(false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final int selectedColour = context.getResources().getColor(R.color.selectedGrey, null);
        final Aura item = items.get(position);
        ((Item)holder).title.setText(item.getTitle());

        if (focusedPosition == position){
            if (((ColorDrawable)holder.itemView.getBackground()).getColor() != selectedColour){
                holder.itemView.setBackgroundColor(selectedColour);
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

        ((Item) holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent wnd = new Intent(context, CreateAuraActivity.class);
                wnd.putExtra(EDIT_AURA_KEY, items.get(position));
                wnd.putExtra(STORAGE_CLASS_DATA, storage);
                context.startActivity(wnd);
                ((AuraManagementActivity)context).finish();
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
        ImageButton edit;
        ImageButton delete;

        public Item(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.rowTitleAura);
            edit = itemView.findViewById(R.id.rowButtonEditAura);
            delete = itemView.findViewById(R.id.rowButtonDeleteAura);
        }
    }

}
