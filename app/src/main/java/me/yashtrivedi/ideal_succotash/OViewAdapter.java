package me.yashtrivedi.ideal_succotash;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * Created by amit on 21-May-16.
 */
public class OViewAdapter extends RecyclerView.Adapter<OViewHolder> {
    LayoutInflater layoutInflater;
    List<RideRequest> list  = new ArrayList<>();

    String prefDefine;
    Context context;

    public OViewAdapter(Context context, String prefDefine){
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.prefDefine = prefDefine;
    }

    public void setList(List<RideRequest> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void removeItem(int ru){
        list.remove(ru);
    }

    public void addItem(RideRequest ru){
        list.add(0, ru);

    }

    @Override
    public OViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.waiting_list_row, parent, false);
        return new OViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OViewHolder holder, final int position) {


        RideRequest ru = list.get(position);
        View.OnClickListener mOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firebase firebase = new Firebase(Constants.FIREBASE_URL_REQUEST_RIDE.concat("/").concat(prefDefine).concat("/").concat(list.get(position).getEmail()));
                Map<String, Object> map = new HashMap<>();
                switch (view.getId()){
                    case R.id.cancel_request:
                        map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_REJECTED);
                        break;
                    case R.id.accept_request:
                        map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_ACCEPTED);
                        break;
                }
                firebase.updateChildren(map);

            }
        };
        Boolean stat = ru.getStatus()==0;
        holder.name.setText(ru.getName() + " (" + Utils.emailToroll(ru.getEmail()) + ")");
        holder.status.setText(ru.getStatus());
        holder.status.setVisibility(stat?View.GONE:View.VISIBLE);
        holder.acceptCancelButtonHolder.setVisibility(!stat?View.GONE:View.VISIBLE);
        holder.acceptButton.setOnClickListener(mOnClickListner);
        holder.cancelButton.setOnClickListener(mOnClickListner);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
