package io.github.gdgmiage.sunshine.android.sj.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.github.gdgmiage.sunshine.android.sj.R;
import io.github.gdgmiage.sunshine.android.sj.pojo.DayJson;

/**
 * Created by angebagui on 30/04/2016.
 */
public class ForecastAdapterNew extends RecyclerView.Adapter<ForecastAdapterNew.ViewHolder> {

    private List<DayJson> mItems;
    private static final String TAG =  ForecastAdapterNew.class.getSimpleName();

    public ForecastAdapterNew(List<DayJson> mItems) {
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
        DayJson data = mItems.get(position);
        Log.e(TAG, "onBindViewHolder");
        if (data !=null){
            final long date = data.date;
            final String weatherMain = data.weather.get(0).main;
            final double tempMin = data.temp.min;
            final double tempMax = data.temp.max;
            if(date != 0 && !TextUtils.isEmpty(weatherMain) && tempMin != 0 && tempMax != 0){
                final String weatherDayString = getCorrectDayString(date)+" - "+weatherMain+" - "+(int)tempMin+"/"+(int)tempMax;
                holder.listItemForcastTextView.setText(weatherDayString);
            }

        }


    }

    @Override
    public int getItemCount() {
        if(mItems!=null ){
            return mItems.size();
        }
        return 0;
    }

    public void addItems(List<DayJson> mItems){
        this.mItems = mItems;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
            TextView listItemForcastTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            listItemForcastTextView = (TextView)itemView.findViewById(R.id.list_item_forecast_textview);


        }
    }

    public String getCorrectDayString(long dt){
        Date date = new Date(dt);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

        return "Day";
    }
}
