package com.example.project.Utils.Adaptery;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.example.project.activity_fragments_class.Fragments.PartnersFragment;
import com.example.project.activity_fragments_class.StartActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter_partners extends RecyclerView.Adapter<RecyclerViewAdapter_partners.ViewHolder>{

    private ArrayList<String> mId;
    private ArrayList<String> mWId;
    private ArrayList<String> mName;
    private ArrayList<String> mLongitude;
    private ArrayList<String> mLatitude;
    private ArrayList<String> mDesc;
    private ArrayList<String> mPicture;
    private ArrayList<String> mCity;
    private ArrayList<String> mMultiplier;
    private ArrayList<String> mOwnedPoints;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder implements SearchView.OnQueryTextListener{
        ImageView image;
        TextView imageName, textViewPokazNagrody, textViewSchowajNagrody, textView_ownedPoints, opisPromocji;
        RelativeLayout kafelekPartneraLayoutNagrod, layoutSchowajNagrody, layoutPokazNagrody, relativeLayoutPartnera;
        LinearLayout wariantNagrody;

        public ViewHolder(View itemView) {
            super(itemView);
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

        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            return false;
        }
    }

    public RecyclerViewAdapter_partners(Context mContext, ArrayList<String> mPartners_Id, ArrayList<String> mPartners_Wid, ArrayList<String> mPartners_Name, ArrayList<String> mPartners_Longitude, ArrayList<String> mPartners_Latitude, ArrayList<String> mPartners_Desc, ArrayList<String> mPartners_Picture, ArrayList<String> mPartners_City, ArrayList<String> mPartners_Multiplier, ArrayList<String> mPartners_OwnedPoints ) {
        this.mId = mPartners_Id;
        this.mWId = mPartners_Wid;
        this.mName = mPartners_Name;
        this.mLongitude = mPartners_Longitude;
        this.mLatitude = mPartners_Latitude;
        this.mDesc = mPartners_Desc;
        this.mPicture = mPartners_Picture;
        this.mCity = mPartners_City;
        this.mMultiplier = mPartners_Multiplier;
        this.mOwnedPoints = mPartners_OwnedPoints;
        this.mContext = mContext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_partner, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        PartnersFragment partnersFragment = new PartnersFragment();
        if(!mName.get(position).isEmpty()){
            Log.v("App", "Name: " + mName.get(position) + " pozycja: " + position);
            if(mOwnedPoints.get(position).isEmpty()) {
                Picasso.get()
                        .load(StartActivity.partners_layout_url + mPicture.get(position))
                        .placeholder(R.drawable.error_image)
                        .fit()
                        .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP_LEFT))
                        .into(holder.image);
                holder.imageName.setText(mName.get(position));
                holder.opisPromocji.setText(mDesc.get(position));
                if(mOwnedPoints.get(position).isEmpty()){
                    holder.textView_ownedPoints.setText("0");
                }else{
                    holder.textView_ownedPoints.setText(String.valueOf(mOwnedPoints.get(position)));
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
            } else {
                Log.v("sorting", "Ilosc punktow w pozycji: " + position + " < 0");
            }
        }

    }

    @Override
    public int getItemCount() {
        return mName.size();
    }

}