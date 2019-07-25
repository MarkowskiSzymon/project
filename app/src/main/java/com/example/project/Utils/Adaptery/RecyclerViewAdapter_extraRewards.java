package com.example.project.Utils.Adaptery;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.PartnersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IllegalFormatCodePointException;
import java.util.List;


public class RecyclerViewAdapter_extraRewards extends RecyclerView.Adapter<RecyclerViewAdapter_extraRewards.ViewHolder> implements Filterable {

    private List<PartnersModel> partnerList;
    private List<PartnersModel> exampleListFull;
    private List<String> nid = new ArrayList<>();
    private List<String> descOfPromotion = new ArrayList<>();
    private List<String> pointsForPromotion = new ArrayList<>();
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView partnerLogo;
        TextView partnerName, textViewPokazNagrody, textViewSchowajNagrody, textView_ownedPoints, opisPromocji, distanceFromPartner;
        RelativeLayout kafelekPartneraLayoutNagrod, layoutSchowajNagrody, layoutPokazNagrody, relativeLayoutPartnera, extendLayoutPartner;
        LinearLayout wariantNagrody;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            distanceFromPartner = itemView.findViewById(R.id.textView_viewLayoutExtraReward_distanceFromPartner);
            recyclerView = itemView.findViewById(R.id.recyclerView_fragmentExtraRewardList);
            relativeLayoutPartnera = itemView.findViewById(R.id.relativeLayout_viewLayoutExtraReward_fullCardLayout);
            wariantNagrody = itemView.findViewById(R.id.linearLayout_viewLayoutExtraReward_listOfRewards);
            textView_ownedPoints = itemView.findViewById(R.id.textView_viewLayoutExtraReward_ownedPoints);
            opisPromocji = itemView.findViewById(R.id.textView_viewLayoutExtendedDiscPartner_desc);
            partnerLogo = itemView.findViewById(R.id.imageView_viewLayoutExtraReward_logo);
            partnerName = itemView.findViewById(R.id.textView_viewLayoutExtraReward_partnerName);
            kafelekPartneraLayoutNagrod = itemView.findViewById(R.id.relativeLayout_viewLayoutExtraReward_listOfRewards);
        }
    }

    public RecyclerViewAdapter_extraRewards(Context context, List<PartnersModel> exampleList) {
        this.partnerList = exampleList;
        this.mContext = context;
        exampleListFull = new ArrayList<>(exampleList);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_extra_reward, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        PartnersModel currentItem = partnerList.get(position);
        holder.wariantNagrody.setOrientation(LinearLayout.VERTICAL);
        holder.partnerName.setText(currentItem.getName());

        if(currentItem.getIlosc_pkt().equals("") || currentItem.getIlosc_pkt().isEmpty()){
            holder.textView_ownedPoints.setText("0");
        }else{
            holder.textView_ownedPoints.setText(currentItem.getIlosc_pkt());
        }

        nid.clear();
        descOfPromotion.clear();
        pointsForPromotion.clear();

        for(int i=0; i < currentItem.listOfReward.size(); i++) {
            if (currentItem.listOfReward.get(i).getBid().equals(currentItem.bid)) {
                if((Integer.parseInt(currentItem.listOfReward.get(i).getPoints()) <= Integer.parseInt(String.valueOf(holder.textView_ownedPoints.getText())))){
                    nid.add(currentItem.listOfReward.get(i).getBid());
                    descOfPromotion.add(currentItem.listOfReward.get(i).getName());
                    pointsForPromotion.add(currentItem.listOfReward.get(i).getPoints());
                }
            }
        }

            Picasso.get()
                    .load(StartActivity.partners_layout_url + currentItem.getPic())
                    .placeholder(R.drawable.error_image)
                    .fit()
                    .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP))
                    .into(holder.partnerLogo);

            if (currentItem.getIlosc_pkt().isEmpty() || Integer.parseInt(currentItem.getIlosc_pkt()) == 0) {
                holder.textView_ownedPoints.setText("0");

            } else {
                holder.textView_ownedPoints.setText(currentItem.getIlosc_pkt());
            }


            holder.wariantNagrody.removeAllViews();

        for (int i = 0; i < descOfPromotion.size(); i++) {
            View child1 = LayoutInflater.from(mContext).inflate(R.layout.view_layout_extended_description_partner, null);
            TextView promotionPoints = child1.findViewById(R.id.textView_viewLayoutExtendedDiscPartner_points);
            TextView promotionDesc = child1.findViewById(R.id.textView_viewLayoutExtendedDiscPartner_desc);
            promotionPoints.setText(pointsForPromotion.get(i));
            promotionDesc.setText(descOfPromotion.get(i));
            holder.wariantNagrody.addView(child1);
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