package pl.edu.pwr.i269691.project;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Tour;

public class AllToursAdapter extends RecyclerView.Adapter<AllToursAdapter.MyViewHolder>{
    private AllToursAdapter.OnTourClickListener onTourClickListener;
    private ArrayList<Tour> added_Tours = new ArrayList<Tour>();
    Context context;

    public AllToursAdapter(Context ct, ArrayList<Tour> tours, AllToursAdapter.OnTourClickListener onTourClickListener){
        context = ct;
        added_Tours = tours;
        this.onTourClickListener = onTourClickListener;
    }
    public void setItem(Tour tour) {
        added_Tours.add(tour);
        notifyDataSetChanged();
    }
    public void onItemDismiss(int position)
    {
        added_Tours.remove(position);
        Intent intent = new Intent("Item was deleted");
        intent.putExtra("Deleted",position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public AllToursAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_row, parent, false);
        return new AllToursAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllToursAdapter.MyViewHolder holder, int position) {
        holder.title.setText(added_Tours.get(position).getName());

        int pos = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour chosen = added_Tours.get(pos);
                Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                onTourClickListener.OnTaskClick(chosen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return added_Tours.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.place_name);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnTourClickListener {
        void OnTaskClick(Tour Tour);
    }
}
