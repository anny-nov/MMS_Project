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

import pl.edu.pwr.i269691.project.entity.Place;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {
    private OnPlaceClickListener onPlaceClickListener;
    private ArrayList<Place> added_places = new ArrayList<Place>();
    Context context;

    public PlaceAdapter(Context ct, ArrayList<Place> places, OnPlaceClickListener onPlaceClickListener){
        context = ct;
        added_places = places;
        this.onPlaceClickListener = onPlaceClickListener;
    }
    public void setItem(Place place) {
        added_places.add(place);
        notifyDataSetChanged();
    }
    public void onItemDismiss(int position)
    {
        added_places.remove(position);
        Intent intent = new Intent("Item was deleted");
        intent.putExtra("Deleted",position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.task_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_row, parent, false);
        //final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(itemView);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(added_places.get(position).getName());
        if(added_places.get(position).getImg().size()>0)
        {
            URL newurl = null;
            try {
                newurl = new URL(added_places.get(position).getImg().get(0));
                Bitmap pic = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
                holder.typeimg.setImageBitmap(pic);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            holder.typeimg.setImageResource(R.drawable.no_img);
        }
        int pos = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Place chosen = added_places.get(pos);
                Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                onPlaceClickListener.OnTaskClick(chosen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return added_places.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView typeimg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.place_name);
            typeimg = itemView.findViewById(R.id.Type_img);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnPlaceClickListener {
        void OnTaskClick(Place place);
    }

}