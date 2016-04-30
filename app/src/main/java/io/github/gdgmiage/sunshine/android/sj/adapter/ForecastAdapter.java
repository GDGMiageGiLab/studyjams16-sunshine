package io.github.gdgmiage.sunshine.android.sj.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.github.gdgmiage.sunshine.android.sj.R;

/**
 * Created by angebagui on 30/04/2016.
 */
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<String> mItems;
    private static final String TAG =  ForecastAdapter.class.getSimpleName();

    public ForecastAdapter(List<String> mItems) {
        this.mItems = mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_forecast,parent,false);
        Log.e(TAG, "onCreateViewHolder");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String data = mItems.get(position);
        Log.e(TAG, "onBindViewHolder");
        holder.listItemForcastTextView.setText(data);

    }

    @Override
    public int getItemCount() {
        if(mItems!=null ){
            return mItems.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
            TextView listItemForcastTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemForcastTextView = (TextView)itemView.findViewById(R.id.list_item_forecast_textview);


        }
    }
}
