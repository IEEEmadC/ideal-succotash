package me.yashtrivedi.ideal_succotash.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.yashtrivedi.ideal_succotash.R;

/**
 * Created by amit on 21-May-16.
 */
public class OViewHolder extends RecyclerView.ViewHolder {

    TextView name, status, area;
    Button acceptButton, cancelButton;
    LinearLayout acceptCancelButtonHolder;

    public OViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        status = (TextView) itemView.findViewById(R.id.status);
        acceptButton = (Button) itemView.findViewById(R.id.accept_request);
        cancelButton = (Button) itemView.findViewById(R.id.cancel_request);
        acceptCancelButtonHolder = (LinearLayout) itemView.findViewById(R.id.accept_cancel_button_holder);
        area = (TextView) itemView.findViewById(R.id.area);
    }

}
