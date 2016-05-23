package me.yashtrivedi.ideal_succotash;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ListFragment extends Fragment implements ClickListener {

    NotificationManager notificationManager;
    //    RequestService requestService;
    List<ListUser> list;
    List<String> requestList;
    Firebase firebase;
    ChildEventListener childEventListener;
    private RecyclerView recyclerView;
    private RViewAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    /*@Override
    public void onResume() {
        super.onResume();
        firebase.addChildEventListener(childEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        firebase.removeEventListener(childEventListener);

    }*/

    public void showRequestForm() {
        DialogFragment dialogFragment = ShowOfferFormFragment.newInstance();
        dialogFragment.show(getFragmentManager(), "ShowOfferFormFragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        FloatingActionButton mfab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        if (getArguments() != null && getArguments().getBoolean("animation", false)) {
            mfab.animate().rotation(0);
        }
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.requestFocus();
        firebase = new Firebase(Constants.FIREBASE_URL_RIDES);
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        adapter = new RViewAdapter(getContext());
        list = new ArrayList<>();
        requestList = new ArrayList<>();

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRequestForm();
            }
        });
        mfab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });


        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ListUser lu = dataSnapshot.getValue(ListUser.class);
                lu.setRoll(Utils.emailToroll(dataSnapshot.getKey()));
                Map<String, Object> rr = lu.rideRequest;
                if (rr != null) {
                    lu.setTried(rr.containsKey(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "")));
                } else {
                    lu.setTried(false);
                    lu.rideRequest = null;
                }
                list.add(0, lu);
                adapter.addItem(lu);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int pos = 0;
                for (ListUser lu : list) {
                    if (lu.getRoll().equals(Utils.emailToroll(dataSnapshot.getKey()))) {
                        int capacity = dataSnapshot.getValue(ListUser.class).carCapacity;
                        Map<String, Object> rr = dataSnapshot.getValue(ListUser.class).rideRequest;
                        list.get(pos).carCapacity = capacity;
                        list.get(pos).rideRequest = rr;
                        adapter.updateCapacity(pos, capacity, rr);
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
        };
        firebase.addChildEventListener(childEventListener);
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
    public void onClick(View view, int position) {
        //Dialog code
        if (!list.get(position).getTried()) {
            DialogFragment dialogFragment = ShowRequestFormFragment.newInstance(position, list.get(position).getName(), list.get(position).getToNirma());
            dialogFragment.show(getFragmentManager(), "ShowRequestFormFragment");
            dialogFragment.setTargetFragment(this, 123);
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            int position = data.getIntExtra("position", 0);
            String area = data.getStringExtra(Constants.AREA);
            ListUser lu = list.get(position);
            list.get(position).setTried();
            adapter.updateTried(position);
            Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/")
                    .concat(Utils.rollToEmail(lu.getRoll())).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> current = new HashMap<>();
            String myEmail = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "");
            current.put(myEmail, map);
            map.put(Constants.USER_NAME, PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_NAME, ""));
            map.put(Constants.AREA, area);
            map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_WAITING);
            firebase.updateChildren(current);
            Intent i = new Intent(getContext(), RequestService.class);
            i.putExtra(Constants.REQUESTED_USER, Utils.rollToEmail(list.get(position).getRoll()).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
            i.putExtra("position", position);
            i.putExtra(Constants.REQUESTED_USER, lu.getName());
            i.putExtra(Constants.KEY_ENCODED_EMAIL, lu.getRoll());
            i.putExtra(Constants.CAR_NO, lu.getCarNo());
            getContext().startService(i);
            /*ServiceConnection mRequestConnection = new ServiceConnection() {
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
            getContext().bindService(i, mRequestConnection, Context.BIND_AUTO_CREATE);*/

        }
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
}
