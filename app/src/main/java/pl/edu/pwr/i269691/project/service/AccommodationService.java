package pl.edu.pwr.i269691.project.service;

import java.util.List;

import pl.edu.pwr.i269691.project.entity.Accommodation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AccommodationService {
    @GET("Accommodations")
    Call<List<Accommodation>> all();

    @GET("Accommodations/{id}")
    Call<Accommodation> get(@Path("id") String id);

    @POST("Accommodations/new")
    Call<Accommodation> create(@Body Accommodation Accommodation);
}
