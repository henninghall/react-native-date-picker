package com.henninghall.date_picker;

import java.util.HashMap;
import cn.carbswang.android.numberpickerview.library.NumberPickerView;

public class EmptyWheelUpdater {

    private final PickerView pickerView;
    private final HashMap<Integer, NumberPickerView> views;

    private int[] ids = {
            R.id.empty1,
            R.id.empty2,
            R.id.empty3
    };

    EmptyWheelUpdater(PickerView pickerView) {
        this.pickerView = pickerView;
        this.views = getViews();
    }

    private HashMap<Integer, NumberPickerView> getViews() {
        HashMap<Integer, NumberPickerView> views = new HashMap<>();
        for (int id: ids) {
            NumberPickerView view = (NumberPickerView) pickerView.findViewById(id);
            views.put(id, view);
        }
        return views;
    }

    void update(Mode mode) {
        hideAll();
        int numberOfVisibleWheels = pickerView.getVisibleWheels().size();
        int emptyViewsToAdd = numberOfVisibleWheels - 1;
        int numberOfPickerWheelsBeforeMode = getNumberOfPickerWheelsBeforeMode(mode);

        for (int i = 0; i < emptyViewsToAdd; i++) {
            int index = numberOfPickerWheelsBeforeMode + 1 + i * 2;
            pickerView.wheelsWrapper.addView(views.get(ids[i]), index);
        }
    }

    private int getNumberOfPickerWheelsBeforeMode(Mode mode) {
        if(mode == Mode.date) return 1;
        if(mode == Mode.datetime) return 4;
        if(mode == Mode.time) return 5;
        return 0;
    }

    private void hideAll() {
        for (NumberPickerView view: views.values()) {
            pickerView.wheelsWrapper.removeView(view);
        }
    }

}
