import {
    Platform,
    NativeModules,
    NativeAppEventEmitter,
    DatePickerIOS,
    Text,
    requireNativeComponent,
    ViewPropTypes,
    StyleSheet,
} from 'react-native';
import React, { Component } from 'react'
import PropTypes from 'prop-types';
import { DatePicker } from 'react-native-date-picker-x';

const ios = Platform.OS === 'ios';

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

class DatePickerIOSWithSize extends React.Component {
    render = () => <DatePickerIOS {...this.props} style={[styles.picker, this.props.style]} />
}


const styles = StyleSheet.create({
    picker: {
        width: 310,
        height: 180,
    }
})

DatePickerAndroid.propTypes = DatePickerIOS.propTypes;

export default ios ? DatePickerIOSWithSize : DatePickerAndroid;
