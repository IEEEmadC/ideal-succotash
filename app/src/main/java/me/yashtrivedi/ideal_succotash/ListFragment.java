package me.yashtrivedi.ideal_succotash;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment implements ClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    public ListFragment() {
        // Required empty public constructor
    }
    List<ListUser> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES);

        final RViewAdapter adapter = new RViewAdapter(getContext());
        list = new ArrayList<>();
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListUser lu = dataSnapshot.getValue(ListUser.class);
                lu.setRoll(Utils.emailToroll(dataSnapshot.getKey()));
                list.add(0,lu);
                adapter.addItem(lu);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int pos = 0;
                for(ListUser lu : list){
                    if(lu.getRoll().equals(Utils.emailToroll(dataSnapshot.getKey()))){
                        list.remove(lu);
                        adapter.removeItem(pos);
                        break;
                    }
                    pos++;
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(500);
        animator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(),recyclerView,this));
        return v;
    }

    @Override
    public void onClick(View view, final int position) {
        //Dialog code
        Log.d("value",list.get(position).getName());
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle("Are you sure to ride with "+list.get(position).getName()+"?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                Intent intent = new Intent(getContext(),MainActivity.class);
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setTicker("Requesting Ride")
                        .setOngoing(true)
                        .addAction(new NotificationCompat.Action(R.drawable.ic_close_black_24dp,"Cancel",null));
                notificationManager.notify(13123,builder.build());
                //notify user
                Firebase firebase = new Firebase(Constants.FIREBASE_URL_REQUEST_RIDE);
                Map<String,Object> map = new HashMap<String, Object>();
                Map<String,Object> current = new HashMap<String, Object>();
                current.put(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL,""),false);
                map.put(Utils.rollToEmail(list.get(position).getRoll()),current);
                firebase.updateChildren(map);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create();
        dialog.show();
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener=clickListener;
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null)
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                }

            });
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
                clickListener.onClick(child, rv.getChildLayoutPosition(child));

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
