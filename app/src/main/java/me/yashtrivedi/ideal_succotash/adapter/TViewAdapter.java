package me.yashtrivedi.ideal_succotash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class TViewAdapter extends RecyclerView.Adapter<TViewHolder> {
    List<Threads> list;
    Context context;
    LayoutInflater inflater;
    public TViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void add(int position, Threads thread){
        list.add(position,thread);
        notifyItemInserted(position);
    }

    public void remove(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void move(int initialPosition, int newPosition, long newTime){
        Threads t = list.get(initialPosition);
        t.setTime(newTime);
        list.remove(initialPosition);
        list.add(newPosition,t);
        notifyItemMoved(initialPosition,newPosition);
    }

    @Override
    public TViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.thread_personal_row,parent);
        return new TViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TViewHolder holder, int position) {
        Threads thread = list.get(position);
        holder.date.setText(new Date(thread.getTime()).toString());
        holder.name.setText(thread.getName());
//        holder.msg.setText(thread.getMessage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
