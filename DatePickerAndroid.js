import React from 'react';
import { DatePickerIOS, requireNativeComponent, StyleSheet } from 'react-native';

const NativeDatePicker = requireNativeComponent(`DatePickerManager`, DatePickerAndroid, { nativeOnly: { onChange: true } });

class DatePickerAndroid extends React.Component {

    static defaultProps = { 
        mode: 'datetime',
        minuteInterval: 1,
     };
  
    _onChange = e => this.props.onDateChange(new Date(parseInt(e.nativeEvent.date)));
    _maximumDate = () => this.props.maximumDate && this.props.maximumDate.getTime();
    _minimumDate = () => this.props.minimumDate && this.props.minimumDate.getTime();
    render = () => (
        <NativeDatePicker
            {...this.props }
            date={this.props.date.getTime()}
            minimumDate={this._minimumDate()}
            maximumDate={this._maximumDate()}
            onChange={this._onChange}
            style={[styles.picker, this.props.style]}
        />
    )
}

const styles = StyleSheet.create({
    picker: {
        width: 310,
        height: 180,
    }
})

DatePickerAndroid.propTypes = DatePickerIOS.propTypes;

export default DatePickerAndroid;
