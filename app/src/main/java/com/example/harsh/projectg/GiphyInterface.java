package com.example.harsh.projectg;

import com.example.harsh.projectg.models.GiphyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by harsh on 09/12/16.
 */
public interface GiphyInterface {
    @GET("trending")
    Call<GiphyResponse> getTrending(@Query("api_key") String api_key);

    @GET("search")
    Call<GiphyResponse> searchGifs(@Query("api_key") String api_key, @Query("q") String search_keywords);


}