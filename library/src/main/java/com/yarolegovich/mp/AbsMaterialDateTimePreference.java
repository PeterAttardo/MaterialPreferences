package com.yarolegovich.mp;

import android.content.Context;
import android.util.AttributeSet;

import com.yarolegovich.mp.io.StorageModule;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yarolegovich on 01.05.2016.
 */
public abstract class AbsMaterialDateTimePreference extends AbsMaterialTextValuePreference<Date> {

    DateFormat storageDateFormat;

    public AbsMaterialDateTimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsMaterialDateTimePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AbsMaterialDateTimePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStorageDateFormat(DateFormat storageDateFormat) {
        this.storageDateFormat = storageDateFormat;
    }

    @Override
    public Date getValue() {
        try {
            return storageDateFormat.parse(storageModule.getString(key, defaultValue));
        } catch (ParseException e) {
            return new Date();
        }
    }

    @Override
    public void setValue(Date value) {
        storageModule.saveString(key, storageDateFormat.format(value));
        showNewValueIfNeeded(toRepresentation(value));
    }

    @Override
    public void setStorageModule(StorageModule storageModule) {
        super.setStorageModule(storageModule);
        showNewValueIfNeeded(toRepresentation(getValue()));
    }
}
