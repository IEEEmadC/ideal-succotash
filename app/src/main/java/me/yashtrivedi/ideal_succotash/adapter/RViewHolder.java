package me.yashtrivedi.ideal_succotash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.yashtrivedi.ideal_succotash.R;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class RViewHolder extends RecyclerView.ViewHolder {
    TextView name, from, to, capacity;

    //ImageView pic;
    public RViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        from = (TextView) itemView.findViewById(R.id.from);
        to = (TextView) itemView.findViewById(R.id.to);
        //pic = (ImageView) itemView.findViewById(R.id.img);
        capacity = (TextView) itemView.findViewById(R.id.capacity);
    }
}
