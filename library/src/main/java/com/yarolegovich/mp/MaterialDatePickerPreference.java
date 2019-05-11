package com.yarolegovich.mp;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MaterialDatePickerPreference extends AbsMaterialDateTimePreference {

    DateFormat representationDateFormat;

    public MaterialDatePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialDatePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialDatePickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init(AttributeSet attrs) {
        setStorageDateFormat(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()));
        setRepresentationDateFormat(DateFormat.getDateInstance());
        super.init(attrs);
    }

    public void setRepresentationDateFormat(DateFormat representationDateFormat) {
        this.representationDateFormat = representationDateFormat;
    }

    @Override
    protected CharSequence toRepresentation(Date value) {
        return representationDateFormat.format(value);
    }

    @Override
    public void onClick(View v) {
        userInputModule.showDatePickerInput(key, getTitle(), getValue(), this);
    }
}
