package me.yashtrivedi.ideal_succotash.fragment;

import android.app.NotificationManager;
import android.content.Context;
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
import java.util.Calendar;
import java.util.List;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.ClickListener;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.RecyclerTouchListener;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.TViewAdapter;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * A placeholder fragment containing a simple view.
 */
public class ChatThreadFragment extends Fragment implements ClickListener {

    TViewAdapter adapter;
    List<Threads> list;
    FloatingActionButton fab;
    Firebase firebase;
    ChildEventListener listener;
    @Override
    public void onResume() {
        super.onResume();
//        firebase.addChildEventListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
//        firebase.removeEventListener(listener);
    }

    public ChatThreadFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_thread, container, false);
        getActivity().setTitle("Chats");
        ((NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel("onethreetwo",12345);
        adapter = new TViewAdapter(getContext());
        list = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.thread_list);
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = ShowCreateChatFragment.newInstance( adapter.getAllEmails());
                dialogFragment.show(getFragmentManager(), "ShowCreateChatFragment");
            }
        });
        firebase = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(BaseApplication.utils.getMyEmail()).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS));
        firebase.keepSynced(true);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) throws NullPointerException{
                try {
                    Threads t = dataSnapshot.getValue(Threads.class);
                    t.setKey(dataSnapshot.getKey());
                    int position = BaseApplication.utils.getInsertPositionByTime(list, 0, list.size() - 1, t.getTime());
                    list.add(position, t);
                    adapter.add(position, t);
                }catch (NullPointerException ne){}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) throws NullPointerException{
                    Threads t = dataSnapshot.getValue(Threads.class);
                    t.setKey(dataSnapshot.getKey());
                    int oldPosition = 0;
                    for (Threads lt : list) {
                        if (lt.getKey().equals(t.getKey())) {
                            break;
                        }
                        oldPosition++;
                    }
                    int newPosition = BaseApplication.utils.getInsertPositionByTime(list, 0, oldPosition, t.getTime());
                    list.remove(oldPosition);
                    list.add(newPosition, t);
                    adapter.move(oldPosition, newPosition, t.getTime());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) throws NullPointerException{

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
        };
        firebase.addChildEventListener(listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView,this));
        return v;
    }

    @Override
    public void onClick(View view, int position) {
        Threads t = adapter.get(position);
        Bundle b = new Bundle();
        b.putString(Constants.THREAD_EMAIL,t.getEmail());
        b.putString(Constants.CONVERSATION_PUSH_ID,t.getKey());
        b.putString(Constants.USER_NAME,t.getName());
        ChatConversationFragment chatConversationFragment = new ChatConversationFragment();
        chatConversationFragment.setArguments(b);
        getFragmentManager().beginTransaction().replace(R.id.container,chatConversationFragment,null).addToBackStack("chat").commit();

    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
