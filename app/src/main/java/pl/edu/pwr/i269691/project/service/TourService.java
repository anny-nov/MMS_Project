package pl.edu.pwr.i269691.project.service;

import java.util.List;

import pl.edu.pwr.i269691.project.entity.Tour;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TourService {
    @GET("Tours")
    Call<List<Tour>> all();

    @GET("Tours/{id}")
    Call<Tour> get(@Path("id") String id);

    @POST("Tours/new")
    Call<Tour> create(@Body Tour Tour);
}
