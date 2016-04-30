package io.github.gdgmiage.sunshine.android.sj;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.gdgmiage.sunshine.android.sj.adapter.ForecastAdapter;
import io.github.gdgmiage.sunshine.android.sj.model.Book;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  {

    //private ListView mListView;
    private RecyclerView mRecyclerView;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_main_2, parent,false);
        //mListView = (ListView)v.findViewById(R.id.listview_forecast);

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerview_forecast);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create our fake Data
        String [] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Sunny - 88/63",
                "Weds - Sunny - 88/63",
                "Fri - Sunny - 88/63",
                "Sat - Sunny - 88/63",
                "Sun - Sunny - 88/63"};
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        // Create an adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast,R.id.list_item_forecast_textview,weekForecast);
        ForecastAdapter forecastAdapter = new ForecastAdapter(weekForecast);
        // Give an adapter to our Listview
       // mListView.setAdapter(adapter);
        mRecyclerView.setAdapter(forecastAdapter);
        return v;
    }

}
