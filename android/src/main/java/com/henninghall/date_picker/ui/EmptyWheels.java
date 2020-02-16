package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.PickerView;
import com.henninghall.date_picker.State;
import com.henninghall.date_picker.Utils;

import java.util.HashMap;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class EmptyWheels {

    private final HashMap<Integer, NumberPickerView> views;
    private final Wheels wheels;
    private View rootView;
    private State state;

    EmptyWheels(View rootView, Wheels wheels, State state) {
        this.wheels = wheels;
        this.rootView = rootView;
        this.state = state;
        this.views = getViews();
    }

    private HashMap<Integer, NumberPickerView> getViews() {
        HashMap<Integer, NumberPickerView> views = new HashMap<>();
        for (int id: Utils.emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) rootView.findViewById(id);
            views.put(id, view);
        }
        return views;
    }

    void add() {
        int numberOfVisibleWheels = state.getVisibleWheels().size();
        int emptyViewsToAdd = numberOfVisibleWheels + 1;
        for (int i = 0; i < emptyViewsToAdd; i++) {
            int index = i * 2;
            wheels.addWheel(views.get(Utils.emptyWheelIds[i]), index);
        }
    }


}
