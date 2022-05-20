package pl.edu.pwr.i269691.project.service;

import java.util.List;

import pl.edu.pwr.i269691.project.entity.Event;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {
    @GET("events")
    Call<List<Event>> all();

    @GET("events/{id}")
    Call<Event> get(@Path("id") int id);

    @POST("event/new")
    Call<Event> create(@Body Event event);
}
