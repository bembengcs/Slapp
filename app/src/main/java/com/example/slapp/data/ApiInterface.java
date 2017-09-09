package com.example.slapp.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by bembengcs on 9/4/2017.
 */

public interface ApiInterface {
    @GET("kosakata.json")
    Call<List<KosaKata>> getAllKosaKata();
}
