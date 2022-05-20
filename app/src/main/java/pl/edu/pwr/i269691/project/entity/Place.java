package pl.edu.pwr.i269691.project.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Place {

    @SerializedName("id")
    public int id;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("audio")
    public String audio;

    @SerializedName("rating")
    public Float rating;

    @SerializedName("latitude")
    public Double latitude;

    @SerializedName("longitude")
    public Double longitude;

    @SerializedName("address")
    public String address;

    private ArrayList<String> img= new ArrayList<String>();
    private ArrayList<String> video= new ArrayList<String>();
    private ArrayList<Comment> comment = new ArrayList<Comment>();

    public void CountRating()
    {
        Integer sum = 0;
        for (Comment com: comment
             ) { sum += com.getRate(); }
        rating = (float) sum/comment.size();
    }

    public String getAddress() {
        return address;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public ArrayList<String> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<String> video) {
        this.video = video;
    }

    public ArrayList<Comment> getComment() {
        return comment;
    }

    public void setComment(ArrayList<Comment> comment) {
        this.comment = comment;
    }

    public Place(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAudio() {
        return audio;
    }

    public float getRating() {
        return rating;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getaddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Place(int id, String name, String description, String audio, Float rating, Double latitude, Double longitude, String address, ArrayList<String> imgs,ArrayList<String> videos,ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.audio = audio;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.img=imgs;
        this.video=videos;
        this.comment=comments;
    }
    public Place(int id, String name, ArrayList<String> imgs) {
        this.id = id;
        this.name = name;
        this.img=imgs;
    }

    public Place(int id, String name, Double latitude, Double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
