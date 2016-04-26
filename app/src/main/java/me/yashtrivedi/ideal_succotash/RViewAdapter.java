package me.yashtrivedi.ideal_succotash;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by yashtrivedi on 05/04/16.
 */
public class RViewAdapter extends RecyclerView.Adapter<RViewHolder> {

    List<ListUser> list = Collections.emptyList();

    public void setList(List<ListUser> list) {
        this.list = list;
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return null;
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
