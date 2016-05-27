package me.yashtrivedi.ideal_succotash.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.model.Message;

/**
 * Created by yashtrivedi on 25/05/16.
 */
public class CViewAdapter extends RecyclerView.Adapter<CViewHolder> {
    List<Message> list;
    Context context;
    LayoutInflater inflater;
    public CViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        list = new ArrayList<>();
    }

    public void add(Message m){
        list.add(0,m);
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getFrom().equals(BaseApplication.utils.getMyEmail()) ? Constants.MESSAGE_VIEW_OWN : Constants.MESSAGE_VIEW_OTHERS;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType){
            case Constants.MESSAGE_VIEW_OWN: v = inflater.inflate(R.layout.c_right,parent,false);
                break;
            case Constants.MESSAGE_VIEW_OTHERS: v = inflater.inflate(R.layout.c_left,parent,false);
                break;

        }
        return new CViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
        Message m = list.get(position);
        holder.msg.setText(m.getMsg());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(m.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm", Locale.US);
        holder.time.setText(dateFormat.format(c.getTime()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
