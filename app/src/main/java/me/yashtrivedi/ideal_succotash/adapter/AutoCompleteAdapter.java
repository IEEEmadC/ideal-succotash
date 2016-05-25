package me.yashtrivedi.ideal_succotash.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.yashtrivedi.ideal_succotash.R;
import me.yashtrivedi.ideal_succotash.Utils;
import me.yashtrivedi.ideal_succotash.model.User;

/**
 * Created by yashtrivedi on 25/05/16.
 */
public class AutoCompleteAdapter extends ArrayAdapter<User> implements Filterable{

    List<User> items, itemsAll, suggestions;
    public AutoCompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        items = new ArrayList<>();
        itemsAll = new ArrayList<>();
        suggestions = new ArrayList<>();
    }

    @Override
    public void add(User object) {
        super.add(object);
        items.add(0,object);
        itemsAll.add(0,object);
    }
/*
    @Override
    public int getCount() {
        return super.getCount();
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            v = inflater.inflate(R.layout.autocomplete_item,null);
        }
        User user = items.get(position);
        if(user != null){
            TextView name = (TextView) v.findViewById(R.id.name);
            name.setText(user.getName());
            TextView email = (TextView) v.findViewById(R.id.email);
            email.setText(Utils.emailToroll(user.getEmail()));
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        @Override
        public String convertResultToString(Object resultValue) {

            return null;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint!=null){
                suggestions.clear();
                for(User user : itemsAll){
                    if(user.getName().toLowerCase().contains(constraint.toString().toLowerCase()) || Utils.emailToroll(user.getEmail()).toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(user);
                    }
                }
                FilterResults results = new FilterResults();
                results.values = suggestions;
                results.count = suggestions.size();
                return results;
            }else
                return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<User> featuredList = (List<User>) results.values;
            if(results!=null && results.count>0){
                clear();
                for(User c : featuredList){
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };
}
