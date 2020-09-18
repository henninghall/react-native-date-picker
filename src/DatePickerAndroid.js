import React from 'react'
import { StyleSheet, requireNativeComponent } from 'react-native'

function addMinutes(date, minutesToAdd) {
  return new Date(date.valueOf() + minutesToAdd * 60 * 1000)
}

const NativeDatePicker = requireNativeComponent(
  `DatePickerManager`,
  DatePickerAndroid,
  { nativeOnly: { onChange: true } }
)

const height = 180
const timeModeWidth = 240
const defaultWidth = 310

class DatePickerAndroid extends React.PureComponent {
  render() {
    return (
      <NativeDatePicker
        {...this.props}
        date={this._date()}
        minimumDate={this._minimumDate()}
        maximumDate={this._maximumDate()}
        onChange={this._onChange}
        style={this.getStyle()}
        utc={this.props.timeZoneOffsetInMinutes !== undefined}
      />
    )
  }

  getStyle = () => {
    const width = this.props.mode === 'time' ? timeModeWidth : defaultWidth
    return [{ width, height }, this.props.style]
  }

  _onChange = e => {
    const jsDate = this._fromIsoWithTimeZoneOffset(e.nativeEvent.date)
    this.props.onDateChange && this.props.onDateChange(jsDate)
    if (this.props.onDateStringChange) {
      this.props.onDateStringChange(e.nativeEvent.dateString)
    }
  }

  _maximumDate = () =>
    this.props.maximumDate &&
    this._toIsoWithTimeZoneOffset(this.props.maximumDate)

  _minimumDate = () =>
    this.props.minimumDate &&
    this._toIsoWithTimeZoneOffset(this.props.minimumDate)

  _date = () => this._toIsoWithTimeZoneOffset(this.props.date)

  _fromIsoWithTimeZoneOffset = timestamp => {
    const date = new Date(timestamp)
    if (this.props.timeZoneOffsetInMinutes === undefined) return date
    return addMinutes(date, -this.props.timeZoneOffsetInMinutes)
  }

  _toIsoWithTimeZoneOffset = date => {
    if (this.props.timeZoneOffsetInMinutes === undefined)
      return date.toISOString()

    return addMinutes(date, this.props.timeZoneOffsetInMinutes).toISOString()
  }
}

export default DatePickerAndroid
