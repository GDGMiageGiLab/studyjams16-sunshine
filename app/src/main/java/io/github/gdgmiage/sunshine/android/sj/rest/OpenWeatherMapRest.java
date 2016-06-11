package io.github.gdgmiage.sunshine.android.sj.rest;

import io.github.gdgmiage.sunshine.android.sj.pojo.ForecastJson;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by angebagui on 04/06/2016.
 */
public class OpenWeatherMapRest {

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String APP_ID = "67c2b6d72ed43d1fbaf58a6deb9d170c";
    private static final String DEFAULT_MODE ="json";
    private static final String DEFAULT_UNITS = "metric";
    private static final int WEEK_CNT = 7;

    private static OpenWeatherMapService service;

    private static OpenWeatherMapService get(){
        if(service==null){
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(OpenWeatherMapService.class);
        }
        return service;
    }

    public static Call<ForecastJson> getForecastDaily( String query,
                                                          String mode,
                                                          String units,
                                                          int cnt,
                                                          String appId){
        return get().loadDailyForecast(query,mode,units,cnt,appId);

    }
    public static Call<ForecastJson> getForecastDaily( String query,
                                                       String mode,
                                                       String units,
                                                       int cnt){
        return get().loadDailyForecast(query,mode,units,cnt,APP_ID);

    }
    public static Call<ForecastJson> getWeekForecast( String query,
                                                       String units
                                                       ){
        return get().loadDailyForecast(query,DEFAULT_MODE,units,WEEK_CNT,APP_ID);

    }
    public static Call<ForecastJson> getWeekForecast(String query)
    {
        return get().loadDailyForecast(query,DEFAULT_MODE,DEFAULT_UNITS,WEEK_CNT,APP_ID);

    }
}
