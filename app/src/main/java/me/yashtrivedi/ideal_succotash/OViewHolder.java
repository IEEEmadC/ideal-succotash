package me.yashtrivedi.ideal_succotash;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by amit on 21-May-16.
 */
public class OViewHolder extends RecyclerView.ViewHolder {

    TextView name, status;
    Button acceptButton, cancelButton;
    LinearLayout acceptCancelButtonHolder;
    public OViewHolder(View itemView){

        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name);
        status = (TextView) itemView.findViewById(R.id.status);
        acceptButton = (Button) itemView.findViewById(R.id.accept_request);
        cancelButton = (Button) itemView.findViewById(R.id.cancel_request);
        acceptCancelButtonHolder = (LinearLayout) itemView.findViewById(R.id.accept_cancel_button_holder);



    }

}
