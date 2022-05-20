package pl.edu.pwr.i269691.project;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Accommodation;
import pl.edu.pwr.i269691.project.entity.Comment;
import pl.edu.pwr.i269691.project.entity.Event;
import pl.edu.pwr.i269691.project.entity.Place;
import pl.edu.pwr.i269691.project.entity.RatingPlace;
import pl.edu.pwr.i269691.project.entity.Tour;

public class Connector {
    private static String ip = "ec2-54-75-184-144.eu-west-1.compute.amazonaws.com";// this is the host ip that your data base exists on you can use 10.0.2.2 for local host                                                    found on your pc. use if config for windows to find the ip if the database exists on                                                    your pc
    private static String port = "5432";// the port sql server runs on
    private static String Classes = "org.postgresql.Driver";// the driver that is required for this connection use                                                                           "org.postgresql.Driver" for connecting to postgresql
    private static String database = "d454bbg7ads118";// the data base name
    private static String username = "lutunzuaeboqfj";// the user name
    private static String password = "e4978433d9db74ff861f9a02934779f235739df30577cfca8ff7fd5662d70d41";// the password
    private static String url =  "jdbc:postgresql://"+ip+":"+port+"/"+database; // the connection url string

    private Connection connection = null;

    public void Connect(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url, username,password);
            Log.i("Connection","Connected");

        } catch (ClassNotFoundException e) {
        e.printStackTrace();
            Log.i("Connection","Class ");
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
    }

    public ArrayList<Place> Get_Places(){
        ArrayList<Place> places = new ArrayList<Place>();
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectPlace = "select * from places";
            ResultSet rs = st.executeQuery(selectPlace);
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                ArrayList<String> imgs = new ArrayList<String>();
                String selectimg = "SELECT link from Media_Places WHERE TYPE='0' AND id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                    String img = rs2.getString("LINK");
                    imgs.add(img);
                }
                Place place = new Place(id,name,imgs);
                places.add(place);
                Log.i("Number","!!!Number of elements in Connector is "+places.size());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return places;
    }

    public Place Get_Place_by_ID(int id){
        Place place1 = new Place(id);
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectPlace = "select * from places WHERE id="+id;
            ResultSet rs = st.executeQuery(selectPlace);

            while (rs.next()) {
                int id1 = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                Log.i("Name",name);
                String description = rs.getString("DESCRIPTION");
                String audio = rs.getString("AUDIO");
                Float rating = Float.parseFloat(rs.getString("RATING"));
                Double latitude = Double.parseDouble(rs.getString("LATITUDE"));
                Double longitude = Double.parseDouble(rs.getString("LONGITUDE"));
                String address = rs.getString("ADDRESS");
                ArrayList<String> imgs = new ArrayList<String>();
                ArrayList<String> videos = new ArrayList<String>();
                String selectimg = "SELECT link,type from Media_Places WHERE id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                    String type = rs2.getString("TYPE");
                    Log.i("Type","Type is "+ type);
                    if(type.equals("0")) {
                        String img = rs2.getString("LINK");
                        imgs.add(img);
                    }
                    else
                    {
                        String vid = rs2.getString("LINK");
                        videos.add(vid);
                    }
                }
                ArrayList<Comment> comms = new ArrayList<Comment>();
                String select_comments = "SELECT * from Rating_Places WHERE place_id="+id;
                rs2 = st2.executeQuery(select_comments);
                while (rs2.next())
                {
                    String username = rs2.getString("USERNAME");
                    String text = rs2.getString("Text");
                    Integer rate = Integer.parseInt(rs2.getString("Rate"));
                    Comment tmp = new Comment(username,text,rate);
                    comms.add(tmp);
                }
                Place place = new Place(id1,name,description,audio,rating,latitude,longitude,address,imgs,videos,comms);
                Log.i("Log","Name in Connector is "+ place.getName());
                return place;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return place1;
    }

    public ArrayList<Event> Get_Events(){
        ArrayList<Event> events = new ArrayList<Event>();
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectEvent = "select * from events";
            ResultSet rs = st.executeQuery(selectEvent);
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                ArrayList<String> imgs = new ArrayList<String>();
                String selectimg = "SELECT link from Media_Events WHERE id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                    String img = rs2.getString("LINK");
                    imgs.add(img);
                }
                Event event = new Event(id,name,imgs);
                events.add(event);
            }
            Log.i("Count1","!!!Number of elements is "+events.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return events;
    }

    public ArrayList<Accommodation> Get_Accomodations(){
        ArrayList<Accommodation> accs = new ArrayList<Accommodation>();
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectAcc = "select * from Accommodation";
            ResultSet rs = st.executeQuery(selectAcc);
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                ArrayList<String> imgs = new ArrayList<String>();
                String selectimg = "SELECT link from Media_Acc WHERE id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                    String img = rs2.getString("LINK");
                    imgs.add(img);
                }
                Accommodation acc = new Accommodation(id,name,imgs);
                accs.add(acc);
            }
            Log.i("Count2","!!!Number of elements is "+accs.size());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accs;
    }

    public void Post_RatingPlace(String username, String comment, int rating,int place_id) {
        try {
            Statement st = connection.createStatement();
            String sqlInsert = "insert into rating_places(place_id,rate,text,username) values ("+place_id + ", " + rating + ", '" + comment + "', '" + username + "')";
            st.executeQuery(sqlInsert);
            Log.e("Insert","Comment was inserted!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Insert",throwables.getMessage());
        }
    }

    public void Post_RatingAcc(String username, String comment, int rating,int id_acc) {
        try {
            Statement st = connection.createStatement();
            String sqlInsert = "insert into rating_acc(id_acc,rate,text,username) values ("+id_acc + ", " + rating + ", '" + comment + "', '" + username + "')";
            st.executeQuery(sqlInsert);
            Log.e("Insert","Comment was inserted!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            Log.e("Insert",throwables.getMessage());
        }
    }

    public ArrayList<Place> Get_CityTourPlaces(){
        ArrayList<Place> places = new ArrayList<Place>();
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectPlace = "SELECT p.id,p.name,p.latitude,p.longitude " +
                    "FROM tour as t " +
                    "INNER JOIN tour_list as tl " +
                    "ON t.id = tl.id " +
                    "INNER JOIN places as p " +
                    "ON tl.place_id = p.id " +
                    "WHERE t.id = 1";
            ResultSet rs = st.executeQuery(selectPlace);
            //Log.e("Number","!!!Nplaces "+rs);
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                Double latitude = Double.parseDouble(rs.getString("latitude"));
                Double longitude = Double.parseDouble(rs.getString("longitude"));
                Place place = new Place(id,name,latitude,longitude);
                places.add(place);
                //Log.i("Number","!!!Number of elements in Connector is "+places.size());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return places;
    }

    public Tour Get_Tour_by_ID(int id){
        Tour tour = new Tour();
        try{
            Statement st = connection.createStatement();
            String selectPlace = "select * from tour WHERE id="+id;
            ResultSet rs = st.executeQuery(selectPlace);

            while (rs.next()) {
                int t_id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");

                tour = new Tour(t_id,name,description);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tour;
    }

    public ArrayList<Tour> Get_Tours(){
        ArrayList<Tour> tours = new ArrayList<Tour>();
        try{
            Statement st = connection.createStatement();
            String selectTour = "select * from tour";
            ResultSet rs = st.executeQuery(selectTour);

            while (rs.next()) {
                int t_id = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                String description = rs.getString("DESCRIPTION");

                Tour tour = new Tour(t_id,name,description);
                tours.add(tour);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tours;
    }

    public Accommodation Get_Accommodation_by_ID(int id){
        Accommodation accommodation = new Accommodation(id);
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectAcc = "select * from accommodation WHERE id="+id;
            ResultSet rs = st.executeQuery(selectAcc);

            while (rs.next()) {
                int id1 = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                Log.i("Name",name);
                String description = rs.getString("DESCRIPTION");
                Float rating = Float.parseFloat(rs.getString("RATING"));
                Double latitude = Double.parseDouble(rs.getString("LATITUDE"));
                Double longitude = Double.parseDouble(rs.getString("LONGITUDE"));
                String address = rs.getString("ADDRESS");
                ArrayList<String> imgs = new ArrayList<String>();
                String selectimg = "SELECT link from Media_Acc WHERE id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                        String img = rs2.getString("LINK");
                        imgs.add(img);
                }
                ArrayList<Comment> comms = new ArrayList<Comment>();
                String select_comments = "SELECT * from Rating_Acc WHERE id_acc="+id;
                rs2 = st2.executeQuery(select_comments);
                while (rs2.next())
                {
                    String username = rs2.getString("USERNAME");
                    String text = rs2.getString("Text");
                    Integer rate = Integer.parseInt(rs2.getString("Rate"));
                    Comment tmp = new Comment(username,text,rate);
                    comms.add(tmp);
                }
                Accommodation acc = new Accommodation(id1,name,description,rating,latitude,longitude,address,imgs,comms);
                return acc;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return accommodation;
    }

    public Event Get_Event_by_ID(int id){
        Event event = new Event(id);
        try{
            Statement st = connection.createStatement();
            Statement st2 = connection.createStatement();
            String selectAcc = "select * from events WHERE id="+id;
            ResultSet rs = st.executeQuery(selectAcc);

            while (rs.next()) {
                int id1 = Integer.parseInt(rs.getString("ID"));
                String name = rs.getString("NAME");
                Log.i("Name",name);
                String description = rs.getString("DESCRIPTION");
                Double latitude = Double.parseDouble(rs.getString("LATITUDE"));
                Double longitude = Double.parseDouble(rs.getString("LONGITUDE"));
                String address = rs.getString("ADDRESS");
                ArrayList<String> imgs = new ArrayList<String>();
                String selectimg = "SELECT link from Media_Events WHERE id="+id;
                ResultSet rs2 = st2.executeQuery(selectimg);
                while (rs2.next())
                {
                    String img = rs2.getString("LINK");
                    imgs.add(img);
                }
                Event event1 = new Event(id1,name,description,latitude,longitude,address,imgs);
                return event1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return event;
    }


}

