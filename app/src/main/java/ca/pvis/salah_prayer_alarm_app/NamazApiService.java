package ca.pvis.salah_prayer_alarm_app;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NamazApiService {
    @GET("v1/timings")
    Call<NamazResponse> getNamazTimes(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            @Query("method") int method
    );
}
