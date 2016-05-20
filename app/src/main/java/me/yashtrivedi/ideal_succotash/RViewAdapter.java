package me.yashtrivedi.ideal_succotash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {
    LayoutInflater infater;
    List<ListUser> list = Collections.emptyList();

    public RViewAdapter(Context context) {
        infater = LayoutInflater.from(context);
    }

    public void setList(List<ListUser> list) {
        this.list = list;
        notifyDataSetChanged();
        for(ListUser l : list){
            Log.d("value", l.getName());
        }
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = infater.inflate(R.layout.list_row,parent,false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        ListUser lu = list.get(position);
        holder.from.setText((!lu.getToNirma())?"Nirma":lu.getArea());
        holder.to.setText(lu.getToNirma()?"Nirma":lu.getArea());
        //holder.pic.setImageBitmap(ImageHelper.getRoundedCornerBitmap(BitmapFactory.decodeFile(lu.getImg().getAbsolutePath()),holder.pic.getWidth()));
        holder.name.setText(lu.getName() + " (" + lu.getRoll().toUpperCase() + ")");
        holder.capacity.setText(lu.getCapacity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
