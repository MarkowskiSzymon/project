package com.example.project.Utils.Adaptery;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.model.TransactionsModel;

import java.util.List;

public class RecyclerViewAdapter_transactions extends RecyclerView.Adapter<RecyclerViewAdapter_transactions.ViewHolder> {

    private List<TransactionsModel> transactionsList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_layout_history_date, textView_layout_history_name, textView_layout_history_number, textView_layout_history_type, textView_layout_history_amount, textView_layout_history_expense;
        ImageView imageView_layout_history_image;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_layout_history_date = itemView.findViewById(R.id.textView_layout_history_date);
            textView_layout_history_name = itemView.findViewById(R.id.textView_layout_history_name);
            textView_layout_history_number = itemView.findViewById(R.id.textView_layout_history_number);
            textView_layout_history_type = itemView.findViewById(R.id.textView_layout_history_type);
            textView_layout_history_amount = itemView.findViewById(R.id.textView_layout_history_amount);
            textView_layout_history_expense = itemView.findViewById(R.id.textView_layout_history_expense);
            imageView_layout_history_image = itemView.findViewById(R.id.imageView_layout_history_image);
        }
    }


    public RecyclerViewAdapter_transactions(Context context, List<TransactionsModel> transactionsList) {
        this.transactionsList = transactionsList;
        this.mContext = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_layout_history_transaction, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TransactionsModel currentItem = transactionsList.get(position);


        holder.textView_layout_history_date.setText(currentItem.getData());
        holder.textView_layout_history_name.setText(currentItem.getNazwa());
        holder.textView_layout_history_number.setText(currentItem.getNr_karty());
        holder.textView_layout_history_amount.setText(currentItem.getKwota_zakupow());
        holder.textView_layout_history_expense.setText(currentItem.getKoszt_punktow());
        if(currentItem.getTyp_transakcji_id() == "1" ){
            holder.textView_layout_history_type.setText("Dodanie punktów");
        }else{
            holder.textView_layout_history_type.setText("Zamowienie nagrody");
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }


}