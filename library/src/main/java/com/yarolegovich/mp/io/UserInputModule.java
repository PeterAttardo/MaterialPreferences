package com.yarolegovich.mp.io;

import android.content.Context;

import java.util.Date;
import java.util.Set;

/**
 * Created by yarolegovich on 05.05.2016.
 */
public interface UserInputModule {

    void showEditTextInput(
            String key,
            CharSequence title,
            CharSequence defaultValue,
            Listener<String> listener);

    void showSingleChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            CharSequence[] values,
            int selected,
            Listener<String> listener);

    void showMultiChoiceInput(
            String key,
            CharSequence title,
            CharSequence[] displayItems,
            CharSequence[] values,
            boolean[] defaultSelection,
            Listener<Set<String>> listener);

    void showColorSelectionInput(
            String key,
            CharSequence title,
            int defaultColor,
            Listener<Integer> color);

    void showDatePickerInput(
            String key,
            CharSequence title,
            Date defaultValue,
            Listener<Date> listener);

    void showTimePickerInput(
            String key,
            CharSequence title,
            Date defaultValue,
            Listener<Date> listener);

    interface Factory {
        UserInputModule create(Context context);
    }

    interface Listener<T> {
        void onInput(T value);
    }
}
