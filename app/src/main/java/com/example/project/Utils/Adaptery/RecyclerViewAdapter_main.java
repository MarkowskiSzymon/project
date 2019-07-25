package com.example.project.Utils.Adaptery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.activity_fragments_class.ExtendedPartnerActivity;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.PartnersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_main extends RecyclerView.Adapter<RecyclerViewAdapter_main.ViewHolder> {

    private List<PartnersModel> partnerList;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView nameOfPartner, textView_ownedPoints, distanceFromPartner;
        RelativeLayout relativeLayoutPartnera, extendLayoutPartner;
        LinearLayout wariantNagrody;
        RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);

            extendLayoutPartner = itemView.findViewById(R.id.relativeLayout_viewLayoutPartner_upLayout);
            distanceFromPartner = itemView.findViewById(R.id.textView_viewLayoutPartner_distanceFromPartner);
            recyclerView = itemView.findViewById(R.id.recyclerView_fragmentHome);
            relativeLayoutPartnera = itemView.findViewById(R.id.relativeLayout_viewLayoutPartner_fullPartnerView);
            textView_ownedPoints = itemView.findViewById(R.id.textView_viewLayoutPartner_ownedPoints);
            image = itemView.findViewById(R.id.imageView_viewLayoutPartner_logo);
            nameOfPartner = itemView.findViewById(R.id.textView_viewLayoutPartner_partnerName);
        }
    }

    public RecyclerViewAdapter_main(Context context, List<PartnersModel> partnerList) {
        this.partnerList = partnerList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_partner, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PartnersModel currentItem = partnerList.get(position);

        Picasso.get()
                .load(StartActivity.partners_layout_url + currentItem.getPic())
                .placeholder(R.drawable.error_image)
                .centerCrop()
                .fit()
                .centerCrop()
                .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP))
                .into(holder.image);

        holder.nameOfPartner.setText(currentItem.getName());
        holder.distanceFromPartner.setText(currentItem.getDistanceToPartner() + " km");
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExtendedPartnerActivity.class);
                intent.putExtra("position", String.valueOf(position));
               // ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.image, ViewCompat.getTransitionName(holder.image));
               // Bundle bundle = options.toBundle();
               // mContext.startActivity(intent, bundle);
                mContext.startActivity(intent);
            }
        });

        if(currentItem.getIlosc_pkt().equals("")){
            holder.textView_ownedPoints.setText("0 pkt");
        } else{
            holder.textView_ownedPoints.setText(currentItem.getIlosc_pkt() + " pkt");
        }
    }

    @Override
    public int getItemCount() {
        return partnerList.size();
    }

}