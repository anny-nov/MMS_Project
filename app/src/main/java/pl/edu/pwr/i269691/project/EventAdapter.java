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

import pl.edu.pwr.i269691.project.entity.Event;
import pl.edu.pwr.i269691.project.entity.Place;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private OnEventClickListener onEventClickListener;
    private ArrayList<Event> added_events = new ArrayList<Event>();
    Context context;

    public EventAdapter(Context ct, ArrayList<Event> events, OnEventClickListener onEventClickListener){
        context = ct;
        added_events = events;
        this.onEventClickListener = onEventClickListener;
    }
    public void setItem(Event event) {
        added_events.add(event);
        notifyDataSetChanged();
    }
    public void onItemDismiss(int position)
    {
        added_events.remove(position);
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
        holder.title.setText(added_events.get(position).getName());
        if(added_events.get(position).getImg().size()>0)
        {
            URL newurl = null;
            try {
                newurl = new URL(added_events.get(position).getImg().get(0));
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
                Event chosen = added_events.get(pos);
                Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                onEventClickListener.OnTaskClick(chosen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return added_events.size();
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

    public interface OnEventClickListener {
        void OnTaskClick(Event event);
    }

}