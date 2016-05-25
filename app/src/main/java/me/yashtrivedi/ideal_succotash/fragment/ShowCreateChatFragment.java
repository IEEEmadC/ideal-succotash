package me.yashtrivedi.ideal_succotash.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.model.Threads;

/**
 * Created by yashtrivedi on 24/05/16.
 */
public class ShowCreateChatFragment extends DialogFragment{
    public static ShowCreateChatFragment newInstance(){
        ShowCreateChatFragment fragment = new ShowCreateChatFragment();
        Bundle b = new Bundle();
        fragment.setArguments(b);
        return fragment;
    }

    AppCompatAutoCompleteTextView appCompatAutoCompleteTextView;
    ArrayAdapter<String> adapter;
    String name;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomTheme_Dialog);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View v = inflater.inflate(R.layout.dialog_create_chat,null,false);
        adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item);
        appCompatAutoCompleteTextView = (AppCompatAutoCompleteTextView) v.findViewById(R.id.addName);
        appCompatAutoCompleteTextView.setAdapter(adapter);
        appCompatAutoCompleteTextView.requestFocus();
        appCompatAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appCompatAutoCompleteTextView.setText(adapter.getItem(position));
                Firebase firebase = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.encodeEmail(adapter.getItem(position))));
                firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        name = ((Map<String,Object>) dataSnapshot.getValue()).get(Constants.KEY_NAME).toString();
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_USERS);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    adapter.add(Utils.decodeEmail(d.getKey()));
                    Log.d("id",d.getKey());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        builder.setTitle("Create Chat")
        .setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Firebase firebase1 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.getMyEmail(getContext())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS)).push();
                Map<String,Object> map = new HashMap<>();
                map.put(Constants.THREAD_EMAIL,Utils.encodeEmail(appCompatAutoCompleteTextView.getText().toString()));
                map.put(Constants.THREAD_NAME,name);
                map.put(Constants.THREAD_READ,true);
                map.put(Constants.THREAD_TIME,ServerValue.TIMESTAMP);
                map.put(Constants.THREAD_UNREAD_COUNT,0);
                firebase1.setValue(map);
                String threadID = firebase1.getKey();
                Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USERS.concat("/").concat(Utils.encodeEmail(appCompatAutoCompleteTextView.getText().toString())).concat("/").concat(Constants.FIREBASE_LOCATION_CHATS).concat("/").concat(threadID));
                Map<String,Object> map1 = new HashMap<>();
                map1.put(Constants.THREAD_EMAIL,Utils.getMyEmail(getContext()));
                map1.put(Constants.THREAD_NAME,Utils.getMyName(getContext()));
                map1.put(Constants.THREAD_READ,true);
                map1.put(Constants.THREAD_TIME,true);
                map1.put(Constants.THREAD_UNREAD_COUNT,0);
                firebase2.setValue(map1);
            }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        })
        .setView(v);

        return builder.create();
    }
}
