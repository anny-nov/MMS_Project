package pl.edu.pwr.i269691.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;


public class MapFragment extends Fragment implements GoogleMap.OnInfoWindowClickListener {
    Double latitude = null;
    Double longitude = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arg = getArguments();
        if (arg != null) {
            latitude = this.getArguments().getDouble("Latitude");
            longitude = this.getArguments().getDouble("Longitude");
        }
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_map, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.google_map);

        // Async map
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        googleMap.setMinZoomPreference(6.0f);
                        LatLngBounds veniceBounds = new LatLngBounds(
                                new LatLng(45.437975, 12.293954), // SW bounds
                                new LatLng(45.445335, 12.3913734)  // NE bounds
                        );
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(veniceBounds, 0));

                    }
                });
                if(latitude != null && longitude != null) {
                    LatLng place = new LatLng(latitude, longitude);
                    googleMap.addMarker(new MarkerOptions()
                            .position(place)
                            .title("This place located here")
                    );
                }
                /*LatLng sanMarko = new LatLng(45.434185, 12.337817);
                googleMap.addMarker(new MarkerOptions()
                        .position(sanMarko)
                        .title("Marker in San Marko")
                        .snippet("Piazza San Marco is the city's main square")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                        ));

                LatLng pesaro = new LatLng(45.435331592, 12.322332044);
                googleMap.addMarker(new MarkerOptions()
                        .position(pesaro)
                        .title("Marker in Ca' Pesaro"));

                LatLng rialtoBridge = new LatLng(45.438039, 12.335926);
                googleMap.addMarker(new MarkerOptions()
                        .position(rialtoBridge)
                        .title("Marker in Rialto Bridge"));

                LatLng leonardoMuseum = new LatLng(45.436872, 12.325591);
                googleMap.addMarker(new MarkerOptions()
                        .position(leonardoMuseum)
                        .title("Marker in Leonardo Museum"));

                Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(sanMarko.latitude, sanMarko.longitude),
                                new LatLng(pesaro.latitude, pesaro.longitude),
                                new LatLng(rialtoBridge.latitude, rialtoBridge.longitude),
                                new LatLng(leonardoMuseum.latitude, leonardoMuseum.longitude))); */
            }
        });
        // Return view
        return view;
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {

    }
}