package com.flik.flickerimagelist.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flik.flickerimagelist.R;
import com.flik.flickerimagelist.models.Item;
import com.squareup.picasso.Picasso;

import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private List<Item.Item_> itemRowArrayList;
    private Context context;

    public DataAdapter(Context context, List<Item.Item_> itemRowArrayList) {
        this.itemRowArrayList = itemRowArrayList;
        this.context = context;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.tv_android.setText(itemRowArrayList.get(i).getTitle());
        Picasso.with(context).load(itemRowArrayList.get(i).getMedia().getM()).resize(240, 120).into(viewHolder.img_android);
    }

    @Override
    public int getItemCount() {
        return itemRowArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_android;
        private ImageView img_android;

        public ViewHolder(View view) {
            super(view);

            tv_android = (TextView) view.findViewById(R.id.tv_android);
            img_android = (ImageView) view.findViewById(R.id.img_android);
        }
    }

}