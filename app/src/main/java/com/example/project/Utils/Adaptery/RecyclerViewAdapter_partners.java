package com.example.project.Utils.Adaptery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.listaPartnerow;
import com.example.project.activity_fragments_class.ExtendedPartnerActivity;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.PartnersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RecyclerViewAdapter_partners extends RecyclerView.Adapter<RecyclerViewAdapter_partners.ViewHolder> implements Filterable {

    private List<PartnersModel> partnerList;
    private List<PartnersModel> exampleListFull;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView nameOfPartner, textViewPokazNagrody, textViewSchowajNagrody, textView_ownedPoints, opisPromocji, distanceFromPartner;
        RelativeLayout kafelekPartneraLayoutNagrod, layoutSchowajNagrody, layoutPokazNagrody, relativeLayoutPartnera, extendLayoutPartner;
        LinearLayout wariantNagrody;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            extendLayoutPartner = itemView.findViewById(R.id.relativeLayout_viewLayoutPartner_upLayout);
            distanceFromPartner = itemView.findViewById(R.id.textView_viewLayoutPartner_distanceFromPartner);
            recyclerView = itemView.findViewById(R.id.recyclerView_fragmentHome);
            relativeLayoutPartnera = itemView.findViewById(R.id.relativeLayout_viewLayoutPartner_fullPartnerView);
            textView_ownedPoints = itemView.findViewById(R.id.textView_viewLayoutPartner_ownedPoints);
            opisPromocji = itemView.findViewById(R.id.textView_viewLayoutExtendedDiscPartner_desc);
            image = itemView.findViewById(R.id.imageView_viewLayoutPartner_logo);
            nameOfPartner = itemView.findViewById(R.id.textView_viewLayoutPartner_partnerName);
            textViewSchowajNagrody = itemView.findViewById(R.id.textViewSchowajNagrody);
        }
    }

    public RecyclerViewAdapter_partners(Context context, List<PartnersModel> exampleList) {
        this.partnerList = exampleList;
        this.mContext = context;
        exampleListFull = new ArrayList<>(exampleList);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_partner, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PartnersModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (PartnersModel item : exampleListFull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            partnerList.clear();
            partnerList.addAll((Collection<? extends PartnersModel>) results.values);
            notifyDataSetChanged();
        }
    };
}
