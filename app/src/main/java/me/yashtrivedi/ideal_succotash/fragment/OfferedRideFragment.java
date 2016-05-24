package me.yashtrivedi.ideal_succotash.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import me.yashtrivedi.ideal_succotash.Constants;
import me.yashtrivedi.ideal_succotash.service.OfferService;
import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.adapter.OViewAdapter;
import me.yashtrivedi.ideal_succotash.model.RideRequest;

/**
 * Created by amit on 21-May-16.
 */
public class OfferedRideFragment extends Fragment {
    Boolean isFabOpen = false;
    List<RideRequest> list;
    private RecyclerView recyclerView;
    FloatingActionButton mfab,fab1,fab2;
    Animation fab_open,fab_close,fab_scale,fab_unscale;
    public OfferedRideFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_offered_ride, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "")).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
        final OViewAdapter adapter = new OViewAdapter(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, ""));
        list = new ArrayList<>();
        mfab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab1 = (FloatingActionButton) getActivity().findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        fab_scale = AnimationUtils.loadAnimation(getContext(),R.anim.fab_scale);
        fab_unscale = AnimationUtils.loadAnimation(getContext(),R.anim.fab_unscale);
        CoordinatorLayout parent = (CoordinatorLayout) getActivity().findViewById(R.id.parent);
        mfab.animate().rotation(45);
//        fab1.animate().translationX(fab1.getWidth()/4 - parent.getWidth()/2);
//        fab2.animate().translationX(fab2.getWidth()/4 - parent.getWidth()/2);

        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFabOpen){
                    mfab.startAnimation(fab_unscale);
                    fab1.startAnimation(fab_close);
                    fab2.startAnimation(fab_close);
                    fab1.setClickable(false);
                    fab2.setClickable(false);
                    isFabOpen = false;

                } else {
                    mfab.startAnimation(fab_scale);
                    fab1.startAnimation(fab_open);
                    fab2.startAnimation(fab_open);
                    fab1.setClickable(true);
                    fab2.setClickable(true);
                    isFabOpen = true;

                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mfab.startAnimation(fab_unscale);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
            }
        });
        /*CoordinatorLayout.LayoutParams centerParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        centerParams.anchorGravity = Gravity.CENTER_HORIZONTAL;
        centerParams.anchorGravity = Gravity.BOTTOM;
        centerParams.setAnchorId(centerParams.getAnchorId());
        mfab.setLayoutParams(centerParams);*/
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefrences = PreferenceManager.getDefaultSharedPreferences(getContext());
                Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(sharedPrefrences.getString(Constants.KEY_ENCODED_EMAIL, "null")));
                firebase.removeValue();
                sharedPrefrences.edit().putBoolean(Constants.KEY_OFFERED,false).apply();
                ListFragment listFragment = new ListFragment();
                Bundle b = new Bundle();
                b.putBoolean("animation",true);
                listFragment.setArguments(b);
                mfab.startAnimation(fab_unscale);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
                getContext().stopService(new Intent(getContext(),OfferService.class));
                getFragmentManager().beginTransaction().replace(R.id.container, listFragment).commit();
            }
        });
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("here", "x");
                RideRequest ru = dataSnapshot.getValue(RideRequest.class);
                ru.setEmail(dataSnapshot.getKey());
                list.add(0, ru);
                adapter.addItem(ru);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //change new data in list : map : key: get riderequest : keyset :
               int pos = 0;
                for(RideRequest ru : list){
                    if(ru.getEmail().equals(dataSnapshot.getKey())){
                        RideRequest rr = dataSnapshot.getValue(RideRequest.class);
                        rr.setEmail(dataSnapshot.getKey());
                        list.set(pos, rr);
                        adapter.set(pos,rr);
                        break;
                    }
                    pos++;
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int pos = 0;
                for (RideRequest ru : list) {
                    if (ru.getEmail().equals(dataSnapshot.getKey())) {
                        list.remove(ru);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        // getContext().startService(new Intent(getContext(),OfferService.class));
        return view;
    }

}
