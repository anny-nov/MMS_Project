package pl.edu.pwr.i269691.project.entity;

public class Comment {
    private String username;
    private String text;
    private Integer rate;

    public Comment(String username, String text, Integer rate) {
        this.username = username;
        this.text = text;
        this.rate = rate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }
}
