package com.example.slapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.slapp.R;
import com.example.slapp.data.KosaKata;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bembengcs on 9/4/2017.
 */

public class KataAdapter extends RecyclerView.Adapter<KataAdapter.MyViewHolder> implements Filterable {

    private List<KosaKata> mArrayList = new ArrayList<>();
    private List<KosaKata> mFilteredList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private String TAG = KataAdapter.class.getSimpleName();

    public KataAdapter(List<KosaKata> arrayList, Context context, OnItemClickListener onItemClickListener) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        mContext = context;
        mOnItemClickListener = onItemClickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_video_thumbnail)
        ImageView mIvVideoThumbnail;
        @BindView(R.id.tv_kata)
        TextView mTvKata;
        @BindView(R.id.layout_container)
        CardView mLayoutContainer;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final KosaKata kosaKata) {
            Glide.with(mContext)
                    .load("https://img.youtube.com/vi/" + kosaKata.getLink() + "/0.jpg")
                    .fitCenter()
                    .into(mIvVideoThumbnail);
            mTvKata.setText(kosaKata.getNama().toString().toUpperCase());

            mLayoutContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(kosaKata.getLink());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_video, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(mFilteredList.get(position));
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String link);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    ArrayList<KosaKata> filteredList = new ArrayList<>();
                    for (KosaKata kosaKata : mArrayList) {
                        if (kosaKata.getNama().toLowerCase().contains(charString)) {
                            filteredList.add(kosaKata);
                        }
                    }
                    mFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<KosaKata>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
