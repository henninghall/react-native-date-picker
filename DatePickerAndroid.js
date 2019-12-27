import React from 'react'
import { StyleSheet, requireNativeComponent } from 'react-native'
import moment from 'moment'
import { throwIfInvalidProps } from './propChecker'

const NativeDatePicker = requireNativeComponent(
  `DatePickerManager`,
  DatePickerAndroid,
  { nativeOnly: { onChange: true } }
)

class DatePickerAndroid extends React.PureComponent {

  render() {
    if (__DEV__) throwIfInvalidProps(this.props)
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
    this.props.onDateChange && this.props.onDateChange(jsDate)
  }

  _maximumDate = () =>
    this.props.maximumDate &&
    this._toIsoWithTimeZoneOffset(this.props.maximumDate)

  _minimumDate = () =>
    this.props.minimumDate &&
    this._toIsoWithTimeZoneOffset(this.props.minimumDate)

  _date = () => this._toIsoWithTimeZoneOffset(this.props.date)

  _fromIsoWithTimeZoneOffset = date => {
    if (this.props.timeZoneOffsetInMinutes === undefined) return moment(date)

    return moment
      .utc(date)
      .subtract(this.props.timeZoneOffsetInMinutes, 'minutes')
  }

  _toIsoWithTimeZoneOffset = date => {
    if (this.props.timeZoneOffsetInMinutes === undefined)
      return moment(date).toISOString()

    return moment
      .utc(date)
      .add(this.props.timeZoneOffsetInMinutes, 'minutes')
      .toISOString()
  }
}

const styles = StyleSheet.create({
  picker: {
    width: 310,
    height: 180,
  },
})

export default DatePickerAndroid
