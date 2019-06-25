package com.example.project.Utils.Adaptery;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.QrCodeGenerator;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.CardModelTest;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter_cards extends RecyclerView.Adapter<RecyclerViewAdapter_cards.ViewHolder> {

    private List<CardModelTest> cardList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout kafelek_karty_gora, kafelek_karty_dol, relativeLayoutKarty;
        TextView textViewNumerKarty, textViewRodzajKarty;
        ImageView imageViewWygladKarty;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayoutKarty = itemView.findViewById(R.id.relativeLayoutKarty);
            kafelek_karty_gora = itemView.findViewById(R.id.relativeLayout_card_upper);
            kafelek_karty_dol = itemView.findViewById(R.id.kafelek_karty_dol);
            textViewNumerKarty = itemView.findViewById(R.id.textViewNumerKarty);
            textViewRodzajKarty = itemView.findViewById(R.id.textViewRodzajKarty);
            imageViewWygladKarty = itemView.findViewById(R.id.imageViewWygladKarty);
        }
    }

    public RecyclerViewAdapter_cards(Context mContext, List<CardModelTest> cardList) {
        this.cardList = cardList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_card, parent, false);
        final ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CardModelTest currentItem = cardList.get(position);

        Picasso.get()
                .load(StartActivity.cards_layout_url +  currentItem.getPic())
                .placeholder(R.drawable.error_image)
                .fit()
                .transform(new picasso_rounded_corners(50,0,  picasso_rounded_corners.CornerType.TOP))
                .into(holder.imageViewWygladKarty);

        holder.textViewNumerKarty.setText("Numer karty: " + currentItem.getNr());

        if(position == 0){
            holder.textViewRodzajKarty.setText("Twoja karta główna");
        }else {
            holder.textViewRodzajKarty.setText("Karta dodatkowa");
        }


        holder.relativeLayoutKarty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog myDialog = new Dialog(mContext);
                myDialog.setContentView(R.layout.cards_popup);
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                final ImageView imageViewPopupKarty = myDialog.findViewById(R.id.imageViewPopupKarty);
                final TextView textViewNumerKartyPopup = myDialog.findViewById(R.id.textViewNumerKartyPopup);
                final TextView textViewTypKartyPopup = myDialog.findViewById(R.id.textViewTypKartyPopup);

                QrCodeGenerator qrCodeGenerator = new QrCodeGenerator();
                imageViewPopupKarty.setImageBitmap(qrCodeGenerator.QrCodeGenerator(currentItem.getNr()));

                textViewNumerKartyPopup.setText("Numer karty: " + currentItem.getNr());

                if(position == 0){
                    textViewTypKartyPopup.setText("Twoja karta główna");
                }else {
                    textViewTypKartyPopup.setText("Karta dodatkowa");
                }

                myDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }


}