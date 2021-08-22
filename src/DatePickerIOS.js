import React, { useEffect } from 'react'
import {
  StyleSheet,
  View,
  requireNativeComponent,
  NativeModules,
} from 'react-native'

const RCTDatePickerIOS = requireNativeComponent('RNDatePicker')

export default class DatePickerIOS extends React.Component {
  _picker = null

  componentDidUpdate() {
    if (this.props.date) {
      const propsTimeStamp = this.props.date.getTime()
      if (this._picker) {
        this._picker.setNativeProps({
          date: propsTimeStamp,
        })
      }
    }
  }

  _onChange = (event) => {
    const nativeTimeStamp = event.nativeEvent.timestamp
    this.props.onDateChange &&
      this.props.onDateChange(new Date(nativeTimeStamp))
  }

  _toIosProps = (props) => {
    return {
      testID: props.testID,
      style: [styles.datePickerIOS, props.style],
      date: props.date ? props.date.getTime() : undefined,
      locale: props.locale ? props.locale : undefined,
      maximumDate: props.maximumDate ? props.maximumDate.getTime() : undefined,
      minimumDate: props.minimumDate ? props.minimumDate.getTime() : undefined,
      mode: props.mode,
      minuteInterval: props.minuteInterval,
      timeZoneOffsetInMinutes: props.timeZoneOffsetInMinutes,
      open: props.open,
      textColor: props.textColor,
    }
  }

  render() {
    const props = this._toIosProps(this.props)

    if (props.open !== undefined) {
      if (props.open) NativeModules.RNDatePickerManager.openPicker(props)
      return null
    }

    return (
      <RCTDatePickerIOS
        key={props.textColor} // preventing "Today" string keep old text color when text color changes
        ref={(picker) => {
          this._picker = picker
        }}
        onChange={this._onChange}
        onStartShouldSetResponder={() => true}
        onResponderTerminationRequest={() => false}
        {...props}
      />
    )
  }
}

const styles = StyleSheet.create({
  datePickerIOS: {
    height: 216,
    width: 310,
  },
})
