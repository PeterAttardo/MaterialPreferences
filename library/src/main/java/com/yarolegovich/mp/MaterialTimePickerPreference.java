package com.yarolegovich.mp;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MaterialTimePickerPreference extends AbsMaterialDateTimePreference {

    DateFormat representationTimeFormat;

    public MaterialTimePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaterialTimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MaterialTimePickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setRepresentationTimeFormat(DateFormat representationTimeFormat) {
        this.representationTimeFormat = representationTimeFormat;
    }

    @Override
    protected void init(AttributeSet attrs) {
        setStorageDateFormat(new SimpleDateFormat("HH:mm", Locale.getDefault()));
        setRepresentationTimeFormat(DateFormat.getTimeInstance());
        super.init(attrs);
    }

    @Override
    protected CharSequence toRepresentation(Date value) {
        return representationTimeFormat.format(value);
    }

    @Override
    public void onClick(View v) {
        userInputModule.showTimePickerInput(key, getTitle(), getValue(), this);
    }
}
