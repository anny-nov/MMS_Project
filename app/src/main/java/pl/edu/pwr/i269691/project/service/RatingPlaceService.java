package pl.edu.pwr.i269691.project.service;

import java.util.List;

import pl.edu.pwr.i269691.project.entity.RatingPlace;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RatingPlaceService {

    @POST("RatingPlaces/new")
    Call<RatingPlace> create(@Body RatingPlace ratingPlace);

    @GET("RatingPlaces")
    Call<List<RatingPlace>> all();
}
