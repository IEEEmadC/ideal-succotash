package me.yashtrivedi.ideal_succotash;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class RViewHolder extends RecyclerView.ViewHolder {
    TextView name,from,to;
    ImageView pic;
    public RViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        from = (TextView) itemView.findViewById(R.id.from);
        to = (TextView) itemView.findViewById(R.id.to);
        pic = (ImageView) itemView.findViewById(R.id.img);
    }
}
