package io.github.gdgmiage.sunshine.android.sj.rest;

import io.github.gdgmiage.sunshine.android.sj.MainActivityFragment;
import io.github.gdgmiage.sunshine.android.sj.pojo.ForecastJson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by angebagui on 04/06/2016.
 */
public interface OpenWeatherMapService {

    @GET("forecast/daily")
    Call<ForecastJson> loadDailyForecast(
            @Query(MainActivityFragment.PARAM_QUERY)String q,
            @Query(MainActivityFragment.PARAM_MODE)String mode,
            @Query(MainActivityFragment.PARAM_UNITS)String units,
            @Query(MainActivityFragment.PARAM_CNT)int cnt,
            @Query(MainActivityFragment.PARAM_APP_ID) String appId
            );
}
