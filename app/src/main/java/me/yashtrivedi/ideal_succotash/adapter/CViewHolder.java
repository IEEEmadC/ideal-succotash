package me.yashtrivedi.ideal_succotash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.yashtrivedi.ideal_succotash.R;

/**
 * Created by yashtrivedi on 25/05/16.
 */
public class CViewHolder extends RecyclerView.ViewHolder {
    TextView msg, time, from;
    public CViewHolder(View itemView) {
        super(itemView);
        msg = (TextView) itemView.findViewById(R.id.msg);
        time = (TextView) itemView.findViewById(R.id.time);
        from = (TextView) itemView.findViewById(R.id.from);
    }
}
