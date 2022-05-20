package pl.edu.pwr.i269691.project.service;

import java.util.List;

import pl.edu.pwr.i269691.project.entity.Place;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PlaceService {
    @GET("places")
    Call<List<Place>> all();

    @GET("places/{id}")
    Call<Place> get(@Path("id") int id);

    @POST("places/new")
    Call<Place> create(@Body Place Place);
}
