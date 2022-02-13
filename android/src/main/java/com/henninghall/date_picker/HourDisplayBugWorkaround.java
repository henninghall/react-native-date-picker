package com.henninghall.date_picker;

import com.henninghall.date_picker.models.Variant;

/**
 * This workaround prevents a bug existing in the NativeAndroid variant.
 * The bug displays duplicated "12" hours string when the current time is set to "1".
 * Haven't found the root cause of this bug but since this bug only occurs in the NativeAndroid
 * variant there are reasons to believe there is a internal bug in Android's NumberPicker for this
 * use case.
 *
 * More info about the bug:
 * https://github.com/henninghall/react-native-date-picker/issues/382
 */
public class HourDisplayBugWorkaround {

    private final State state;

    public HourDisplayBugWorkaround(State state) {
        this.state = state;
    }

    private boolean shouldApply(String displayValue) {
        if(state.getVariant() != Variant.nativeAndroid) return false;
        if(displayValue.length() != 1) return false;
        return true;
    }

    private String adjust(String displayValue) {
        return " " + displayValue + " ";
    }

    public String adjustValueIfNecessary(String displayValue) {
        if(!shouldApply(displayValue)) return displayValue;
        return adjust(displayValue);
    }

}
