import React from 'react';
import { DatePickerIOS, requireNativeComponent, StyleSheet } from 'react-native';

const NativeDatePicker = requireNativeComponent(`DatePickerManager`, DatePickerAndroid, { nativeOnly: { onChange: true } });

class DatePickerAndroid extends React.Component {

    static defaultProps = {
        mode: 'datetime',
        minuteInterval: 1,
    };

    render = () => (
        <NativeDatePicker
            {...this.props}
            date={this._date()}
            minimumDate={this._minimumDate()}
            maximumDate={this._maximumDate()}
            onChange={this._onChange}
            style={[styles.picker, this.props.style]}
        />
    )

    _onChange = e => this.props.onDateChange(new Date(parseInt(e.nativeEvent.date + this._getOffsetMillis())));
    _maximumDate = () => this._toUnixMillisWithTimeZoneOffset(this.props.maximumDate);
    _minimumDate = () => this._toUnixMillisWithTimeZoneOffset(this.props.minimumDate);
    _date = () => this._toUnixMillisWithTimeZoneOffset(this.props.date);
    _toUnixMillisWithTimeZoneOffset = date => date && this._toUnixMillis(date) - this._getOffsetMillis();
    _toUnixMillis = date => date.getTime()

    _getOffsetMillis = () => this.props.timeZoneOffsetInMinutes === undefined ? 0
        : -toMs(this.props.timeZoneOffsetInMinutes + new Date().getTimezoneOffset())

}

const toMs = minutes => minutes * 60 * 1000

const styles = StyleSheet.create({
    picker: {
        width: 310,
        height: 180,
    }
})

DatePickerAndroid.propTypes = DatePickerIOS.propTypes;

export default DatePickerAndroid;
