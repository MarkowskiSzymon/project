package com.example.project.Utils.Adaptery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
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
import com.example.project.Utils.listaPartnerow;
import com.example.project.activity_fragments_class.CreatePasswordActivity;
import com.example.project.activity_fragments_class.ExtenderPartnerActivity;
import com.example.project.activity_fragments_class.LoginActivity;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.MyListData;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class RecyclerViewAdapter_rewards extends RecyclerView.Adapter<RecyclerViewAdapter_rewards.ViewHolder> implements Filterable {

    private List<MyListData> exampleList;
    private List<MyListData> exampleListFull;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageName, textViewPokazNagrody, textViewSchowajNagrody, textView_ownedPoints, opisPromocji, distanceFromPartner;
        RelativeLayout kafelekPartneraLayoutNagrod, layoutSchowajNagrody, layoutPokazNagrody, relativeLayoutPartnera, extendLayoutPartner;
        LinearLayout wariantNagrody;
        RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);
            extendLayoutPartner = itemView.findViewById(R.id.relativeLayout_viewLayoutPartner_upLayout);
            distanceFromPartner = itemView.findViewById(R.id.textView_partnerLayout_distanceFromPartner);
            recyclerView = itemView.findViewById(R.id.recycler_view_fragment_home);
            layoutSchowajNagrody = itemView.findViewById(R.id.layout_tekstu_schowaj_nagrode);
            layoutPokazNagrody = itemView.findViewById(R.id.layout_tekstu_pokaz_nagrode);
            relativeLayoutPartnera = itemView.findViewById(R.id.relativeLayoutPartnera);
            wariantNagrody = itemView.findViewById(R.id.wariantNagrody);
            textView_ownedPoints = itemView.findViewById(R.id.textView_layoutPartner_ownedPoints);
            opisPromocji = itemView.findViewById(R.id.opisPromcji);
            image = itemView.findViewById(R.id.zdjecie);
            imageName = itemView.findViewById(R.id.zdjecieTextView);
            textViewPokazNagrody = itemView.findViewById(R.id.textViewPokazNagrody);
            textViewSchowajNagrody = itemView.findViewById(R.id.textViewSchowajNagrody);
            kafelekPartneraLayoutNagrod = itemView.findViewById(R.id.kafelekPartneraLayoutNagrod);
        }
    }

    public RecyclerViewAdapter_rewards(Context context, List<MyListData> exampleList) {
        this.exampleList = exampleList;
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final MyListData currentItem = exampleList.get(position);

            Picasso.get()
                    .load(StartActivity.partners_layout_url + currentItem.getPic())
                    .placeholder(R.drawable.error_image)
                    .fit()
                    .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP_LEFT))
                    .into(holder.image);



            holder.imageName.setText(currentItem.getNazwa());
            holder.distanceFromPartner.setText(currentItem.getDistanceToPartner() + " km");
            holder.extendLayoutPartner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ExtenderPartnerActivity.class);
                    intent.putExtra("partnerLogo", currentItem.getPic());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, holder.image, ViewCompat.getTransitionName(holder.image));

                    mContext.startActivity(intent, options.toBundle());
                }
            });

            // Owned points in partner's shop
            if(currentItem.getIlosc_pkt().equals("")){
                holder.textView_ownedPoints.setText("0 pkt");
            }else{
                holder.textView_ownedPoints.setText(currentItem.getIlosc_pkt() + " pkt");
            }




            holder.wariantNagrody.removeAllViews();
            holder.wariantNagrody.setOrientation(LinearLayout.VERTICAL);

            holder.textViewPokazNagrody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listaPartnerow listaPartnerow = new listaPartnerow();
                    Log.v("App", String.valueOf(listaPartnerow.listaOpisow.size()));
                    holder.wariantNagrody.setVisibility(View.VISIBLE);
                    holder.wariantNagrody.removeAllViews();
                    for (int i = 0; i < 4; i++) {
                        View child1 = LayoutInflater.from(mContext).inflate(R.layout.view_layout_extended_description_partner, null);
                        holder.wariantNagrody.addView(child1);
                    }
                    holder.layoutPokazNagrody.setVisibility(View.GONE);
                    holder.layoutSchowajNagrody.setVisibility(View.VISIBLE);
                }

            });

            holder.textViewSchowajNagrody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.layoutPokazNagrody.setVisibility(View.VISIBLE);
                    holder.wariantNagrody.setVisibility(View.GONE);
                    holder.layoutSchowajNagrody.setVisibility(View.GONE);
                }
            });
        }



    @Override
    public int getItemCount() {
        return exampleList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MyListData> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MyListData item : exampleListFull) {
                    if (item.getNazwa().toLowerCase().contains(filterPattern)) {
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
            exampleList.clear();
            exampleList.addAll((Collection<? extends MyListData>) results.values);
            notifyDataSetChanged();
        }
    };
}
