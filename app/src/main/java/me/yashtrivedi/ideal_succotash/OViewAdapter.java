package me.yashtrivedi.ideal_succotash;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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
        Log.d("hi","a");
    }
    public void setList(List<RideRequest> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void removeItem(int ru){
        list.remove(ru);
        notifyItemRemoved(ru);
    }

    public void addItem(RideRequest ru){
        list.add(0, ru);
        notifyItemInserted(0);
    }

    @Override
    public OViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.waiting_list_row, parent, false);
        //Log.d("hi","d");
        return new OViewHolder(view);
    }
    int cap;

    @Override
    public void onBindViewHolder(final OViewHolder holder,final int position) {
        final RideRequest ru = list.get(position);
        //Log.d("name",ru.getName());
        View.OnClickListener mOnClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Firebase firebase = new Firebase(Constants.FIREBASE_URL_REQUEST_RIDE.concat("/").concat(prefDefine).concat("/").concat(list.get(position).getEmail()));
                Map<String, Object> map = new HashMap<>();
                //Log.d("here",firebase.toString());
                switch (view.getId()){
                    case R.id.cancel_request:
                        map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_REJECTED);
                        ru.status=Constants.RIDE_REQUEST_REJECTED;
                        break;
                    case R.id.accept_request:
                        map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_ACCEPTED);
                        ru.status=Constants.RIDE_REQUEST_ACCEPTED;
                        break;
                }
                holder.status.setVisibility(View.VISIBLE);
                holder.status.setText(Utils.statusString(ru.getStatus()));
                holder.acceptCancelButtonHolder.setVisibility(View.GONE);
                firebase.updateChildren(map);
                Firebase ride = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(prefDefine).concat("/").concat(Constants.CAR_CAPACITY));

                ride.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cap = Integer.parseInt(dataSnapshot.getValue().toString());
                        Map<String,Object> mapa = new HashMap<>();
                        mapa.put(Constants.CAR_CAPACITY,cap-1);
                        Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(prefDefine));
                        firebase1.updateChildren(mapa);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        };
        Boolean stat = ru.getStatus()==0;
        holder.area.setText(ru.getArea());
        holder.name.setText(ru.getName() + "\n(" + Utils.emailToroll(ru.getEmail()) + ")");
        Log.d("here",ru.getStatus()+"");
        holder.acceptCancelButtonHolder.setVisibility(!stat ? View.GONE : View.VISIBLE);
        holder.status.setVisibility(stat?View.GONE:View.VISIBLE);
        if(ru.getStatus()!=0) {
            holder.status.setText(Utils.statusString(ru.getStatus()));
        }else {
            holder.acceptButton.setOnClickListener(mOnClickListner);
            holder.cancelButton.setOnClickListener(mOnClickListner);
        }
    }

    @Override
    public int getItemCount() {
//        Log.d("size",list.size()+"");
        return list.size();
    }
}
