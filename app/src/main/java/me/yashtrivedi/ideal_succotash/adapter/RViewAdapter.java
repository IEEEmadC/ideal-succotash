package me.yashtrivedi.ideal_succotash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.model.ListUser;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {
    LayoutInflater infater;
    List<ListUser> list = new ArrayList<>();
    Context context;

    public RViewAdapter(Context context) {
        infater = LayoutInflater.from(context);
        this.context = context;
    }

    public void updateTried(int position) {
        list.get(position).setTried();
        notifyItemChanged(position);
    }

    public void updateCapacity(int position, int capacity, Map<String,Object> rideRequest) {
        list.get(position).setCarCapacity(capacity);
        list.get(position).setRideRequest(rideRequest);
        notifyItemChanged(position);
    }

    public void removeItem(int lu) {
        list.remove(lu);
        notifyItemRemoved(lu);
    }

    public void addItem(ListUser lu) {
        list.add(0, lu);
        notifyItemInserted(0);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = infater.inflate(R.layout.list_row, parent, false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        ListUser lu = list.get(position);
        holder.itemView.setBackgroundColor(Color.WHITE);
        holder.itemView.setEnabled(true);
        holder.itemView.setClickable(true);
        holder.itemView.setActivated(true);
        holder.itemView.setAlpha(1.0f);
        if(lu.getRideRequest()!=null && lu.getRideRequest().containsKey(PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.KEY_ENCODED_EMAIL,"null"))){
            holder.itemView.setEnabled(false);
            holder.itemView.setClickable(false);
            holder.itemView.setActivated(false);
            holder.itemView.setAlpha(0.5f);
            switch((int) ((HashMap) lu.getRideRequest().get(PreferenceManager.getDefaultSharedPreferences(context).getString(Constants.KEY_ENCODED_EMAIL,"null"))).get("status")) {
                case Constants.RIDE_REQUEST_ACCEPTED: holder.itemView.setBackgroundColor(Color.GREEN);
                    break;
                case Constants.RIDE_REQUEST_REJECTED: holder.itemView.setBackgroundColor(Color.RED);
                    break;
                case Constants.RIDE_REQUEST_WAITING: holder.itemView.setBackgroundColor(Color.YELLOW);
                    break;
                default:
                    holder.itemView.setBackgroundColor(Color.WHITE);
                    break;
            }
        }
        holder.from.setText((!lu.getToNirma()) ? "Nirma" : lu.getArea());
        holder.to.setText(lu.getToNirma() ? "Nirma" : lu.getArea());
        //holder.pic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(BitmapFactory.decodeFile(lu.getImg().getAbsolutePath()),holder.pic.getWidth()));
        holder.name.setText(lu.getuserName() + " (" + lu.getRoll() + ")");
        holder.capacity.setText(lu.getCapacity() + "");
    }

    @Override
    public int getItemCount() {
        //Log.d("size",list.size()+"");
        return list.size();
    }
}
