package com.example.profitcalcapp.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profitcalcapp.Activities.CreateCategoryActivity;
import com.example.profitcalcapp.Activities.CreateDataEntryActivity;
import com.example.profitcalcapp.Data.Category;
import com.example.profitcalcapp.Data.DataEntry;
import com.example.profitcalcapp.Data.Storage;
import com.example.profitcalcapp.R;

import java.math.BigDecimal;
import java.util.List;

import static com.example.profitcalcapp.Utilities.IntentKeys.DATA_ENTRY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.EDITING_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.NEW_CATEGORY_PASS_KEY;
import static com.example.profitcalcapp.Utilities.IntentKeys.STORAGE_CLASS_DATA;

public class DataEntryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Storage storage;
    private final Commands cmds = new Commands();

    private Context context;
    private List<DataEntry> items;

    private final int defaultHeight = 1245;
    private final int defaultDivision = 11;
    private final int expandedDivision = 4;

    public int focusedPosition = -1;

    public DataEntryAdapter(Context context, List<DataEntry> items, Storage storage){
        this.context = context;
        this.items = items;
        this.storage = storage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        StringBuilder sb = new StringBuilder();
        sb.append("Profits: ").append(cmds.BigDecimalFormatter().format(item.getProfit())).
                append("\nKills: ").append(cmds.BigDecimalFormatter().format(item.getKillCount())).
                append("\nHours: ").append(cmds.BigDecimalFormatter().format(item.getHoursSpent()));

        String string = sb.toString();
        Spannable colouredText = new SpannableString(string);
        if (item.getProfit().compareTo(new BigDecimal("0")) < 0)
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
                Intent wnd = new Intent(context, CreateDataEntryActivity.class);

                if (((CreateCategoryActivity)context).editingCategory == null) {
                    Category c = ((CreateCategoryActivity) context).tempCategory;
                    c.setTitle(((CreateCategoryActivity) context).fieldCategoryTitle.getText().toString().trim());
                    wnd.putExtra(NEW_CATEGORY_PASS_KEY, ((CreateCategoryActivity) context).tempCategory);
                }else {
                    Category c = ((CreateCategoryActivity) context).editingCategory;
                    c.setTitle(((CreateCategoryActivity) context).fieldCategoryTitle.getText().toString().trim());
                    wnd.putExtra(EDITING_CATEGORY_PASS_KEY, ((CreateCategoryActivity) context).editingCategory);
                }

                wnd.putExtra(DATA_ENTRY_PASS_KEY, item);
                wnd.putExtra(STORAGE_CLASS_DATA, storage);
                context.startActivity(wnd);
                ((CreateCategoryActivity)context).finish();
            }
        });
        ((Item) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);

                dialog.setCancelable(true);
                dialog.setTitle("Deleting '" + item.getTitle() + "'");
                dialog.setMessage("Deleting items cannot be undone, do you wish to continue?");

                dialog.setNegativeButton("Cancel", null);
                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        BooleanString r;
                        if (((CreateCategoryActivity)context).editingCategory == null)
                            r = ((CreateCategoryActivity)context).tempCategory.deleteEntry(item);
                        else
                            r = ((CreateCategoryActivity)context).editingCategory.deleteEntry(item);

                        if (!r.result)
                            Toast.makeText(context, r.msg, Toast.LENGTH_LONG).show();
                        new AppDataStorage(context, storage).execute();
                        focusedPosition = -1;
                        ((CreateCategoryActivity)context).RefreshList();

                    }
                });

                dialog.create().show();
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

        public Item(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.rowTitleDataEntry);
            brief = itemView.findViewById(R.id.rowBriefDataEntry);
            view = itemView.findViewById(R.id.rowButtonViewDataEntry);
            edit = itemView.findViewById(R.id.rowButtonEditDataEntry);
            delete = itemView.findViewById(R.id.rowButtonDeleteDataEntry);
        }
    }

}
