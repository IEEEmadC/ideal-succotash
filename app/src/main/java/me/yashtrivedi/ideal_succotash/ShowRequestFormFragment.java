package me.yashtrivedi.ideal_succotash;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by yashtrivedi on 22/05/16.
 */
public class ShowRequestFormFragment extends DialogFragment {

    EditText editTextArea;

    public static ShowRequestFormFragment newInstance(int position, String name, Boolean toNirma) {
        ShowRequestFormFragment showRequestFormFragment = new ShowRequestFormFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("name", name);
        bundle.putBoolean(Constants.TO_NIRMA, toNirma);
        showRequestFormFragment.setArguments(bundle);
        return showRequestFormFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_request_form, null);
        editTextArea = (EditText) v.findViewById(R.id.area);
        editTextArea.setHint(getArguments().getBoolean(Constants.TO_NIRMA) ? "Going to" : "Coming from");
        builder.setView(v)
                .setTitle("Are you sure to ride with " + getArguments().getString("name"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editTextArea.getText().length() != 0) {
                            Intent i = new Intent();
                            i.putExtra(Constants.AREA, editTextArea.getText().toString());
                            i.putExtra("position", getArguments().getInt("position"));
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
                        } else {
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
