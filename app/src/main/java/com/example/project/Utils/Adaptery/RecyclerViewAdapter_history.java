package com.example.project.Utils.Adaptery;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;

import java.util.ArrayList;

public class RecyclerViewAdapter_history extends RecyclerView.Adapter<RecyclerViewAdapter_history.ViewHolder> {

    private ArrayList<String> mTransactionData;
    private ArrayList<String> mTransactionExpanse;
    private ArrayList<String> mTransactionAmount;
    private ArrayList<String> mTransactionCardNumber;
    private ArrayList<String> mTransactionPicture;
    private ArrayList<String> mTransactionName;
    private ArrayList<String> mTransactionType;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView_layout_history_date, textView_layout_history_name, textView_layout_history_number, textView_layout_history_type, textView_layout_history_amount, textView_layout_history_expanse;
        ImageView imageView_layout_history_image;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_layout_history_date = itemView.findViewById(R.id.textView_layout_history_date);
            textView_layout_history_name = itemView.findViewById(R.id.textView_layout_history_name);
            textView_layout_history_number = itemView.findViewById(R.id.textView_layout_history_number);
            textView_layout_history_type = itemView.findViewById(R.id.textView_layout_history_type);
            textView_layout_history_amount = itemView.findViewById(R.id.textView_layout_history_amount);
            textView_layout_history_expanse = itemView.findViewById(R.id.textView_layout_history_expanse);
            imageView_layout_history_image = itemView.findViewById(R.id.imageView_layout_history_image);
        }
    }


    public RecyclerViewAdapter_history(Context Context, ArrayList<String> transactionData, ArrayList<String> transactionType, ArrayList<String> transactionExpanse, ArrayList<String> transactionAmount, ArrayList<String> transactionCardNumber, ArrayList<String> transactionPicture, ArrayList<String> transactionName) {
        this.mTransactionData = transactionData;
        this.mTransactionExpanse = transactionExpanse;
        this.mTransactionAmount = transactionAmount;
        this.mTransactionCardNumber = transactionCardNumber;
        this.mTransactionType = transactionType;
        this.mTransactionPicture = transactionPicture;
        this.mTransactionName = transactionName;
        this.mContext = Context;
        Log.v("historia", "weszlo");
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
        holder.textView_layout_history_date.setText(mTransactionData.get(position));
        holder.textView_layout_history_name.setText(mTransactionName.get(position));
        holder.textView_layout_history_number.setText(mTransactionCardNumber.get(position));
        holder.textView_layout_history_amount.setText(mTransactionAmount.get(position));
        holder.textView_layout_history_expanse.setText(mTransactionExpanse.get(position));
        if(mTransactionType.get(position) == "1" ){
            holder.textView_layout_history_type.setText("Dodanie punktów");
        }else{
            holder.textView_layout_history_type.setText("Zamowienie nagrody");
        }
    }

    @Override
    public int getItemCount() {
        return mTransactionData.size();
    }


}