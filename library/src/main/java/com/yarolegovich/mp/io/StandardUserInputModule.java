package com.yarolegovich.mp.io;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.pavelsikun.vintagechroma.ChromaDialog;
import com.pavelsikun.vintagechroma.IndicatorMode;
import com.pavelsikun.vintagechroma.OnColorSelectedListener;
import com.pavelsikun.vintagechroma.colormode.ColorMode;
import com.yarolegovich.mp.R;
import com.yarolegovich.mp.util.Utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yarolegovich on 06.05.2016.
 */
public class StandardUserInputModule implements UserInputModule {

    protected Context context;

    public StandardUserInputModule(Context context) {
        this.context = context;
    }

    @Override
    public void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            final Listener<String> listener) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_edittext, null);
        final EditText inputField = (EditText) view.findViewById(R.id.mp_text_input);

        if (defaultValue != null) {
            inputField.setText(defaultValue);
            inputField.setSelection(defaultValue.length());
        }

        final Dialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(view)
                .show();
        view.findViewById(R.id.mp_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInput(inputField.getText().toString());
                dialog.dismiss();
            }
        });
    }

    @Override
    public void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            int selected,
            final Listener<String> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setSingleChoiceItems(displayItems, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                        dialog.dismiss();
                    }
                })
                /*.setItems(displayItems, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = values[which].toString();
                        listener.onInput(selected);
                    }
                })*/
                .show();
    }

    @Override
    public void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            final CharSequence[] values,
            final boolean[] itemStates,
            final Listener<Set<String>> listener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMultiChoiceItems(displayItems, itemStates, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        itemStates[which] = isChecked;
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Set<String> result = new HashSet<>();
                        for (int i = 0; i < values.length; i++) {
                            if (itemStates[i]) {
                                result.add(values[i].toString());
                            }
                        }
                        listener.onInput(result);
                    }
                })
                .show();
    }

    @Override
    public void showColorSelectionInput(
            String key,
            CharSequence title,
            int defaultColor,
            final Listener<Integer> colorListener) {
        FragmentActivity activity;
        try {
            activity = (FragmentActivity) context;
        } catch (ClassCastException exc) {
            throw new AssertionError(context.getString(R.string.exc_not_frag_activity_subclass));
        }
        String tag = colorListener.getClass().getSimpleName();
        new ChromaDialog.Builder()
                .initialColor(defaultColor)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color) {
                        colorListener.onInput(color);
                    }
                })
                .create()
                .show(activity.getSupportFragmentManager(), tag);
    }

    @Override
    public void showDatePickerInput(String key, CharSequence title, Date defaultValue, final Listener<Date> listener) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(defaultValue);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                listener.onInput(cal.getTime());
            }
        }, year, month, day);
        dialog.show();
    }

    @Override
    public void showTimePickerInput(String key, CharSequence title, Date defaultValue, final Listener<Date> listener) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(defaultValue);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, 0);
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DAY_OF_MONTH, 0);
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);
                listener.onInput(cal.getTime());
            }
        }, hour, minute, false);
        dialog.show();
    }
}
