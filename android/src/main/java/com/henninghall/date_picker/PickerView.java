package com.henninghall.date_picker;

import android.widget.RelativeLayout;

import com.facebook.react.bridge.Dynamic;
import com.henninghall.date_picker.props.DividerHeightProp;
import com.henninghall.date_picker.props.Is24hourSourceProp;
import com.henninghall.date_picker.props.MaximumDateProp;
import com.henninghall.date_picker.props.MinimumDateProp;
import com.henninghall.date_picker.props.MinuteIntervalProp;
import com.henninghall.date_picker.props.UtcProp;
import com.henninghall.date_picker.props.VariantProp;
import com.henninghall.date_picker.props.DateProp;
import com.henninghall.date_picker.props.FadeToColorProp;
import com.henninghall.date_picker.props.HeightProp;
import com.henninghall.date_picker.props.LocaleProp;
import com.henninghall.date_picker.props.ModeProp;
import com.henninghall.date_picker.props.TextColorProp;
import com.henninghall.date_picker.ui.UIManager;
import java.util.ArrayList;

public class PickerView extends RelativeLayout {

    private UIManager uiManager;
    private State state = new State();
    private ArrayList<String> updatedProps = new ArrayList<>();

    public PickerView() {
        super(DatePickerManager.context);
    }

    public void update() {

        if (didUpdate(VariantProp.name)) {
            this.removeAllViewsInLayout();
            inflate(getContext(), state.derived.getRootLayout(), this);
            uiManager = new UIManager(state, this);
        }

        if (didUpdate(FadeToColorProp.name)) {
            uiManager.updateFadeToColor();
        }

        if (didUpdate(TextColorProp.name)) {
            uiManager.updateTextColor();
        }

        if (didUpdate(ModeProp.name, VariantProp.name, Is24hourSourceProp.name)) {
            uiManager.updateWheelVisibility();
        }

        if (didUpdate(HeightProp.name)) {
            uiManager.updateHeight();
        }

        if (didUpdate(DividerHeightProp.name)) {
            uiManager.updateDividerHeight();
        }

        if (didUpdate(ModeProp.name, LocaleProp.name, VariantProp.name, Is24hourSourceProp.name)) {
            uiManager.updateWheelOrder();
        }

        if (didUpdate(ModeProp.name)) {
            uiManager.updateWheelPadding();
        }

        if (didUpdate(DateProp.name, HeightProp.name, LocaleProp.name,
                MaximumDateProp.name, MinimumDateProp.name, MinuteIntervalProp.name, ModeProp.name,
                UtcProp.name, VariantProp.name
        )) {
            uiManager.updateDisplayValues();
        }

        uiManager.setWheelsToDate();

        updatedProps = new ArrayList<>();
    }

    private boolean didUpdate(String... propNames) {
        for (String propName : propNames) {
            if (updatedProps.contains(propName)) return true;
        }
        return false;
    }

    public void updateProp(String propName, Dynamic value) {
        state.setProp(propName, value);
        updatedProps.add(propName);
    }

    public void scroll(int wheelIndex, int scrollTimes) {
        uiManager.scroll(wheelIndex, scrollTimes);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }


}
