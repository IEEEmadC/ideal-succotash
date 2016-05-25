package me.yashtrivedi.ideal_succotash.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.TViewAdapter;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatThreadFragment extends Fragment {

    TViewAdapter adapter;
    List<Threads> list;

    public ChatThreadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        adapter = new TViewAdapter(getContext());
        list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.thread_list);
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = ShowCreateChatFragment.newInstance();
                dialogFragment.show(getFragmentManager(), "ShowCreateChatFragment");
            }
        });
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.getMyEmail(getContext())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS));
        firebase.keepSynced(true);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Threads t = dataSnapshot.getValue(Threads.class);
                t.setKey(dataSnapshot.getKey());
                int position = Utils.getInsertPositionByTime(list, 0, list.size() - 1, t.getTime());
                list.add(position, t);
                adapter.add(position, t);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Threads t = dataSnapshot.getValue(Threads.class);
                t.setKey(dataSnapshot.getKey());
                int oldPosition = 0;
                for (Threads lt : list) {
                    if (lt.getKey().equals(t.getKey())) {
                        break;
                    }
                    oldPosition++;
                }
                int newPosition = Utils.getInsertPositionByTime(list, 0, oldPosition, t.getTime());
                list.remove(oldPosition);
                list.add(newPosition, t);
                adapter.move(oldPosition, newPosition, t.getTime());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int oldPosition = 0;
                for (Threads lt : list) {
                    if (lt.getKey().equals(dataSnapshot.getKey())) {
                        list.remove(oldPosition);
                        adapter.remove(oldPosition);
                        break;
                    }
                    oldPosition++;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }
}
