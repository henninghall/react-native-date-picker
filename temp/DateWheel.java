
      -ckage com.henninghall.date_picker.wheels;

import java.util.*;
import com.henninghall.date_picker.*;

public class DateWheel extends Wheel
{
    public DateWheel(final PickerView pickerView, final int id) {
        super(pickerView, id);
    }
    
    @Override
    void init() {
        final int maxDate = 31;
        final int minDate = 1;
        final Calendar cal = this.pickerView.getInitialDate();
        final String initialDate = this.format.format(cal.getTime());
        for (int i = minDate; i <= maxDate; ++i) {
            final int currentDate = (Integer.valueOf(initialDate) + i) % maxDate + 1;
            final String currentDateString = String.valueOf(currentDate);
            this.values.add(currentDateString);
            this.displayValues.add(currentDateString);
        }
        this.picker.setDisplayedValues((String[])this.displayValues.toArray(new String[0]));
        this.picker.setMinValue(0);
        this.picker.setMaxValue(maxDate - minDate);
    }
    
    @Override
    public boolean visible() {
        return this.pickerView.mode == Mode.date;
    }
    
    public String getFormatTemplate() {
        return "d";
    }
}
