import React from 'react';
import { DatePickerIOS, requireNativeComponent, StyleSheet } from 'react-native';
import moment from 'moment'

const NativeDatePicker = requireNativeComponent(`DatePickerManager`, DatePickerAndroid, { nativeOnly: { onChange: true } });

class DatePickerAndroid extends React.Component {

    static defaultProps = {
        mode: 'datetime',
        minuteInterval: 1,
    };

    render = () => {
        return (
            <NativeDatePicker
                {...this.props}
                date={this._date()}
                minimumDate={this._minimumDate()}
                maximumDate={this._maximumDate()}
                onChange={this._onChange}
                style={[styles.picker, this.props.style]}
                utc={this.props.timeZoneOffsetInMinutes !== undefined}
            />
        )
    }

    _onChange = e => {
        const jsDate = this._fromIsoWithTimeZoneOffset(e.nativeEvent.date).toDate()
        this.props.onDateChange(jsDate)
    }

    _maximumDate = () => this._toIsoWithTimeZoneOffset(this.props.maximumDate);
    
    _minimumDate = () => this._toIsoWithTimeZoneOffset(this.props.minimumDate);
    
    _date = () => this._toIsoWithTimeZoneOffset(this.props.date);

    _fromIsoWithTimeZoneOffset = date => {
        if (this.props.timeZoneOffsetInMinutes === undefined)
            return moment(date)

        return moment.utc(date).subtract(this.props.timeZoneOffsetInMinutes, 'minutes')
    }
    
    _toIsoWithTimeZoneOffset = date => {
        if (this.props.timeZoneOffsetInMinutes === undefined)
            return moment(date).toISOString()

        return moment.utc(date).add(this.props.timeZoneOffsetInMinutes, 'minutes').toISOString()
    }

}

const styles = StyleSheet.create({
    picker: {
        width: 310,
        height: 180,
    }
})

DatePickerAndroid.propTypes = DatePickerIOS.propTypes;

export default DatePickerAndroid;
