package io.github.gdgmiage.sunshine.android.sj.database;

import android.provider.BaseColumns;

/**
 * Created by angebagui on 11/06/2016.
 */
public final class ForecastContract {

    public ForecastContract() {
    }

    public static abstract class ForecastEntry implements BaseColumns {

        public static final String TABLE_NAME = "forecast";
        public static final String COLUMN_NAME_TEMP_MAX = "temp_max";
        public static final String COLUMN_NAME_TEMP_MIN = "temp_min";
        public static final String COLUMN_NAME_DATE_TIME = "date_time";
        public static final String COLUMN_NAME_WEATHER_ID = "weather_id";
        public static final String COLUMN_NAME_WEATHER_MAIN = "weather_main";
    }
}
