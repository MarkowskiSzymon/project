package com.example.project.Utils.Adaptery;



import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.project.R;
import com.example.project.Utils.listaPartnerow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter_home extends RecyclerView.Adapter<RecyclerViewAdapter_home.ViewHolder>{

    private ArrayList<String> mImagesNames;
    private ArrayList<String> mImagesUrl;
    private ArrayList<Integer> mPunkty;
    private ArrayList<String> mOpisy;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView imageName, textViewPokazNagrody, textViewSchowajNagrody, iloscPunktow, opisPromocji;
        RelativeLayout kafelekPartneraLayoutNagrod, layoutSchowajNagrody, layoutPokazNagrody, relativeLayoutPartnera;
        LinearLayout wariantNagrody;
        RecyclerView recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.recycler_view_fragment_home);
            layoutSchowajNagrody = itemView.findViewById(R.id.layout_tekstu_schowaj_nagrode);
            layoutPokazNagrody = itemView.findViewById(R.id.layout_tekstu_pokaz_nagrode);
            relativeLayoutPartnera = itemView.findViewById(R.id.relativeLayoutPartnera);
            wariantNagrody = itemView.findViewById(R.id.wariantNagrody);
            iloscPunktow = itemView.findViewById(R.id.iloscPunktow);
            opisPromocji = itemView.findViewById(R.id.opisPromcji);
            image = itemView.findViewById(R.id.zdjecie);
            imageName = itemView.findViewById(R.id.zdjecieTextView);
            textViewPokazNagrody = itemView.findViewById(R.id.textViewPokazNagrody);
            textViewSchowajNagrody = itemView.findViewById(R.id.textViewSchowajNagrody);
            kafelekPartneraLayoutNagrod = itemView.findViewById(R.id.kafelekPartneraLayoutNagrod);
        }
    }

        public RecyclerViewAdapter_home(Context mContext, ArrayList<String> mImagesNames, ArrayList<String> mImagesUrl, ArrayList<String> mOpisy, ArrayList<Integer> mPunkty) {
        this.mImagesNames = mImagesNames;
        this.mImagesUrl = mImagesUrl;
        this.mOpisy = mOpisy;
        this.mPunkty = mPunkty;
        this.mContext = mContext;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_layout_partner, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {



            if(mPunkty.get(position) > 0) {
                Log.v("sorting", "pozycja: " + position);
                Picasso.get()
                        .load(mImagesUrl.get(position))
                        .placeholder(R.drawable.error_image)
                        .fit()
                        .transform(new picasso_rounded_corners(50, 0, picasso_rounded_corners.CornerType.TOP_LEFT))
                        .into(holder.image);
                holder.imageName.setText(mImagesNames.get(position));
                holder.opisPromocji.setText(mOpisy.get(position));
                holder.iloscPunktow.setText(String.valueOf(mPunkty.get(position)));
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

    @Override
    public int getItemCount() {
        return mImagesNames.size();
    }

}