package io.github.gdgmiage.sunshine.android.sj.database.async;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import static io.github.gdgmiage.sunshine.android.sj.database.ForecastContract.ForecastEntry.*;

import io.github.gdgmiage.sunshine.android.sj.database.ForecastDbHelper;
import io.github.gdgmiage.sunshine.android.sj.pojo.DayJson;
import io.github.gdgmiage.sunshine.android.sj.pojo.ForecastJson;

/**
 * Created by angebagui on 11/06/2016.
 */
public class ForecastSaveDbTask extends AsyncTask<List<DayJson>, Void, Void> {


    private static final String TAG = ForecastSaveDbTask.class.getSimpleName();
    private Context mContext;


    public ForecastSaveDbTask(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected Void doInBackground(List<DayJson>... params) {
        ForecastDbHelper mDbHelper = new ForecastDbHelper(mContext);
       SQLiteDatabase db =  mDbHelper.getWritableDatabase();

        List<DayJson> dayJsons = params[0];
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



        return null;
    }

}
