package me.yashtrivedi.ideal_succotash;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by amit on 20-May-16.
 */
public class ShowRequestFormFragment extends DialogFragment {

    EditText editTextMealName;

    /**
     * Public static constructor that creates fragment and
     * passes a bundle with data into it when adapter is created
     */
    public static ShowRequestFormFragment newInstance() {
        ShowRequestFormFragment showRequestFormFragment = new ShowRequestFormFragment();
        Bundle bundle = new Bundle();
        showRequestFormFragment.setArguments(bundle);
        return showRequestFormFragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Open the keyboard automatically when the dialog fragment is opened
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Use the Builder class for convenient dialog construction */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppTheme);
        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_request_form, null);
        editTextMealName = (EditText) rootView.findViewById(R.id.edit_text_meal_name);

        /**
         * Call addMeal() when user taps "Done" keyboard action
         */
      /*  editTextMealName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    addMeal();
                }
                return true;
            }
        });*/

        /* Inflate and set the layout for the dialog */
        /* Pass null as the parent view because its going in the dialog layout */
        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addMeal();
                    }
                });

        return builder.create();
    }

    /**
     * Add new meal
     */
    public void addMeal() {
    }
}

