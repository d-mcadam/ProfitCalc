package com.example.profitcalcapp.Utilities;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;

import java.math.BigDecimal;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Storage storage;
    private final Commands cmds = new Commands();

    private Context context;
    private List<Category> items;

    private final int defaultHeight = 1429;
    private final int defaultDivision = 12;
    private final int expandedDivision = 4;

    public int focusedPosition = -1;

    public CategoryAdapter(Context context, List<Category> items, Storage storage){
        this.context = context;
        this.items = items;
        this.storage = storage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(context).inflate(R.layout.custom_row_category, parent, false);
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
        final Category item = items.get(position);
        ((Item)holder).title.setText(item.getTitle());

        StringBuilder sb = new StringBuilder();
        sb.append("Profits: ").append(cmds.BigDecimalFormatter().format(item.getTotalProfit())).
                append("\nEntries: ").append(item.getEntryCount()).
                append("\nKills: ").append(cmds.BigDecimalFormatter().format(item.getTotalKills())).
                append("\nHours: ").append(cmds.BigDecimalFormatter().format(item.getTotalHoursSpent()));

        String string = sb.toString();
        Spannable colouredText = new SpannableString(string);
        if (item.getTotalProfit().compareTo(new BigDecimal("0")) < 0)
            colouredText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.pureRed, null)), string.indexOf(" "), string.indexOf("\n"), string.length());

        ((Item) holder).brief.setText(colouredText);

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

            }
        });
        ((Item) holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EDITING_CATEGORY_PASS_KEY
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
        TextView brief;
        ImageButton view;
        ImageButton edit;
        ImageButton delete;

        public Item(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rowTitleCategory);
            brief = itemView.findViewById(R.id.rowBriefCategory);
            view = itemView.findViewById(R.id.rowButtonViewCategory);
            edit = itemView.findViewById(R.id.rowButtonEditCategory);
            delete = itemView.findViewById(R.id.rowButtonDeleteCategory);
        }
    }

}
