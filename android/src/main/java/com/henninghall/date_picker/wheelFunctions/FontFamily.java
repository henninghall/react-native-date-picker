// node_modules/react-native-date-picker/android/src/main/java/com/henninghall/date_picker/wheelFunctions/FontFamily.java
package com.henninghall.date_picker.wheelFunctions;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.henninghall.date_picker.wheels.Wheel;

import java.lang.reflect.Field;

/**
 * This class handles the application of a custom font family to the date picker wheels.
 * It loads the font from the app's assets and applies it to the picker elements.
 * If the font fails to load, it falls back to the default font to ensure continuity.
 * Note: This implementation uses reflection, which may have compatibility risks on different Android versions.
 * @author YourName (customized for font-family support)
 */
public class FontFamily implements WheelFunction {
    
    private final String fontFamily;
    
    /**
     * Constructor to initialize the font family string.
     * @param fontFamily The name of the font file (without extension) located in assets/fonts/.
     */
    public FontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }
    
    /**
     * Applies the font family to the wheel if provided and valid.
     * @param wheel The wheel object to apply the font to.
     */
    @Override
    public void apply(Wheel wheel) {
        if (fontFamily == null || fontFamily.isEmpty()) return;
        
        try {
            Typeface tf = Typeface.createFromAsset(
                ((View) wheel.picker).getContext().getAssets(),
                "fonts/" + fontFamily + ".ttf"
            );
            try {
                Field paintField = wheel.picker.getClass().getSuperclass().getDeclaredField("mSelectorWheelPaint");
                paintField.setAccessible(true);
                Paint selectorPaint = (Paint) paintField.get(wheel.picker);
                selectorPaint.setTypeface(tf);
            } catch (NoSuchFieldException e) {
                android.util.Log.e("DatePicker_FontFamily", "Reflection error: Field not found. Skipping reflection step.");
            } catch (IllegalAccessException e) {
                android.util.Log.e("DatePicker_FontFamily", "Reflection error: Access issue. Skipping.");
            }
            ViewGroup vg = (ViewGroup) ((View) wheel.picker);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                if (child instanceof EditText) {
                    ((EditText) child).setTypeface(tf);
                }
            }
            ((View) wheel.picker).invalidate();
        } catch (Exception e) {
            android.util.Log.e("DatePicker_FontFamily", "General error: " + e.getMessage() + ". Falling back to default font.");
            Typeface defaultTf = Typeface.DEFAULT;
            ViewGroup vg = (ViewGroup) ((View) wheel.picker);
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                if (child instanceof EditText) {
                    ((EditText) child).setTypeface(defaultTf);
                }
            }
            ((View) wheel.picker).invalidate();
        }
    }
}
