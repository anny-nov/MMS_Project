package pl.edu.pwr.i269691.project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Tour {


    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    private ArrayList<Place> places = new ArrayList<Place>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tour(int id, String name, String description,ArrayList<Place> places) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.places=places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tour tour = (Tour) o;
        return id == tour.id && Objects.equals(name, tour.name) && Objects.equals(description, tour.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    public Tour() {
    }

    public Tour(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
