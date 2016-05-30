package fang.weighttracker;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Date;

/**
 * Created by Fang2 on 2016/5/13.
 */
public class NumberPickerFragment extends DialogFragment {
    public static final String EXTRA_NUMBER =
            "fang.weighttracker.number";
    private static final String ARG_NUMBER = "number";
    private NumberPicker mNumberPicker;
    private NumberPicker mDecimalPicker;

    public  static NumberPickerFragment newInstance(float number){
        Bundle args = new Bundle();
        args.putSerializable(ARG_NUMBER, number);

        NumberPickerFragment fragment = new NumberPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_number, null);
        mNumberPicker = (NumberPicker)v.findViewById(R.id.number_picker);
        mNumberPicker.setMaxValue(500);
        mNumberPicker.setMinValue(50);
        mNumberPicker.setValue(120);
        mDecimalPicker = (NumberPicker) v.findViewById(R.id.decimal_picker);
        mDecimalPicker.setMaxValue(9);
        mDecimalPicker.setMinValue(0);
        mNumberPicker.setValue(120);
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.number_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int number = mNumberPicker.getValue();
                        int decimal = mDecimalPicker.getValue();
                        float weight_value = (float)(number + decimal * 0.1);
                        sendResult(Activity.RESULT_OK, weight_value);
                    }
                })
                .create();
    }
    private void sendResult(int resultCode, float number){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NUMBER, number);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
