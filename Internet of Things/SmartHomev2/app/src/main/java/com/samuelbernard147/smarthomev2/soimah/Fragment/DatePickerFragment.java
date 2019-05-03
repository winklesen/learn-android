package com.samuelbernard147.smarthomev2.soimah.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment {
    private int year, month, day;
    DatePickerDialog.OnDateSetListener onDateSetListener;

    public DatePickerFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener onSelectedDate) {
        onDateSetListener = onSelectedDate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("EXTRAS_YEAR");
        month = args.getInt("EXTRAS_MONTH");
        day = args.getInt("EXTRAS_DAY");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), onDateSetListener, year, month, day);
    }
}