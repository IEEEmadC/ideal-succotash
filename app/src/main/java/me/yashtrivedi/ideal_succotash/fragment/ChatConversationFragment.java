package me.yashtrivedi.ideal_succotash.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.CViewAdapter;
import me.yashtrivedi.ideal_succotash.model.Message;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by yashtrivedi on 25/05/16.
 */
public class ChatConversationFragment extends Fragment {

    EditText messageView;
    CViewAdapter adapter;
    FloatingActionButton fab, fab2;
    Firebase firebase;
    ChildEventListener listener;
    String chatID;

    public ChatConversationFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat_conversation, container, false);
        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.conv);
        getActivity().setTitle(getArguments().getString(Constants.USER_NAME));
        messageView = (EditText) v.findViewById(R.id.new_msg);
        chatID = getArguments().getString(Constants.CONVERSATION_PUSH_ID);
        Log.d("receivedPushID", chatID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new CViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab2.hide();
        fab = (FloatingActionButton) v.findViewById(R.id.fab_chat);
        fab.hide();
        firebase = new Firebase(Constants.FIREBASE_URL_CHATS.concat("/").concat(chatID).concat("/").concat("messages"));
        firebase.keepSynced(true);
        listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) throws NullPointerException {
                adapter.add(dataSnapshot.getValue(Message.class));
                recyclerView.scrollToPosition(0);
                Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(BaseApplication.utils.getMyEmail()).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(chatID));
                Map<String, Object> map = new HashMap<>();
                map.put(Constants.THREAD_READ, true);
                map.put(Constants.THREAD_UNREAD_COUNT, 0);
                firebase2.updateChildren(map);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) throws NullPointerException {

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
        };
        firebase.addChildEventListener(listener);
        messageView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (messageView.getText().toString().trim().length() > 0) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        messageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.scrollToPosition(0);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fab.hide();
                final Message m = new Message();
                m.setFrom(BaseApplication.utils.getMyEmail());
                m.setMsg(messageView.getText().toString().trim());
                messageView.setText("");
                m.setTime(Calendar.getInstance().getTimeInMillis());
                firebase.push().setValue(m);
                final Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(getArguments().getString(Constants.THREAD_EMAIL)).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(chatID));

                firebase1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = dataSnapshot.getValue(Threads.class).getUnreadCount();
                        Log.d("current count", count + "");
                        Map<String, Object> map = new HashMap<>();
                        map.put(Constants.THREAD_READ, false);
                        map.put(Constants.THREAD_UNREAD_COUNT, ++count);
                        Log.d("new count", count + "");
                        map.put(Constants.THREAD_TIME, m.getTime());
                        map.put(Constants.THREAD_LAST_MSG, m.getMsg());
                        firebase1.updateChildren(map);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
                Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(BaseApplication.utils.getMyEmail()).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(chatID));
                Map<String, Object> map = new HashMap<>();
                map.put(Constants.THREAD_READ, true);
                map.put(Constants.THREAD_UNREAD_COUNT, 0);
                map.put(Constants.THREAD_TIME, m.getTime());
                map.put(Constants.THREAD_LAST_MSG, m.getMsg());
                firebase2.updateChildren(map);
            }

        });

        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        fab2.show();
    }


}
