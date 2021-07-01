package com.ldev.bimq;

public class YourFormatter extends com.github.mikephil.charting.formatter.ValueFormatter {
    @Override
    public String getFormattedValue(float value) {
        return "" + ((int) value);
    }
}
