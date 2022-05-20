package pl.edu.pwr.i269691.project.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;

public class Event {

    @SerializedName("id")
    private int id;

    @SerializedName("description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    private String name;
    
    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("longitude")
    private Double longitude;

    @SerializedName("adress")
    private String address;

    private ArrayList<String> img= new ArrayList<String>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Event(int id) {
        this.id = id;
    }

    public Event(int id,String name, String description, Double latitude, Double longitude, String address,ArrayList<String> img) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.img=img;
    }

    public Event(int id, String name, ArrayList<String> imgs) {
        this.id = id;
        this.name = name;
        this.img=imgs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && Objects.equals(description, event.description) && Objects.equals(latitude, event.latitude) && Objects.equals(longitude, event.longitude) && Objects.equals(address, event.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, latitude, longitude, address);
    }
}
