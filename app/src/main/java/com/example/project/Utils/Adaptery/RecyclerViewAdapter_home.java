package com.example.project.Utils.Adaptery;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.Utils.listaPartnerow;
import com.example.project.activity_fragments_class.ExtendedPartnerActivity;
import com.example.project.activity_fragments_class.StartActivity;
import com.example.project.model.MyListData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter_home extends RecyclerView.Adapter<RecyclerViewAdapter_home.ViewHolder> {

    private List<MyListData> partnerList;
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

        public RecyclerViewAdapter_home(Context context, List<MyListData> exampleList) {
            this.partnerList = exampleList;
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
        final MyListData currentItem = partnerList.get(position);

        Picasso.get()
                .load(StartActivity.partners_layout_url + currentItem.getPic())
                .placeholder(R.drawable.error_image)
                .fit()
                .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP_LEFT))
                .into(holder.image);



        holder.imageName.setText(currentItem.getName());
        holder.distanceFromPartner.setText(currentItem.getDistanceToPartner() + " km");
        holder.extendLayoutPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ExtendedPartnerActivity.class);
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
        return partnerList.size();
    }

}