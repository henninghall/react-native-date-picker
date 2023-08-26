import React from 'react'
import {
  NativeModules,
  requireNativeComponent,
  Platform,
  NativeEventEmitter,
  TurboModuleRegistry,
} from 'react-native'
import { shouldCloseModal, shouldOpenModal } from './modal'

function addMinutes(date, minutesToAdd) {
  return new Date(date.valueOf() + minutesToAdd * 60 * 1000)
}

const NativeDatePicker =
  Platform.OS === 'android'
    ? requireNativeComponent('RNDatePicker', DatePickerAndroid, {
        nativeOnly: { onChange: true },
      })
    : null

const height = 180
const timeModeWidth = 240
const defaultWidth = 310

const eventEmitter = new NativeEventEmitter(NativeModules.RNDatePicker)

const NativePicker = TurboModuleRegistry.get('RNDatePicker')

class DatePickerAndroid extends React.PureComponent {
  render() {
    const props = this.getProps()

    if (shouldOpenModal(props, this.previousProps)) {
      this.isClosing = false
      NativePicker.openPicker(
        props
        // this._onConfirm,
        // this._onCancel
      )
    }
    if (shouldCloseModal(props, this.previousProps, this.isClosing)) {
      this.closing = true
      NativePicker.closePicker()
    }

    this.previousProps = props

    if (props.modal) return null

    return <NativeDatePicker {...props} onChange={this._onChange} />
  }

  componentDidMount = () => {
    this.eventListener = eventEmitter.addListener('dateChange', this._onChange)
  }

  componentWillUnmount = () => {
    this.eventListener.remove()
  }

  getProps = () => ({
    ...this.props,
    date: this._date(),
    minimumDate: this._minimumDate(),
    maximumDate: this._maximumDate(),
    timezoneOffsetInMinutes: this._getTimezoneOffsetInMinutes(),
    style: this._getStyle(),
  })

  _getTimezoneOffsetInMinutes = () => {
    if (this.props.timeZoneOffsetInMinutes == undefined) return undefined
    return this.props.timeZoneOffsetInMinutes
  }

  _getStyle = () => {
    const width = this.props.mode === 'time' ? timeModeWidth : defaultWidth
    return [{ width, height }, this.props.style]
  }

  _onChange = (e) => {
    const jsDate = this._fromIsoWithTimeZoneOffset(e.date)
    this.props.onDateChange && this.props.onDateChange(jsDate)
    if (this.props.onDateStringChange) {
      this.props.onDateStringChange(e.dateString)
    }
  }

  _maximumDate = () =>
    this.props.maximumDate &&
    this._toIsoWithTimeZoneOffset(this.props.maximumDate)

  _minimumDate = () =>
    this.props.minimumDate &&
    this._toIsoWithTimeZoneOffset(this.props.minimumDate)

  _date = () => this._toIsoWithTimeZoneOffset(this.props.date)

  _fromIsoWithTimeZoneOffset = (timestamp) => {
    return new Date(timestamp)
  }

  _toIsoWithTimeZoneOffset = (date) => {
    return date.toISOString()
  }

  _onConfirm = (isoDate) => {
    this.isClosing = true
    this.props.onConfirm(this._fromIsoWithTimeZoneOffset(isoDate))
  }

  _onCancel = () => {
    this.isClosing = true
    this.props.onCancel()
  }
}

export default DatePickerAndroid
