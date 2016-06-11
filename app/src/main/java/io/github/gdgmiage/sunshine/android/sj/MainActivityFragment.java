package io.github.gdgmiage.sunshine.android.sj;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.github.gdgmiage.sunshine.android.sj.adapter.ForecastAdapter;
import io.github.gdgmiage.sunshine.android.sj.adapter.ForecastAdapterNew;
import io.github.gdgmiage.sunshine.android.sj.database.ForecastDbHelper;
import io.github.gdgmiage.sunshine.android.sj.database.async.ForecastSaveDbTask;
import io.github.gdgmiage.sunshine.android.sj.pojo.DayJson;
import io.github.gdgmiage.sunshine.android.sj.pojo.ForecastJson;
import io.github.gdgmiage.sunshine.android.sj.rest.OpenWeatherMapRest;
import io.github.gdgmiage.sunshine.android.sj.rest.OpenWeatherMapService;
import io.github.gdgmiage.sunshine.android.sj.util.Developer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.COLUMN_NAME_DATE_TIME;
import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.COLUMN_NAME_TEMP_MAX;
import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.COLUMN_NAME_TEMP_MIN;
import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_ID;
import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.COLUMN_NAME_WEATHER_MAIN;
import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.TABLE_NAME;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment  {

    private static final String TAG = MainActivityFragment.class.getSimpleName();
    public static final String PARAM_QUERY = "q";
    public static final String PARAM_MODE = "mode";
    public static final String PARAM_UNITS = "units";
    public static final String PARAM_CNT = "cnt";
    public static final String PARAM_APP_ID = "APPID";
    //private ListView mListView;
    private RecyclerView mRecyclerView;
    //private List<String> weekForecast;
    private List<DayJson> weekForecast;
    //private ForecastAdapter forecastAdapter = null;
    private ForecastAdapterNew forecastAdapter = null;


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
      //weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));
      weekForecast =  new ArrayList<>();

        // Create an adapter
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.list_item_forecast,R.id.list_item_forecast_textview,weekForecast);
         //forecastAdapter = new ForecastAdapter(weekForecast);
         forecastAdapter = new ForecastAdapterNew(weekForecast);
        // Give an adapter to our Listview
       // mListView.setAdapter(adapter);
        mRecyclerView.setAdapter(forecastAdapter);

        // Create an forecast task
        //ForecastTask forecastTask = new ForecastTask(getActivity());
        // run our task
        //forecastTask.execute();
         final Call<ForecastJson> forecastJsonCall = OpenWeatherMapRest.getWeekForecast(getString(R.string.default_location));
        forecastJsonCall.enqueue(new
                                         Callback<ForecastJson>() {
                                             @Override
                                             public void onResponse(Call<ForecastJson> call, Response<ForecastJson> response) {
                                                 Log.d(TAG, "Cool les gars ça passe");
                                                 if (response !=null && response.isSuccessful()){
                                                     weekForecast = response.body().list;
                                                     forecastAdapter.addItems(weekForecast);
                                                     new ForecastSaveDbTask(getActivity()).execute(weekForecast);
                                                 }
                                             }

                                             @Override
                                             public void onFailure(final Call<ForecastJson> call, Throwable t) {
                                                 Log.e(TAG, "Désolé c'est du spagetthi",t);
                                                 if (t instanceof java.net.SocketTimeoutException || t instanceof java.net.ConnectException){
                                                     new AlertDialog.Builder(getActivity())
                                                             .setMessage("Pétit Vérifie ta connexion internet ")
                                                             .setPositiveButton("Reply", new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog, int which) {
                                                                     OpenWeatherMapRest.getWeekForecast(getString(R.string.default_location)).enqueue(new Callback<ForecastJson>() {
                                                                         @Override
                                                                         public void onResponse(Call<ForecastJson> call, Response<ForecastJson> response) {
                                                                             Log.d(TAG, "Cool les gars ça passe");

                                                                             if (response !=null && response.isSuccessful()){
                                                                                 weekForecast = response.body().list;
                                                                                 forecastAdapter.addItems(weekForecast);

                                                                             }
                                                                         }

                                                                         @Override
                                                                         public void onFailure(Call<ForecastJson> call, Throwable t) {

                                                                         }
                                                                     });
                                                                 }
                                                             })
                                                             .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                                 @Override
                                                                 public void onClick(DialogInterface dialog, int which) {

                                                                 }
                                                             })
                                                             .show();

                                                 }

                                             }
                                         });




        return v;
    }

    public void saveAll(List<DayJson> dayJsons){
        ForecastDbHelper mDbHelper = new ForecastDbHelper(getContext());
        SQLiteDatabase db =  mDbHelper.getWritableDatabase();
        DayJson dayJson = null;
        ContentValues values = null;

        Log.w(TAG, "DAYS "+dayJsons);

        if(dayJsons != null && dayJsons.size()>0){
            for(int i=0; i<dayJsons.size(); i++){
                dayJson = dayJsons.get(i);
                values = new ContentValues();
                if(dayJson !=null){
                    if(dayJson.temp !=null){
                        values.put(COLUMN_NAME_TEMP_MAX, dayJson.temp.max);
                        values.put(COLUMN_NAME_TEMP_MIN, dayJson.temp.min);
                    }

                    values.put(COLUMN_NAME_DATE_TIME, dayJson.date);
                    if (dayJson.weather !=null && dayJson.weather.size()>0){
                        if(dayJson.weather.get(0) !=null){
                            values.put(COLUMN_NAME_WEATHER_ID, dayJson.weather.get(0).id);
                            values.put(COLUMN_NAME_WEATHER_MAIN, dayJson.weather.get(0).main);
                        }

                    }

                }

                final long forecastId = db.insert(TABLE_NAME, null, values);
                Log.d(TAG, "Forecast Id"+forecastId);

            }
        }

    }

    public String loadForecastData(){
        //String urlString = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Abidjan&mode=json&units=metric&cnt=7&APPID=67c2b6d72ed43d1fbaf58a6deb9d170c";


        String urlString = new Uri.Builder()
                .encodedPath(Developer.BASE_URL)
                .appendEncodedPath(Developer.DAILY_URL_PATH)
                .appendQueryParameter(PARAM_QUERY, getString(R.string.default_location))
                .appendQueryParameter(PARAM_MODE, getString(R.string.default_mode))
                .appendQueryParameter(PARAM_UNITS, getString(R.string.default_units))
                .appendQueryParameter(PARAM_CNT, getString(R.string.default_day_count))
                .appendQueryParameter(PARAM_APP_ID, getString(R.string.api_key))
                .build().toString();

        StringBuilder builder;
        String forecastData = null;
        BufferedReader bufferedReader = null;

        try {
            final URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream is = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(is));

                builder = new StringBuilder();
                String line ;
                while( (line = bufferedReader.readLine()) !=null){
                    builder.append(line);
                }

                if (builder.length() == 0){
                    forecastData = null;
                }

                forecastData = builder.toString();
                bufferedReader.close();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, "MalformedURLException =====>> O ma tete j'ai eu une erreur Mec ", e);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IOException =====>> Error found ", e);
        }
        return forecastData;

    }

    class ForecastTask extends AsyncTask<Void, Void,ForecastJson> {

        private static final String JSON_COD = "cod" ;
        private static final String JSON_TEMP = "temp";
        private static final String JSON_WEATHER = "weather";
        private static final String JSON_DAY = "day";
        private static final String JSON_TEMP_MAX = "max";
        private static final String JSON_TEMP_MIN = "min";
        private static final String JSON_WEATHER_MAIN = "main";
        private static final String JSON_DATE_TIME = "dt";

        private ProgressDialog progressDialog;

        public ForecastTask(Context context){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Chargement en cours...");
            progressDialog.setIndeterminate(true);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "onPreExecute ==> In ForecastTask I have been called");
            progressDialog.show();
        }

        @Override
        protected ForecastJson doInBackground(Void... params) {
            Log.d(TAG, "doInBackground ==> In ForecastTask ");
            final String jsonResponse = loadForecastData();
            final Gson gson = new Gson();
            return gson.fromJson(jsonResponse,ForecastJson.class);
        }

        @Override
        protected void onPostExecute(ForecastJson response) {
            progressDialog.dismiss();
            Log.d(TAG, "onPostExecute Open Weather Api Response : "+response);

            if(response !=null){

                weekForecast = response.list;
                forecastAdapter.addItems(weekForecast);
                /*
                try {
                    JSONObject forecastJson = new JSONObject(response);

                    if(forecastJson.has("cod")){
                        String cod = forecastJson.getString(JSON_COD);
                        if (cod.equals("200")){
                          if(forecastJson.has("list")){
                                JSONArray listForecast = forecastJson.getJSONArray("list");
                              if (listForecast != null){

                                  JSONObject day = null;
                                  List<String> forecastList = new ArrayList<>();

                                  JSONObject weatherDayJson = null;
                                  JSONObject tempDayJson = null;
                                  String weatherDayString = null;
                                  double tempDay;
                                  double tempMax = 0.0;
                                  double tempMin = 0.0;
                                  String weatherMain = null;
                                  long date  = 0L;
                                  for(int i=0; i< listForecast.length(); i++){
                                    day = listForecast.getJSONObject(i);
                                      if (day !=null){

                                          if (day.has(JSON_DATE_TIME)){
                                              date = day.getLong(JSON_DATE_TIME);
                                          }
                                          if (day.has(JSON_TEMP)){
                                              tempDayJson = day.getJSONObject(JSON_TEMP);
                                          }
                                          if (day.has(JSON_WEATHER)){
                                              weatherDayJson = day.getJSONArray(JSON_WEATHER).getJSONObject(0);
                                          }

                                          if (weatherDayJson != null && tempDayJson != null ){

                                              if (tempDayJson.has(JSON_DAY)){
                                                  tempDay = tempDayJson.getDouble(JSON_DAY);
                                              }

                                              if (tempDayJson.has(JSON_TEMP_MAX)){
                                                  tempMax = tempDayJson.getDouble(JSON_TEMP_MAX);
                                              }
                                              if (tempDayJson.has(JSON_TEMP_MIN)){
                                                  tempMin = tempDayJson.getDouble(JSON_TEMP_MIN);
                                              }

                                              if (weatherDayJson.has(JSON_WEATHER_MAIN)){
                                                  weatherMain = weatherDayJson.getString(JSON_WEATHER_MAIN);
                                              }


                                              weatherDayString = getCorrectDayString(date)+" - "+weatherMain+" - "+(int)tempMin+"/"+(int)tempMax;

                                          }
                                      }



                                      forecastList.add(weatherDayString);

                                  }
                                   weekForecast = forecastList;
                                    forecastAdapter.addItems(weekForecast);


                              }
                            }

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }
    }

    public String getCorrectDayString(long dt){
        Date date = new Date(dt);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("");

        return "Day";
    }

}
