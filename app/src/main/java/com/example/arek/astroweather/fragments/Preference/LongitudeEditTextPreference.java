package com.example.arek.astroweather.fragments.Preference;


import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;

public class LongitudeEditTextPreference extends EditTextPreference {


    public LongitudeEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LongitudeEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LongitudeEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LongitudeEditTextPreference(Context context) {
        super(context);
    }

    @Override
    public void setText(String text) {
        if (text == null || text.length() == 0) super.setText(text);
        super.setText(text);
    }

    @Override
    public String getText() {
        String text = super.getText();
        if (text == null || text.length() == 0) return text;
        return text;
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        super.onSetInitialValue(restoreValue,defaultValue);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
    }
}
