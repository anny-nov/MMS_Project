package pl.edu.pwr.i269691.project.entity;

import com.google.gson.annotations.SerializedName;

public class RatingPlace {

    @SerializedName("place_id")
    private int placeId;

    @SerializedName("rate")
    private int rate;

    @SerializedName("text")
    private String text;

    @SerializedName("username")
    private String username;

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RatingPlace(int placeId, int rate, String text, String username) {
        this.placeId = placeId;
        this.rate = rate;
        this.text = text;
        this.username = username;
    }

    public RatingPlace(int rate, String text, String username) {
        this.rate = rate;
        this.text = text;
        this.username = username;
        placeId = 1;
    }

    @Override
    public String toString() {
        return "RatingPlace{" +
                "place=" + placeId +
                ", rate=" + rate +
                ", text='" + text + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
