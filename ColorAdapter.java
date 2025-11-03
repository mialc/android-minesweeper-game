package com.example.minesweeper.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ColorAdapter extends ArrayAdapter<ColorItem> {

    public ColorAdapter(Context ctx, ColorItem[] items) {
        super(ctx, android.R.layout.simple_spinner_dropdown_item, items);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private View decorate(View v, int position) {
        TextView tv = (TextView) v;
        ColorItem item = getItem(position);

        tv.setText(item.name);

        int size = (int)(tv.getResources().getDisplayMetrics().density * 14);

        GradientDrawable dot = new GradientDrawable();
        dot.setShape(GradientDrawable.OVAL);
        dot.setSize(size, size);
        dot.setColor(item.color);

        tv.setCompoundDrawablesWithIntrinsicBounds(dot, null, null, null);
        tv.setCompoundDrawablePadding((int)(size * 0.6f));

        return tv;
    }

    @Override public View getView(int pos, View convert, ViewGroup parent) {
        return decorate(super.getView(pos, convert, parent), pos);
    }

    @Override public View getDropDownView(int pos, View convert, ViewGroup parent) {
        return decorate(super.getDropDownView(pos, convert, parent), pos);
    }
}
