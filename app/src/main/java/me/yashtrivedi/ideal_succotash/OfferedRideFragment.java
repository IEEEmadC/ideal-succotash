package me.yashtrivedi.ideal_succotash;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amit on 21-May-16.
 */
public class OfferedRideFragment extends Fragment {

    List<RideRequest> list;
    private RecyclerView recyclerView;

    public OfferedRideFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_offered_ride, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        Firebase firebase = new Firebase(Constants.FIREBASE_URL_RIDES.concat("/").concat(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, "")).concat("/").concat(Constants.FIREBASE_LOCATION_REQUEST_RIDE));
        final OViewAdapter adapter = new OViewAdapter(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()).getString(Constants.KEY_ENCODED_EMAIL, ""));
        list = new ArrayList<>();

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
