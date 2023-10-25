package com.umkm.ilhamcollection.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.umkm.ilhamcollection.R;

import java.util.List;

public class UkuranAdapter extends RecyclerView.Adapter<UkuranAdapter.MyViewHolder> {
    private List<String> list;
    private RadioButton lastSelected = null;
    private int lastSelectedPosition = 0;
    private ItemClickListener clickListener;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public RadioButton rb;

        public MyViewHolder(View view) {
            super(view);
            rb = (RadioButton) view.findViewById(R.id.rdCate);
            rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cplcp = lastSelectedPosition;
                    lastSelectedPosition = getAdapterPosition();
                    notifyItemChanged(cplcp);
                    notifyItemChanged(lastSelectedPosition);
                }
            });
        }
    }

    public UkuranAdapter(Context context, List<String> list, ItemClickListener clickListener) {
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cate, parent, false);

        return new MyViewHolder(itemView);
    }

    // Bind data
    @Override
    public void onBindViewHolder(MyViewHolder holder,@SuppressLint("RecyclerView") int position) {

        String result = list.get(position);
        holder.rb.setChecked(position == lastSelectedPosition);
        if (holder.rb.isChecked()){
            holder.rb.setTextColor(ContextCompat.getColor(context,R.color.white));
            holder.rb.setText(result);
            clickListener.onClick(result);
        } else{
            holder.rb.setText(result);
            holder.rb.setTextColor(ContextCompat.getColor(context,R.color.purple_700));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public interface ItemClickListener{

        void onClick(String list);
    }
}

