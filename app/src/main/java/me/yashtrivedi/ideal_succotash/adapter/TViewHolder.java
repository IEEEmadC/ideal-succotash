package me.yashtrivedi.ideal_succotash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.yashtrivedi.ideal_succotash.R;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class TViewHolder extends RecyclerView.ViewHolder {
    TextView name, msg, date;
    public TViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        msg = (TextView) itemView.findViewById(R.id.last_msg);
        date = (TextView) itemView.findViewById(R.id.time);
    }
}
