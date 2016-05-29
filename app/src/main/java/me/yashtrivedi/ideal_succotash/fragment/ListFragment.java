package me.yashtrivedi.ideal_succotash.fragment;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import net.frederico.showtipsview.ShowTipsBuilder;
import net.frederico.showtipsview.ShowTipsView;
import net.frederico.showtipsview.ShowTipsViewInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.yashtrivedi.ideal_succotash.BaseApplication;
import me.yashtrivedi.ideal_succotash.ClickListener;
import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.activity.MainActivity;
import me.yashtrivedi.ideal_succotash.service.RequestService;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.adapter.RViewAdapter;
import me.yashtrivedi.ideal_succotash.model.ListUser;
import me.yashtrivedi.ideal_succotash.RecyclerTouchListener;

import static android.R.style.Animation;

public class ListFragment extends Fragment implements ClickListener {

    NotificationManager notificationManager;
    //    RequestService requestService;
    List<ListUser> list;
    List<String> requestList;
    Firebase firebase;
    ChildEventListener childEventListener;
    ImageView noRidesImg;
    private RecyclerView recyclerView;
    private RViewAdapter adapter;
    public ListFragment() {
        // Required empty public constructor
    }

    /*   @Override
       public void onResume() {
           super.onResume();
       }

       @Override
       public void onPause() {
           super.onPause();
           firebase.removeEventListener(childEventListener);

       }
   */
    public void showRequestForm() {
        DialogFragment dialogFragment = ShowOfferFormFragment.newInstance();
        dialogFragment.show(getFragmentManager(), "ShowOfferFormFragment");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        final FloatingActionButton mfab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        noRidesImg = (ImageView)v.findViewById(R.id.no_rides_available_img);

        if (getArguments() != null && getArguments().getBoolean("animation", false)) {
            mfab.animate().rotation(0);
        }
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.requestFocus();
        firebase = new Firebase(Constants.FIREBASE_URL_RIDES);
        firebase.keepSynced(true);
        notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        adapter = new RViewAdapter(getContext());
        list = new ArrayList<>();
        requestList = new ArrayList<>();

        if(list.size()==0)
        noRidesImg.setVisibility(View.VISIBLE);

        final ShowTipsView showTips = new ShowTipsBuilder(getActivity())
                .setTarget(mfab)
                .setTitle("Create Ride button")
                .setDescription("Tap this button if you want to create a ride")
                .setDelay(100)
                .build();

        showTips.setAlpha(0.8f);
        showTips.show(getActivity());



       /*final ShowTipsView showTips2 = new ShowTipsBuilder(getActivity()).setTarget()
                .setTitle("A magnific button")
                .setDescription("This button do nothing so good")
                .setDelay(200)
                .build();
        showTips2.setAlpha(0.8f);
*/
        showTips.setCallback(new ShowTipsViewInterface() {
            @Override
            public void gotItClicked() {
  //              showTips2.show(getActivity());
            }
        });

//        showTips.setDisplayOneTime(true);
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
                lu.setRoll(BaseApplication.utils.emailToroll(dataSnapshot.getKey()));
                Map<String, Object> rr = lu.getRideRequest();
                if (rr != null) {
                    lu.setTried(rr.containsKey(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "")));
                } else {
                    lu.setTried(false);
                    lu.setRideRequest(null);
                }
                if(!lu.isStarted()) {
                    list.add(0, lu);
                    adapter.addItem(lu);
                    if (list.size() == 0) {
                        noRidesImg.setVisibility(View.INVISIBLE);
                    }
                    else{
                        noRidesImg.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                int pos = 0;
                for (ListUser lu : list) {
                    if (lu.getRoll().equals(BaseApplication.utils.emailToroll(dataSnapshot.getKey()))) {
                        int capacity = dataSnapshot.getValue(ListUser.class).getCapacity();
                        Map<String, Object> rr = dataSnapshot.getValue(ListUser.class).getRideRequest();
                        if (rr != null) {
                            list.get(pos).setTried(rr.containsKey(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "")));
                        } else {
                            list.get(pos).setTried(false);
                        }
                        list.get(pos).setCarCapacity(capacity);
                        list.get(pos).setRideRequest(rr);
                        adapter.updateCapacity(pos, capacity, rr);
                        if(lu.isStarted()){
                            list.remove(pos);
                            adapter.removeItem(pos);
                        }
                        break;
                    }
                    pos++;
                }
                if(list.size()==0)
                    noRidesImg.setVisibility(View.VISIBLE);
                else
                    noRidesImg.setVisibility(View.GONE);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                if (list.size() == 1) {

                }
                int pos = 0;
                for (ListUser lu : list) {
                    if (lu.getRoll().equals(BaseApplication.utils.emailToroll(dataSnapshot.getKey()))) {
                        list.remove(lu);
                        adapter.removeItem(pos);
                        break;
                    }
                    pos++;
                }

                if(list.size()==0){
                    noRidesImg.setVisibility(View.VISIBLE);
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
            DialogFragment dialogFragment = ShowRequestFormFragment.newInstance(position, list.get(position).getuserName(), list.get(position).getToNirma());
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
                    .concat(BaseApplication.utils.rollToEmail(lu.getRoll())).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
            Map<String, Object> map = new HashMap<>();
            Map<String, Object> current = new HashMap<>();
            String myEmail = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "");
            current.put(myEmail, map);
            map.put(Constants.USER_NAME, PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_NAME, ""));
            map.put(Constants.AREA, area);
            map.put(Constants.REQUEST_STATUS, Constants.RIDE_REQUEST_WAITING);
            firebase.updateChildren(current);
            Firebase firebase2 = new Firebase(Constants.FIREBASE_URL_USER_REQUEST.concat("/").concat((myEmail)));
            Map<String, Object> map1 = new HashMap<>();
            map1.put(BaseApplication.utils.rollToEmail(lu.getRoll()), true);
            firebase2.updateChildren(map1);
            Intent i = new Intent(getContext(), RequestService.class);
            i.putExtra(Constants.REQUESTED_USER, BaseApplication.utils.rollToEmail(list.get(position).getRoll()).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE).concat("/").concat(myEmail));
            i.putExtra("position", position);
            i.putExtra("name", lu.getuserName());
            i.putExtra(Constants.KEY_ENCODED_EMAIL, lu.getRoll());
            i.putExtra("myEmail", myEmail);
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
}
