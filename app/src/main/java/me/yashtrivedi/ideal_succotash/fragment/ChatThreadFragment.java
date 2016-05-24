package me.yashtrivedi.ideal_succotash.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.yashtrivedi.ideal_succotash.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatThreadFragment extends Fragment {

    public ChatThreadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.thread_list);

        return v;
    }
}
