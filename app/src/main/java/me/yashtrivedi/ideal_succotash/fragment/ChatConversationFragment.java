package me.yashtrivedi.ideal_succotash.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.CViewAdapter;
import me.yashtrivedi.ideal_succotash.model.Message;

/**
 * Created by yashtrivedi on 25/05/16.
 */
public class ChatConversationFragment extends Fragment {

    EditText messageView;
    CViewAdapter adapter;
    public ChatConversationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_conversation, container, false);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.conv);
        messageView = (EditText) v.findViewById(R.id.new_msg);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CViewAdapter(getContext());
        recyclerView.setAdapter(adapter);

        final Firebase firebase = new Firebase(Constants.FIREBASE_URL_CHATS.concat("/").concat(getArguments().getString(Constants.CONVERSATION_PUSH_ID)).concat("/").concat("messages"));
        firebase.keepSynced(true);
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                adapter.add(dataSnapshot.getValue(Message.class));
                recyclerView.scrollToPosition(0);
                Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.getMyEmail(getContext())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(getArguments().getString(Constants.CONVERSATION_PUSH_ID)));
                Map<String, Object> map = new HashMap<>();
                map.put(Constants.THREAD_READ, true);
                map.put(Constants.THREAD_UNREAD_COUNT, 0);
                firebase2.updateChildren(map);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Message m = new Message();
                m.setFrom(Utils.getMyEmail(getContext()));
                m.setMsg(messageView.getText().toString());
                messageView.setText("");
                m.setTime(Calendar.getInstance().getTimeInMillis());
                firebase.push().setValue(m);
                final Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(getArguments().getString(Constants.THREAD_EMAIL)).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(getArguments().getString(Constants.CONVERSATION_PUSH_ID)));
                firebase1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = Integer.parseInt(((Map<String, Object>) dataSnapshot.getValue()).get(Constants.THREAD_UNREAD_COUNT).toString());
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put(Constants.THREAD_READ, false);
                        map.put(Constants.THREAD_UNREAD_COUNT, count++);
                        map.put(Constants.THREAD_TIME, m.getTime());
                        map.put(Constants.THREAD_LAST_MSG,m.getMsg());
                        firebase1.updateChildren(map);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.getMyEmail(getContext())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(getArguments().getString(Constants.CONVERSATION_PUSH_ID)));
                Map<String, Object> map = new HashMap<>();
                map.put(Constants.THREAD_READ, true);
                map.put(Constants.THREAD_UNREAD_COUNT, 0);
                map.put(Constants.THREAD_TIME, m.getTime());
                map.put(Constants.THREAD_LAST_MSG,m.getMsg());
                firebase2.updateChildren(map);
            }
        });

        return v;
    }
}
