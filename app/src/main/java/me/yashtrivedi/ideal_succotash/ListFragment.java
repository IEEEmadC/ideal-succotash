package me.yashtrivedi.ideal_succotash;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends Fragment implements ClickListener, RequestService.Callbacks, ShowRequestFormFragment.Callbacks {

    NotificationManager notificationManager;
    RequestService requestService;
    List<ListUser> list;
    private RecyclerView recyclerView;


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES);
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        final RViewAdapter adapter = new RViewAdapter(getContext());
        list = new ArrayList<>();
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListUser lu = dataSnapshot.getValue(ListUser.class);
                lu.setRoll(Utils.emailToroll(dataSnapshot.getKey()));
                list.add(0, lu);
                adapter.addItem(lu);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int pos = 0;
                for (ListUser lu : list) {
                    if (lu.getRoll().equals(Utils.emailToroll(dataSnapshot.getKey()))) {
                        int capacity = dataSnapshot.getValue(ListUser.class).carCapacity;
                        list.get(pos).carCapacity = capacity;
                        adapter.updateCapacity(pos,capacity);
                        break;
                    }
                    pos++;
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int pos = 0;
                for (ListUser lu : list) {
                    if (lu.getRoll().equals(Utils.emailToroll(dataSnapshot.getKey()))) {
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
        animator.setChangeDuration(1000);
        recyclerView.setItemAnimator(animator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, this));
        return v;
    }

    @Override
    public void onClick(View view, final int position) {
        //Dialog code
        Log.d("value", list.get(position).getName());
        DialogFragment dialogFragment = ShowRequestFormFragment.newInstance(position, list.get(position).getName());
        dialogFragment.show(getFragmentManager(),"ShowRequestFormFragment");

    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void update(int status, int position) {
        if (status == Constants.RIDE_REQUEST_ACCEPTED) {
            ListUser lu = list.get(position);
            NotificationCompat.Builder notif = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(lu.getName() + " (" + lu.getRoll() + ")")
                    .setContentText(Utils.statusString(status) + " your request")
                    .setSubText("Car No: " + lu.getCarNo());
            notificationManager.notify(12123, notif.build());
        } else if (status == Constants.RIDE_REQUEST_REJECTED) {
            NotificationCompat.Builder notif = new NotificationCompat.Builder(getContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("")
                    .setContentText(" your request");
            notificationManager.notify(12123, notif.build());
        }
    }

    @Override
    public void update(String area, int position) {
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_REQUEST_RIDE.concat("/").concat(Utils.rollToEmail("13BCE123")));
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> current = new HashMap<>();
        String myEmail = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "");
        current.put(myEmail, map);
        map.put(Constants.USER_NAME, PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_NAME, ""));
        map.put(Constants.AREA,area);
        map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_WAITING);
        firebase.updateChildren(current);
        Intent i = new Intent(getContext(), RequestService.class);
        i.putExtra(Constants.REQUESTED_USER, Utils.rollToEmail(list.get(position).getRoll()).concat("/").concat(myEmail));
        i.putExtra("position", position);
        getContext().startService(i);
        ServiceConnection mRequestConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                RequestService.LocalBinder binder = (RequestService.LocalBinder) service;
                requestService = binder.getServiceInstance();
                requestService.registerClient(ListFragment.this);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        getContext().bindService(i, mRequestConnection, Context.BIND_AUTO_CREATE);

    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
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

    interface Callbacks{
        void updateFab();
    }
}
