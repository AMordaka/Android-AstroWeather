package com.example.arek.astroweather.fragments.Preference;


import android.content.Context;
import android.preference.EditTextPreference;
import android.util.AttributeSet;
import android.view.View;

public class LocationEditTextPreference extends EditTextPreference {


    public LocationEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public LocationEditTextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LocationEditTextPreference(Context context) {
        super(context);
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
