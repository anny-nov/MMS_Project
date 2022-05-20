package pl.edu.pwr.i269691.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import pl.edu.pwr.i269691.project.entity.Place;
import pl.edu.pwr.i269691.project.entity.Tour;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.MyViewHolder> {
    private TourListAdapter.OnPlaceClickListener onPlaceClickListener;
    private ArrayList<Tour> added_tours = new ArrayList<Tour>();
    Context context;

    public TourListAdapter(Context ct, ArrayList<Tour> tour, TourListAdapter.OnPlaceClickListener onPlaceClickListener){
        context = ct;
        added_tours = tour;
        this.onPlaceClickListener = onPlaceClickListener;
    }
    public void setItem(Tour tour) {
        added_tours.add(tour);
        notifyDataSetChanged();
    }
    public void onItemDismiss(int position)
    {
        added_tours.remove(position);
        Intent intent = new Intent("Item was deleted");
        intent.putExtra("Deleted",position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public TourListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tour_row, parent, false);
        return new TourListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourListAdapter.MyViewHolder holder, int position) {
        holder.title.setText(added_tours.get(position).getName());
        int pos = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tour chosen = added_tours.get(pos);
                Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                onPlaceClickListener.OnTaskClick(chosen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return added_tours.size();
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

    public interface OnPlaceClickListener {
        void OnTaskClick(Tour tour);
    }
}
