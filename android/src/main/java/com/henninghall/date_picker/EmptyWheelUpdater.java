package com.henninghall.date_picker;

import java.util.HashMap;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class EmptyWheelUpdater {

    private final PickerView pickerView;
    private final HashMap<Integer, NumberPickerView> views;

    EmptyWheelUpdater(PickerView pickerView) {
        this.pickerView = pickerView;
        this.views = getViews();
    }

    private HashMap<Integer, NumberPickerView> getViews() {
        HashMap<Integer, NumberPickerView> views = new HashMap<>();
        for (int id: Utils.emptyWheelIds) {
            NumberPickerView view = (NumberPickerView) pickerView.findViewById(id);
            views.put(id, view);
        }
        return views;
    }

    void update() {
        hideAll();
        int numberOfVisibleWheels = pickerView.getVisibleWheels().size();
        int emptyViewsToAdd = numberOfVisibleWheels + 1;

        for (int i = 0; i < emptyViewsToAdd; i++) {
            int index = i * 2;
            pickerView.wheelsWrapper.addView(views.get(Utils.emptyWheelIds[i]), index);
        }
    }

    private void hideAll() {
        for (NumberPickerView view: views.values()) {
            pickerView.wheelsWrapper.removeView(view);
        }
    }

}
