package com.ravikhb.saside.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ravikhb.saside.R;
import com.ravikhb.saside.entity.SaltSideItem;
import com.ravikhb.saside.ui.ArticleActivity;

import java.util.List;

/**
 * Created by ravikhb on 08/01/16.
 */
public class SaSideAdapter extends RecyclerView.Adapter<SaSideAdapter.SaSideViewHolder> {

    private Context mContext;
    private List<SaltSideItem> mSaltSideItemList;

    public SaSideAdapter(Context context, List<SaltSideItem> saltSideItemList) {
        mContext = context;
        mSaltSideItemList = saltSideItemList;
    }

    @Override
    public SaSideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new SaSideViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaSideViewHolder holder, int position) {
        holder.mTitle.setText(mSaltSideItemList.get(position).title);
        holder.mDescription.setText(mSaltSideItemList.get(position).description);

        holder.setOnSaSideClickListener(new SaSideViewHolder.OnSaSideClickListener() {
            @Override
            public void OnSaSideClick(View view, int position) {
                Log.i("asdfasd", "position + " + position);
                Intent startIntent = new Intent(mContext, ArticleActivity.class);
                startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startIntent.putExtra("title", mSaltSideItemList.get(position).title);
                startIntent.putExtra("image", mSaltSideItemList.get(position).image);
                startIntent.putExtra("description", mSaltSideItemList.get(position).description);
                mContext.startActivity(startIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mSaltSideItemList != null ) {
            return mSaltSideItemList.size();
        }
        return 0;
    }

    public static class SaSideViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTitle;
        TextView mDescription;
        OnSaSideClickListener clickListener;

        public SaSideViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.title);
            mDescription = (TextView)itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.OnSaSideClick(v, getAdapterPosition());
        }

        public void setOnSaSideClickListener(OnSaSideClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        public interface OnSaSideClickListener {
            void OnSaSideClick(View view, int position);
        }
    }
}
