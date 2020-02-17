package com.henninghall.date_picker.ui;

import android.view.View;

import com.henninghall.date_picker.R;
import com.henninghall.date_picker.State;

import java.util.ArrayList;

import cn.carbswang.android.numberpickerview.library.NumberPickerView;

class EmptyWheels {

    private final ArrayList<NumberPickerView> views;
    private final PickerWrapper pickerWrapper;
    private View rootView;
    private State state;

    private int[] emptyWheelIds = {
            R.id.emptyStart,
            R.id.empty1,
            R.id.empty2,
            R.id.empty3,
            R.id.emptyEnd
    };

    EmptyWheels(View rootView, PickerWrapper pickerWrapper, State state) {
        this.pickerWrapper = pickerWrapper;
        this.rootView = rootView;
        this.state = state;
        this.views = getAll();
    }

    private ArrayList<NumberPickerView> getAll() {
        ArrayList<NumberPickerView> wheels = new ArrayList<>();
        for (int id: emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) rootView.findViewById(id);
            wheels.add(view);
        }
        return wheels;
    }

    void add() {
        int numberOfVisibleWheels = state.derived.getVisibleWheels().size();
        int emptyViewsToAdd = numberOfVisibleWheels + 1;
        for (int i = 0; i < emptyViewsToAdd; i++) {
            int index = i * 2;
            pickerWrapper.addPicker(views.get(i), index);
        }
    }

    void setShownCount(int shownCount) {
        for (int id : emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) rootView.findViewById(id);
            if(view != null) view.setShownCount(shownCount);
        }
    }


}
