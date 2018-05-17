package com.softwaredev.groceryappv1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

public class EditTextDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    int mDay;
    int mMonth;
    int mYear;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        ((DatePickerDialog.OnDateSetListener) getActivity()).onDateSet(view, year, month, day);
    }
}
