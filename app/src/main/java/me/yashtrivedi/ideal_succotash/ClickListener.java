package me.yashtrivedi.ideal_succotash;

import android.view.View;

/**
 * Created by yashtrivedi on 20/05/16.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}