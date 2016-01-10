package com.ravikhb.saside.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ravikhb.saside.R;
import com.ravikhb.saside.entity.SaltSideItem;
import com.ravikhb.saside.ui.HeadlinesFragment;

import java.util.List;

/**
 * Created by ravikhb on 08/01/16.
 */
public class SaSideAdapter extends RecyclerView.Adapter<SaSideAdapter.SaSideViewHolder> {

    private List<SaltSideItem> mSaltSideItemList;
    private OnItemSelectedAdapterListener mFragment;

    public interface OnItemSelectedAdapterListener {
        void onItemSelected(SaltSideItem object);
    }

    public SaSideAdapter(Context context, List<SaltSideItem> saltSideItemList, HeadlinesFragment parent) {
        mSaltSideItemList = saltSideItemList;
        mFragment = parent;
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
        holder.itemView.setSelected(true);
        holder.setOnSaSideClickListener(new SaSideViewHolder.OnSaSideClickViewHolderListener() {
            @Override
            public void OnSaSideClickViewHolder(View view, int position) {
                mFragment.onItemSelected(mSaltSideItemList.get(position));
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
        OnSaSideClickViewHolderListener clickListener;

        public SaSideViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView)itemView.findViewById(R.id.title);
            mDescription = (TextView)itemView.findViewById(R.id.description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.OnSaSideClickViewHolder(v, getAdapterPosition());
        }

        public void setOnSaSideClickListener(OnSaSideClickViewHolderListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        public interface OnSaSideClickViewHolderListener {
            void OnSaSideClickViewHolder(View view, int position);
        }

    }
}
