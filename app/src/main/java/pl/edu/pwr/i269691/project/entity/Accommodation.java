package pl.edu.pwr.i269691.project.entity;

import java.util.ArrayList;
import java.util.Objects;

public class Accommodation {

    private int id;
    private String name;
    private String description;
    private Float rating;
    private Double latitude;
    private Double longitude;
    private String address;
    private ArrayList<Comment> comment = new ArrayList<Comment>();
    private ArrayList<String> img= new ArrayList<String>();

    public void CountRating()
    {
        Integer sum = 0;
        for (Comment com: comment
        ) { sum += com.getRate(); }
        rating = (float) sum/comment.size();
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
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

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    public Accommodation(int id, String name, String description, float rating, Double latitude, Double longitude, String address,ArrayList<String> imgs,ArrayList<Comment> comms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.img=imgs;
        this.comment=comms;
    }

    public Accommodation(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accommodation that = (Accommodation) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(rating, that.rating) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, rating, latitude, longitude, address);
    }

    public Accommodation(int id, String name, ArrayList<String> imgs) {
        this.id = id;
        this.name = name;
        this.img=imgs;
    }
}
