package me.yashtrivedi.ideal_succotash;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

/**
 * Created by yashtrivedi on 22/05/16.
 */
public class ShowRequestFormFragment extends DialogFragment {

    public static ShowRequestFormFragment newInstance(int position, String name) {
        ShowRequestFormFragment showRequestFormFragment= new ShowRequestFormFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        bundle.putString("name",name);
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

    EditText editTextArea;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.CustomTheme_Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_request_form,null);
        editTextArea = (EditText) v.findViewById(R.id.area);
        builder.setView(v)
                .setTitle("Are you sure to ride with "+getArguments().getString("name"))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(editTextArea.getText().length() !=0 )
                            ((Callbacks) new ListFragment()).update(editTextArea.getText().toString(),getArguments().getInt("position"));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    interface Callbacks{
        void update(String area,int position);
    }
}
