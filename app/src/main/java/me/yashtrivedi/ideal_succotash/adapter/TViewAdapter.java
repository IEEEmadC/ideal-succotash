package me.yashtrivedi.ideal_succotash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class TViewAdapter extends RecyclerView.Adapter<TViewHolder> {
    List<Threads> list;
    Context context;
    LayoutInflater inflater;
    public TViewAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public Threads get(int positon){
        return list.get(positon);
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
        View v = inflater.inflate(R.layout.thread_personal_row,parent,false);
        return new TViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TViewHolder holder, int position) {
        Threads thread = list.get(position);
        Long diff = Calendar.getInstance().getTimeInMillis() - thread.getTime();
        String tag = "ms";
        Log.d("time",diff.toString());
        diff /= 1000; //these are the seconds


        if(diff>=0 && diff<60){
            tag="just now";
        }
        else if(diff>=60 && diff<3600){
            diff/=60;
            tag=diff+" min ago";

        }
        else if(diff>=3600 && diff<86400){
            diff/=3600;
            tag=diff+" hour ago";
        }
        else if(diff>=86400 && diff<604800){
            diff/=86400;
            tag=diff+" day ago";
        }
        else if(diff>=604800){   //seconds in 1 week
            diff/=604800;
            tag=diff+" week ago";
        }



        holder.date.setText(tag);
        holder.name.setText(thread.getName() + " (" + BaseApplication.utils.emailToroll(thread.getEmail()) + ") ");
        holder.msg.setText(thread.getmsg());
    }

    public ArrayList<String> getAllEmails(){
        ArrayList<String> emailList = new ArrayList<>();
        for(int i=0; i<list.size();i++){
            emailList.add(list.get(i).getEmail());
        }
        return emailList;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
