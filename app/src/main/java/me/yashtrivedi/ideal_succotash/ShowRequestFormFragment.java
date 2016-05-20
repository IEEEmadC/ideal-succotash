package me.yashtrivedi.ideal_succotash;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * Created by amit on 20-May-16.
 */
public class ShowRequestFormFragment extends DialogFragment {

    EditText editTextCarNo, editTextArea;
    CheckBox checkBoxToFromNirma;


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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CustomTheme_Dialog);

        /* Get the layout inflater */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.dialog_request_form, null);
        editTextCarNo = (EditText) rootView.findViewById(R.id.edit_text_car_no);
        editTextArea = (EditText) rootView.findViewById(R.id.edit_text_area);
        checkBoxToFromNirma = (CheckBox) rootView.findViewById(R.id.area_check_box);
        checkBoxToFromNirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkBoxToFromNirma.isChecked()){
                    checkBoxToFromNirma.setText("Going Home");
                    editTextArea.setHint("Enter the going to area");

                }
                else{

                    checkBoxToFromNirma.setText("Going Nirma");
                    editTextArea.setHint("Enter the going from area");

                }

            }
        });




        builder.setView(rootView)
                /* Add action buttons */
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        postRequest();
                    }
                });

        return builder.create();
    }

    public void postRequest() {




    }
}
