package pl.edu.pwr.i269691.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Comment;
import pl.edu.pwr.i269691.project.entity.Place;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    Context context;

    public CommentAdapter(Context ct, ArrayList<Comment> comments){
        context = ct;
        this.comments = comments;
    }
    public void setItem(Comment comment) {
        comments.add(comment);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new CommentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.MyViewHolder holder, int position) {
        holder.username.setText(comments.get(position).getUsername());
        holder.text.setText(comments.get(position).getText());
        holder.rating.setRating(comments.get(position).getRate());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        TextView text;
        RatingBar rating;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.User);
            text = itemView.findViewById(R.id.comment);
            rating = itemView.findViewById(R.id.ratingBar4);
        }
    }

}
